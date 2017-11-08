
package com.example.khaled.Note.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.khaled.Note.CrimeFragment;
import com.example.khaled.Note.interfaces.InterfaceOnBackPressed;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    public static String TAGACTIVITY ="MainActivity";

    private static final String Crime_ID_KEY ="com.example.khaled.crime.crimeID";


    public static Intent newIntent(Context context, UUID crimeID){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(Crime_ID_KEY,crimeID);
        return intent;

    }

//crimeActivity
    @Override
    protected Fragment creatFragment() {
        //to send it to method of newInstance for Argue
        UUID crimeID = (UUID)getIntent().getSerializableExtra(Crime_ID_KEY);

        return CrimeFragment.newInstance(crimeID);
    }




}
