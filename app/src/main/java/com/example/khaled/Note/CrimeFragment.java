package com.example.khaled.Note;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khaled.Note.activities.ViewPagerActivity;
import com.example.khaled.Note.interfaces.InterfaceOnSelectOptionMenuPager;
import com.example.khaled.Note.models.Crime;
import com.example.khaled.Note.models.CrimeLab;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment implements InterfaceOnSelectOptionMenuPager {

EditText mEditText, mContentText;
    private Toolbar mToolbar;
    Button mDateButtn;
    CheckBox mCheckBox;
    private Crime mCrime;
    private static final int REQUEST_DATE = 0;
    Date mDate;
    private static final String ARG_CRIME_ID = "crime_id";

    private static final String DIALOG_DATE = "DialogDate";

    public CrimeFragment() {
        // Required empty public constructor
    }

    public static CrimeFragment newInstance(UUID crimeIDARG){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeIDARG);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPagerActivity.setoptionmenu(this);


        /*

        Arguments

        send the data from Activty be to activity c by Intent
        then get the data from the activty c throw Arguments
        and send it to the Fragment of activity c by agrement

        Intent
        getExtra
        argument
        put
        argument
        get


         */


       /* this was for intent UUID CrimeID =(UUID) getActivity().getIntent()
                .getSerializableExtra(MainActivity.Crime_ID_KEY);*/
       UUID CrimeID =(UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(CrimeID);
    }

//add for database
    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime, container, false);










        mCheckBox=(CheckBox)v.findViewById(R.id.crime_solvedCheckID);
       // mCheckBox.setChecked(mCrime.isSolved());
        mDateButtn=(Button)v.findViewById(R.id.crime_dateBtnID);
        mEditText =(EditText)v.findViewById(R.id.EditTextFragmentID);
        mEditText.setText(mCrime.getTitle());
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                     mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       // mDateButtn.setText(mCrime.getDate().toString());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(mCrime.getDate());
        mDateButtn.setText(date);
        //mDateButtn.setEnabled(false);
        mCheckBox.setChecked(mCrime.isSolved());

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(true);

            }
        });

        mDateButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDate = mCrime.getDate();
                FragmentManager fragmentManager = getFragmentManager();
                DialogPickerFragment dialogPickerFragment = DialogPickerFragment.newInstace(mDate);
                dialogPickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialogPickerFragment.show(fragmentManager ,DIALOG_DATE );

            }
        });

        mContentText =(EditText)v.findViewById(R.id.NoteContentID);
        mContentText.setText(mCrime.getContent());
        mContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   mCrime.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE){
            Date date =(Date)data
                    .getSerializableExtra(DialogPickerFragment.DATE_KEY_BACK);
            mCrime.setDate(date);
        }
        DateUpdate();

    }



    private void DateUpdate(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(mCrime.getDate());
       // mDateButtn.setText(mCrime.getDate().toString());

        mDateButtn.setText(date);
    }


    @Override
    public void onSelectOptionMenu(MenuItem item, ViewPagerActivity viewPagerActivity) {

            int id = item.getItemId();
            if (id== R.id.sharenotefragmentID)
                Toast.makeText(getActivity(), "share", Toast.LENGTH_SHORT).show();



    }
}
