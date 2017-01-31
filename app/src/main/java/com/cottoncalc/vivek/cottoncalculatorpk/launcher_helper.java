package com.cottoncalc.vivek.cottoncalculatorpk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.AsyncTaskCompleteListener;
import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.HttpRequester;
import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.ParseContent;
import com.cottoncalc.vivek.cottoncalculatorpk.utils.AndyConstants;
import com.cottoncalc.vivek.cottoncalculatorpk.utils.AndyUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.HashMap;

public class launcher_helper extends ActionBarActivity implements AsyncTaskCompleteListener {

    private ParseContent parseContent;
    boolean nameNumberEntered;
    SharedPreferences appdata;
    private String playstore_link = "https://play.google.com/store/apps/details?id=com.cottoncalc.vivek.cottoncalculatorpk";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isGooglePlayServicesAvailable(this)){
            parseContent = new ParseContent(this);
            appdata = PreferenceManager.getDefaultSharedPreferences(this);
            nameNumberEntered = appdata.getBoolean("nameNumberEntered",false);

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
                            checkversion();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();

            if(!appdata.getString("version", getString(R.string.VersionNumber)).equals(getString(R.string.VersionNumber))) {
                    final SharedPreferences.Editor editor = appdata.edit();
                    if (appdata.getBoolean("compulsoryUpdate", false)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(launcher_helper.this);
                        builder.setCancelable(false);
                        builder.setTitle("New Version Available");
                        builder.setMessage("Version " + appdata.getString("version", "update") + " is now available. Kindly update to continue using the Oil Mill Calculator");
                        builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("updateClicked", true);
                                editor.commit();
                                finish();
                                Intent viewIntent =
                                        new Intent("android.intent.action.VIEW",
                                                Uri.parse(playstore_link));
                                startActivity(viewIntent);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else if (appdata.getBoolean("recommendedUpdate", false) && appdata.getInt("skipRemaining",0) == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(launcher_helper.this);
                        builder.setCancelable(false);
                        builder.setTitle("New Version Available");
                        builder.setMessage("Version " + appdata.getString("version", "update") + " is now available. Update to get the latest features");
                        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("updateClicked", true);
                                editor.commit();
                                finish();
                                Intent viewIntent =
                                        new Intent("android.intent.action.VIEW",
                                                Uri.parse(playstore_link));
                                startActivity(viewIntent);
                            }
                        });
                        builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putInt("skipRemaining",3);
                                editor.commit();
                                dialog.cancel();
                                continueLaunch();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        editor.putInt("skipRemaining", appdata.getInt("skipRemaining", 0) - 1);
                        editor.commit();
                        continueLaunch();
                    }
                } else {
                    continueLaunch();
                }
            }
        }

    private void checkversion() {
        if (!AndyUtils.isNetworkAvailable(launcher_helper.this)) {
            AndyUtils.showToast(
                    "Internet is not available!",
                    launcher_helper.this);
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.CHECKVERSION);
        map.put(AndyConstants.Params.VERSION, getString(R.string.VersionNumber));

        new HttpRequester(launcher_helper.this, map,
                AndyConstants.ServiceCode.CHECKVERSION, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        SharedPreferences.Editor editor = appdata.edit();

        switch (serviceCode) {
            case AndyConstants.ServiceCode.CHECKVERSION:

                if (parseContent.isSuccess(response)) {
                    ArrayList<HashMap<String, String>> alldetails = parseContent.getDetailVersionCheck(response);
                    if(alldetails.get(0).get(AndyConstants.Params.ISCOMPLUSORY).equals("1"))
                    {
                        System.out.println("Version in res" +alldetails.get(0).get(AndyConstants.Params.VERSIONDB));
                        editor.putBoolean("compulsoryUpdate", true);
                        editor.putString("version", alldetails.get(0).get(AndyConstants.Params.VERSIONDB));
                        editor.commit();
                    }
                    else
                    {
                        System.out.println("Version in res" +alldetails.get(0).get(AndyConstants.Params.VERSIONDB));
                        editor.putBoolean("recommendedUpdate", true);
                        editor.putString("version", alldetails.get(0).get(AndyConstants.Params.VERSIONDB));
                        editor.commit();
                    }
                }
                else
                {
                    String error = parseContent.getErrorCode(response);
                    if(error.equals("Version Match"))
                    {
                        editor.putString("version", getString(R.string.VersionNumber));
                        editor.commit();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void continueLaunch(){
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

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        System.out.println("PLAY SERVICE CHECK");
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }
}
