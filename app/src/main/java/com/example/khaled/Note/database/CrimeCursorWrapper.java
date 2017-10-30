package com.example.khaled.Note.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.khaled.Note.database.CrimeDbSchema.CrimeTable;

import com.example.khaled.Note.models.Crime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by khaled on 22/10/2017.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {

        //impelement in Crimlab
        super(cursor);
    }
    public Crime getCrime(){//impelemnt in crimelap (for query)
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String content = getString(getColumnIndex(CrimeTable.Cols.CONTENT));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String CONTACTNUMBER = getString(getColumnIndex(CrimeTable.Cols.CONTACTNUMBER));


        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setContent(content);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved !=0);
        crime.setContactnumber(CONTACTNUMBER);







        return crime;



    }


}
