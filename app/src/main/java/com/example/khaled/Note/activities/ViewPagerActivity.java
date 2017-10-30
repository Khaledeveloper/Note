package com.example.khaled.Note.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.khaled.Note.CrimeFragment;

import com.example.khaled.Note.R;
import com.example.khaled.Note.interfaces.InterfaceOnSelectOptionMenuPager;
import com.example.khaled.Note.models.Crime;
import com.example.khaled.Note.models.CrimeLab;

import java.util.List;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    static InterfaceOnSelectOptionMenuPager mInterfaceOnSelectOption;
    private List<Crime> mCrime;
    Toolbar mToolbar;
private static final String CRIMID_KEY ="com.example.khaled.crime.crimeIDViewPager";

    public static Intent newIntent(Context context, UUID crimeid){
        Intent intent = new Intent(context,ViewPagerActivity.class);
        intent.putExtra(CRIMID_KEY,crimeid);

        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mToolbar=(Toolbar)findViewById(R.id.ToolbarnorecontentID);
        setSupportActionBar(mToolbar);




        mViewPager=(ViewPager)findViewById(R.id.ViewPagerID);
        UUID crimeID =(UUID) getIntent().getSerializableExtra(CRIMID_KEY);

        mCrime = CrimeLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        //fragmentstatepager adapter diffrent the fragmentpageradapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrime.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrime.size();
            }


        });

// to not start the viewpager from the bigaining
        for (int i = 0; i< mCrime.size() ; i++){
            if (mCrime.get(i).getId().equals(crimeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static void setoptionmenu(InterfaceOnSelectOptionMenuPager interfaceOnSelectOption){
       mInterfaceOnSelectOption = interfaceOnSelectOption;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mInterfaceOnSelectOption.onSelectOptionMenu(item ,this);
        switch (item.getItemId()){
            case android.R.id.home:

               /* Crime crime = new Crime();
                for (int x =0 ; x<mCrime.size() ; x++) {
                    if (mCrime.get(x).getTitle().length()>0) {
                        CrimeLab.get(this).addCrime(crime);
                    }
                }*/
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
