package com.example.khaled.Note;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment /*implementsInterfaceOnSelectOptionMenuPager*/ {

EditText mEditText, mContentText;
    public Toolbar mToolbar;
    Button mDateButtn ,ChooseContactbtn;
    CheckBox mCheckBox;
    private Crime mCrime;
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT =1;
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
       // ViewPagerActivity.setoptionmenu(this);

        Log.d(ViewPagerActivity.TAG,"OnCreateFragment.........");


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

        Log.d(ViewPagerActivity.TAG,"onPaused Fragment");

        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(ViewPagerActivity.TAG,"OnAttach.....");
    }

    @Override
    public void onStart() {
        Log.d(ViewPagerActivity.TAG,"onStart...........");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(ViewPagerActivity.TAG,"onStoop............");
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        setHasOptionsMenu(true);
        Log.d(ViewPagerActivity.TAG,"onCreateView....");

  mToolbar =(Toolbar)v.findViewById(R.id.ToolbarnorecontentID);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle("Khaled");
        mToolbar.inflateMenu(R.menu.menu_note_content);



        ChooseContactbtn=(Button)v.findViewById(R.id.choosecontactbtnID);
        if (mCrime.getContactnumber()!=null) {
            ChooseContactbtn.setText(mCrime.getContactnumber());
        }
        final Intent intentcontact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        ChooseContactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intentcontact , REQUEST_CONTACT);


            }
        });

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(intentcontact ,PackageManager.MATCH_DEFAULT_ONLY)==null){
            ChooseContactbtn.setEnabled(false);
        }








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
            DateUpdate();
        }else if (requestCode == REQUEST_CONTACT && data!=null){
            Uri contactUri = data.getData();
            String[] query = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getActivity().getContentResolver().query(contactUri, query,null,null,null);

            try {
                if (c.getCount()==0){
                    return;
                }

                c.moveToFirst();
                String contact = c.getString(0);
                mCrime.setContactnumber(contact);
                ChooseContactbtn.setText(contact);
            }finally {
                c.close();
            }

        }


    }



    private void DateUpdate(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(mCrime.getDate());
       // mDateButtn.setText(mCrime.getDate().toString());

        mDateButtn.setText(date);
    }


   /* @Override
    public void onSelectOptionMenu(MenuItem item, ViewPagerActivity viewPagerActivity) {

            int id = item.getItemId();
            if (id== R.id.sharenotefragmentID)
                Toast.makeText(getActivity(), "share", Toast.LENGTH_SHORT).show();



    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_note_content, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id== R.id.sharenotefragmentID){
            Toast.makeText(getActivity(), "viewpager", Toast.LENGTH_SHORT).show();
            Log.d(ViewPagerActivity.TAG,"share pressed");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT , getNoteContent());
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
            intent = Intent.createChooser( intent , getString(R.string.send_report));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


 private String getNoteContent(){
     String checksolved = null;
     if (mCrime.isSolved()){
         checksolved= getString(R.string.crime_report_solved);
     }else{
         checksolved=getString(R.string.crime_report_unsolved);
     }

     String dateFormat = "EEE, MMM dd - yyyy";
     String dateString = (String) android.text.format.DateFormat.format( dateFormat,mCrime.getDate());

     String Contactnumber= mCrime.getContactnumber();
     if (Contactnumber == null){
         Contactnumber= getString(R.string.crime_report_no_suspect);
     }else {
         Contactnumber = getString(R.string.crime_report_suspect , Contactnumber);
     }

     String sharecontent = getString(R.string.share_note, mCrime.getTitle(), mCrime.getContent(), dateString ,checksolved, Contactnumber);

     return sharecontent;
 }
}
