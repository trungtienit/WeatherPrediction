package com.example.trantien.myweather;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by trantien on 11/5/17.
 */

public class Common {
    public static final String KEY_WEATHER="73164d13e349d9bbeb4e510e9978f2ef";
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
