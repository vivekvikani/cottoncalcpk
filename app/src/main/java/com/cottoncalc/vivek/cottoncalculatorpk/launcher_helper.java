package com.cottoncalc.vivek.cottoncalculatorpk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;

import com.parse.Parse;

public class launcher_helper extends ActionBarActivity {
 //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this);
        boolean nameNumberEntered = appdata.getBoolean("nameNumberEntered",false);

        if(!nameNumberEntered)
        {
            Intent myIntent = new Intent(launcher_helper.this, authentication.class);
            launcher_helper.this.startActivity(myIntent);
            finish();
        }
        else
        {
            Intent myIntent = new Intent(launcher_helper.this, MainActivity.class);
            launcher_helper.this.startActivity(myIntent);
            finish();
        }
    }
}
