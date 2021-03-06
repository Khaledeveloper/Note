package com.example.khaled.Note.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.example.khaled.Note.R;

/**
 * Created by khaled on 16/10/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment creatFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.Fragment_Container);
        if (fragment==null){
            fragment = creatFragment();
            fm.beginTransaction()
                    .add(R.id.Fragment_Container , fragment)
                    .commit();
        }
    }
}