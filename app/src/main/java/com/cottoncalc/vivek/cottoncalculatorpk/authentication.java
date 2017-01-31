package com.cottoncalc.vivek.cottoncalculatorpk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;

/**
 * Created by Hitu on 17/07/2015.
 */
public class authentication extends ActionBarActivity implements View.OnClickListener{
    public String IMEI;
    public String VersionNumber;
    public String operatorName;
    public String simID;
    int daysLeft = 0;
    private String locationString;
    private LocationManager locationManager;
    private LocationListener locationListener;
    boolean network_enabled = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        operatorName = telephonyManager.getNetworkOperatorName();
        IMEI = telephonyManager.getDeviceId();
        simID = telephonyManager.getSimSerialNumber();

        setContentView(R.layout.activity_authentication);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 0, 121, 107)));
        EditText name = (EditText) findViewById(R.id.name);
        name.requestFocus();

        Button finalEnter = (Button) findViewById(R.id.finalEnter);
        finalEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.finalEnter:

                EditText name = (EditText) findViewById(R.id.name);
                EditText number = (EditText) findViewById(R.id.number);
                int numberLength = number.getText().length();
                final String nameS = name.getText().toString();
                final String numberS = number.getText().toString();
                if(nameS.equals("")  || nameS.equals(null))
                {
                    Toast.makeText(getApplicationContext(), "Enter a Name", Toast.LENGTH_LONG).show();

                }
                else if(numberS.equals("")  || numberS.equals(null) || numberLength<10)
                {
                    Toast.makeText(getApplicationContext(),"Enter a Valid Phone Number",Toast.LENGTH_LONG).show();
                }
                else
                {

                    final parse obj = new parse();

                    final ProgressDialog progress = new ProgressDialog(this);
                    progress.setTitle("Activating Free Trial");
                    progress.setMessage("Wait while contacting server...");
                    progress.setCancelable(false);
                    progress.show();

                    final boolean[] bool = {true};
                    final Handler handler = new Handler();
                    final Thread thread = new Thread() {
                        @Override

                        public void run() {
                            try {
                                VersionNumber = getString(R.string.VersionNumber);
                                while(bool[0]) {
                                    int returnCode = obj.queryParseIMEI(IMEI,VersionNumber);
                                    Looper.prepare();
                                    handler.post(this);
                                    bool[0] = false;
                                    if(returnCode == -404)
                                    {
                                        progress.dismiss();
                                        Log.d("scores", "No Internet -404");
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    else if(returnCode == -101)
                                    {
                                        Log.d("scores", "Return No Entry -101");
                                        //Here no entry, Hence make a new entry with or without location

                                        /*locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                                        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                                        if(network_enabled)
                                        {
                                            Log.d("scores", "Location Enabled");
                                            locationListener = new LocationListener() {
                                                public void onLocationChanged(Location location) {
                                                    locationString = location.toString();
                                                    SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                    String key = appdata.getString("keyUsed", "Trial Till 15-Dec-2015");

                                                    //PARSE SAVE CODE
                                                    try {
                                                        Log.d("scores", "Trying to Save with Location");
                                                        obj.savetoParse(nameS,numberS,key,IMEI,locationString,simID,operatorName,VersionNumber);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    locationManager.removeUpdates(locationListener);
                                                }

                                                public void onStatusChanged(String provider, int status, Bundle extras) {
                                                }

                                                public void onProviderEnabled(String provider) {
                                                }

                                                public void onProviderDisabled(String provider) {
                                                }
                                            };
                                            // Register the listener with the Location Manager to receive location updates
                                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                                        }*/
                                        //else

                                            SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            String key = appdata.getString("keyUsed", "Trial Parse 20Dec");
                                            obj.savetoParse(nameS,numberS,key,IMEI,"Location Disabled",simID,operatorName,VersionNumber);


                                        //Calling to check saved entry and get days left
                                        //if(result == 1){
                                                int returnCodeAfterSave = obj.queryParseIMEI(IMEI,VersionNumber);

                                                if(returnCodeAfterSave == -404)
                                                {
                                                    progress.dismiss();
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                                else if(returnCodeAfterSave == -101)
                                                {
                                                    Log.d("scores", "Return No Entry -101");

                                                    progress.dismiss();
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            Toast.makeText(getApplicationContext(), "Error at server. Try again later.", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                                else
                                                {
                                                    daysLeft = returnCodeAfterSave;
                                                    SharedPreferences.Editor editor = appdata.edit();
                                                    editor.putInt("daysLeft", daysLeft);
                                                    editor.putBoolean("parseEntryDone", true);
                                                    editor.putInt("userRegistered", 0);
                                                    editor.commit();
                                                    progress.dismiss();


                                                    Intent myIntent = new Intent(authentication.this, MainActivity.class);
                                                    startActivity(myIntent);
                                                    finish();
                                                }
                                       // }
                                    }
                                    else
                                    {
                                        progress.dismiss();
                                        daysLeft = returnCode;
                                        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = appdata.edit();
                                        editor.putInt("daysLeft", daysLeft);
                                        editor.putBoolean("parseEntryDone", true);
                                        editor.putInt("userRegistered", 0);
                                        editor.commit();
                                        Log.d("scores", String.valueOf(daysLeft));

                                        Intent myIntent = new Intent(authentication.this, MainActivity.class);
                                        startActivity(myIntent);
                                        finish();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();

                    SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = appdata.edit();
                    editor.putString("userName", nameS);
                    editor.putString("phnNumber", numberS);
                    editor.putBoolean("nameNumberEntered",true);
                    editor.commit();
                }
                break;
        }
    }
}
