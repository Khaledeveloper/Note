package com.example.khaled.Note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.khaled.Note.database.CrimeDbSchema.CrimeTable;

/**
 * Created by khaled on 22/10/2017.
 */

public class CrimeDbHelper extends SQLiteOpenHelper {
    private static final String TAG ="CrimeDbHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "crimeDB";

    public CrimeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
//the space after table is very important!!!!!!
        db.execSQL("create table "+ CrimeTable.NAME+"("+
        "_id integer primary key autoincrement,"+
                CrimeTable.Cols.UUID+","+
                CrimeTable.Cols.TITLE+","+
                CrimeTable.Cols.CONTENT+","+
                CrimeTable.Cols.DATE+","+
                CrimeTable.Cols.SOLVED+","+
                CrimeTable.Cols.CONTACTNUMBER+
                        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
