package com.example.khaled.Note.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by khaled on 11/4/2017.
 */

public class PicUtils {

    public static Bitmap getScaledPic(String path , Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return null;
    }

    public static Bitmap getScaledPic( String path , int destWidth , int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path , options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;

        if (srcHeight> destHeight || srcWidth  >destWidth){
            if (srcWidth> srcHeight){
                inSampleSize = Math.round(srcHeight / destWidth);
            }else {
                inSampleSize= Math.round(srcWidth / srcHeight);
            }
        }



        return null;

    }
}
