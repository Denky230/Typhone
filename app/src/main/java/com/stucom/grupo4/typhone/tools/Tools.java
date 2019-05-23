package com.stucom.grupo4.typhone.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Tools {

    public static void log(String message) {
        Log.d("tydebug", message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
}