
package com.example.khaled.Note.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.khaled.Note.database.CrimeCursorWrapper;
import com.example.khaled.Note.database.CrimeDbHelper;
import com.example.khaled.Note.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by khaled on 16/10/2017.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private ArrayList<Crime> mCrimes;
    //new for database
    private SQLiteDatabase mSQLiteDatabase;
    Context mContext;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab= new CrimeLab(context); //for private constructor below
        }

        return sCrimeLab;
    }

    private CrimeLab(Context context){
        //new for database
        //for DatabaseopenHelper class to write

        mContext = context.getApplicationContext();
        mSQLiteDatabase = new CrimeDbHelper(mContext)
                .getWritableDatabase();


      //*********************************************

      //for loading emptylist to ad crime
      //  mCrimes = new ArrayList<>();

      //************************************************



      //that for loading a dafult list
       /* for (int i =0 ;i <100 ; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i% 2 == 0);
            mCrimes.add(crime);*/
        }

        //a static method for database to insert it
    public static ContentValues getContentValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Cols.CONTENT, crime.getContent());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved()?1:0);
        contentValues.put(CrimeTable.Cols.CONTACTNUMBER, crime.getContactnumber());

        return contentValues;
    }



    public List<Crime> getCrimes(){
       List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor  = queryCrimes(null, null);//query crime is below here

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            crimes.add(cursor.getCrime());
            cursor.moveToNext();
        }
        cursor.close();

        return crimes;





       //***********************************
       //this line was only before DataBase
       // return mCrimes;
    }


    public Crime getCrime(UUID id){


        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID +"= ?",
                new String[] {id.toString()}

        );

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }


        /* this was before database
        for (Crime crime: mCrimes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
        */
    }


    public void addCrime( Crime c){
        //new for database
        ContentValues contentValues = getContentValues(c);

        mSQLiteDatabase.insert(CrimeTable.NAME,null, contentValues);

        //**********************************************
        //this was for adding a crime before DataBase
      //  mCrimes.add(c);
    }




    //a method  for database

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();

        ContentValues contentValues = getContentValues(crime);

        mSQLiteDatabase.update(CrimeTable.NAME ,contentValues,CrimeTable.Cols.UUID +"= ?",new String[] {uuidString});

    }


    public void deleteNote(Crime crime){

        String uuidString = crime.getId().toString();

        ContentValues contentValues =getContentValues(crime);

        mSQLiteDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID+"= ?" , new String[] {uuidString});

    }


    //a method for database

    private CrimeCursorWrapper queryCrimes(String whereClause , String[] whereArgs){
        Cursor cursor =mSQLiteDatabase.query(
                CrimeTable.NAME,
                null, //for columns , null select all columns
                whereClause,
                whereArgs,
                null,  //groupBy
                null, //having
                CrimeTable.Cols.DATE+" DESC" //orderBy
        );



        return new CrimeCursorWrapper(cursor);
    }

    //checking
    //check if there is external storage
    public File getPhotoFile( Crime crime){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if ( externalFilesDir == null){
            return null;
        }

        return new File( externalFilesDir , crime.getPhotoFilename());
    }





}
