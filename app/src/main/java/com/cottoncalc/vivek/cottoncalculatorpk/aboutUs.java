package com.cottoncalc.vivek.cottoncalculatorpk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.AsyncTaskCompleteListener;
import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.HttpRequester;
import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.ParseContent;
import com.cottoncalc.vivek.cottoncalculatorpk.utils.AndyConstants;
import com.cottoncalc.vivek.cottoncalculatorpk.utils.AndyUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class aboutUs extends ActionBarActivity implements View.OnClickListener, AsyncTaskCompleteListener {

    public String IMEI;
    public String VersionNumber;
    TextView trial;
    TextView internetTxt;
    TextView detailsTxt;

    Button final_activate;
    Boolean FullVersionActive;
    SharedPreferences appdata;
    int daysLeft;
    ProgressDialog progress;
    private ParseContent parseContent;
    private ArrayList<HashMap<String,String>> alldetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 0, 121, 107)));

        final_activate = (Button) findViewById(R.id.final_activate);
        final_activate.setOnClickListener(this);


        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = appdata.edit();

        int daysLeft = appdata.getInt("daysLeft", 0);
        if(daysLeft==0)
        {
            editor.putBoolean("FullVersionActive", false);
            editor.commit();
        }
        else if(daysLeft== -1)
        {
            editor.putBoolean("FullVersionActive", true);
            editor.commit();
        }

        FullVersionActive = appdata.getBoolean("FullVersionActive", false);
        trial = (TextView) findViewById(R.id.trialDays);
        internetTxt = (TextView) findViewById(R.id.textView6);
        detailsTxt = (TextView) findViewById(R.id.textView5);
        if(FullVersionActive)
        {
            trial.setText("Full Version!");
            trial.setTextColor(Color.parseColor("#ff008174"));
            final_activate.setEnabled(false);

            final_activate.setVisibility(View.GONE);
            internetTxt.setVisibility(View.GONE);
            detailsTxt.setVisibility(View.GONE);

        }
        else
        {
            trial.setText("Trial Version");
            trial.setTextColor(Color.parseColor("#ffc00200"));
            final_activate.setEnabled(true);
            final_activate.setVisibility(View.VISIBLE);
            internetTxt.setVisibility(View.VISIBLE);
            detailsTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();
        VersionNumber = getString(R.string.VersionNumber);

        parseContent = new ParseContent(this);

        progress = new ProgressDialog(this);
        progress.setTitle("Activating Full Version");
        progress.setMessage("Wait while contacting server...");
        progress.setCancelable(false);
        progress.show();

        final boolean[] bool = {true};
        final Handler handler = new Handler();
        final Thread thread = new Thread() {
            @Override

            public void run() {
                try {
                    while(bool[0]) {
                        Looper.prepare();
                        handler.post(this);
                        bool[0] = false;
                        checkDaysLeftonServer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void checkDaysLeftonServer() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        if (!AndyUtils.isNetworkAvailable(aboutUs.this)) {
            AndyUtils.showToast(
                    "Internet not available!",
                    aboutUs.this);
            progress.dismiss();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.LOGIN);

        map.put(AndyConstants.Params.IMEI, IMEI);
        map.put(AndyConstants.Params.VERSION, VersionNumber);
        map.put(AndyConstants.Params.DAYS_LEFT, String.valueOf(daysLeft));
        map.put(AndyConstants.Params.NOTIFICATION_TOKEN, appdata.getString("firebase_token", null));
        map.put(AndyConstants.Params.LAST_ACCESS, currentDateTimeString);

        new HttpRequester(aboutUs.this, map,
                AndyConstants.ServiceCode.LOGIN, this);
    }
    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case AndyConstants.ServiceCode.LOGIN:

                if (parseContent.isSuccess(response)) {
                    alldetails = parseContent.getDetaillogin(response);
                    if(Integer.parseInt(alldetails.get(0).get(AndyConstants.Params.DAYS_LEFT)) == -1)
                    {
                        progress.dismiss();
                        activate();
                    }
                    else
                    {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Not Activated by Admin", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }else {
                    String msg = parseContent.getErrorCode(response);
                    AndyUtils.showToast(
                            msg,
                            aboutUs.this);
                    Log.d("msg", msg);
                }

                break;
            default:
                break;
        }
    }

    public void activate(){
        runOnUiThread(new Runnable() {
            public void run() {
                SharedPreferences.Editor editor = appdata.edit();
                editor.putBoolean("trialExpired", false);
                editor.putBoolean("FullVersionActive", true);
                editor.putInt("daysLeft", -1);
                editor.commit();

                trial.setText("FULL VERSION");
                trial.setTextColor(Color.parseColor("#ff008174"));
                final_activate.setEnabled(false);

                AlertDialog.Builder builder = new AlertDialog.Builder(aboutUs.this);
                builder.setCancelable(false);
                builder.setTitle("Congratulations");
                builder.setMessage("Full Version now Active");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
