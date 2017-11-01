package com.example.khaled.Note.models;

import java.util.Date;
import java.util.UUID;

/**
 * Created by khaled on 15/10/2017.
 */

public class Crime {
    private String mTitle;
    private UUID mId;
    private Date mDate;
    private boolean mSolved;
    private String mContent;
    private String contactnumber;

    public Crime(){
        mId = UUID.randomUUID();
       mDate = new Date();
    }

    public Crime(UUID uuid){
        mId = uuid;
        mDate = new Date();
    }

    public String getPhotoFilename(){

        return "IMG_"+ getId().toString() +".jpg";

    }
    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
      /*  android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
       dateFormat.format("yyyy-MM-dd hh:mm:ss a", mDate);*/
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
