package com.example.cryptoapp;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class app extends Application{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Configuration newConfig= new Configuration();
        newConfig.locale= Locale.ENGLISH;
        super.onConfigurationChanged(newConfig);
        Locale.setDefault(newConfig.locale);
        getBaseContext().getResources().updateConfiguration(newConfig,getResources().getDisplayMetrics());
    }
    public static void Logger(String m)
    {
        Log.e("mip tag",m);
    }
    public static void ToastMaker(Context c, String m)
    {
        Toast.makeText(c,m,Toast.LENGTH_SHORT).show();
    }

    public static void DialogMaker(Context c, String title, String msg)
    {
        AlertDialog.Builder alert= new AlertDialog.Builder(c);
        alert.setCancelable(true);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

}
