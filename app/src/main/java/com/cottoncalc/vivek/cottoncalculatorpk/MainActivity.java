package com.cottoncalc.vivek.cottoncalculatorpk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.AsyncTaskCompleteListener;
import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.HttpRequester;
import com.cottoncalc.vivek.cottoncalculatorpk.htmlparse.ParseContent;
import com.cottoncalc.vivek.cottoncalculatorpk.utils.AndyConstants;
import com.cottoncalc.vivek.cottoncalculatorpk.utils.AndyUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AsyncTaskCompleteListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public String VersionNumber;
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/1q0ahngKQEHweCWHlZhCSb66xKstETwG2O4hJMM2B9TU/formResponse";
    //input element ids found from the live form page
    public static final String NAME_KEY="entry_155989187";
    public static final String PHN_KEY="entry_1982580765";
    public static final String KEY_KEY="entry_2058375361";
    public static final String LOCATION_KEY="entry_851352356";
    public static final String IMEI_KEY="entry_1909631967";
    public static final String SIM_KEY="entry_652569562";
    public static final String SIM_PROVIDER_KEY="entry_2133343088";
    public static final String VERSION="entry_2089304934";

    public int userRegistered;
    public String operatorName;
    public String IMEI;
    public String simID;
    int daysLeft = 0;

    private String locationString;

    private LocationManager locationManager;
    private LocationListener locationListener;
    boolean network_enabled = false;
    boolean isExpired = false;

    private ParseContent parseContent;
    private ArrayList<HashMap<String,String>> alldetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 0, 121, 107)));
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //FOR FORCEFUL ACTIONBAR MENU
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

        final SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean parseEntryDone = appdata.getBoolean("parseEntryDone", false);
        if(parseEntryDone == false)
        {
            Intent myIntent = new Intent(MainActivity.this, authentication.class);
            startActivity(myIntent);
            finish();
        }

        parseContent = new ParseContent(this);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        operatorName = telephonyManager.getNetworkOperatorName();
        IMEI = telephonyManager.getDeviceId();
        simID = telephonyManager.getSimSerialNumber();


        daysLeft = appdata.getInt("daysLeft", 0);
        if(daysLeft == 0)
            isExpired = true;
        else if(daysLeft != 0)
            isExpired = false;
        if(isExpired)
        {
            SharedPreferences.Editor editor = appdata.edit();
            editor.putBoolean("trialExpired", true);
            editor.commit();
        }
        else
        {
            SharedPreferences.Editor editor = appdata.edit();
            editor.putBoolean("trialExpired", false);
            editor.commit();
        }

        decreaseDaysLeftinSP();
        VersionNumber = getString(R.string.VersionNumber);


        final boolean[] bool = {true};
        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (bool[0]) {
                        bool[0] = false;
                        checkDaysLeftonServer();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }; thread.start();
    }

    private void decreaseDaysLeftinSP()
    {
        System.out.println("DECREASE DAYS");
        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = appdata.edit();
        Calendar calendar = new GregorianCalendar();
        int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int lastAccessDayOfMonth = appdata.getInt("lastAccessDayofMonth", 0);
        System.out.println("Last Access Day: "  +lastAccessDayOfMonth);
        if(todayDayOfMonth != lastAccessDayOfMonth && lastAccessDayOfMonth != 0)
        {
            daysLeft = appdata.getInt("daysLeft", 0);
            System.out.println("DAYS LEFT :" +daysLeft);
            if(daysLeft>0){
                daysLeft = daysLeft - 1;
            }
            editor.putInt("daysLeft", daysLeft);
            System.out.println("DAYS PUT: " +daysLeft);
        }
        lastAccessDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        editor.putInt("lastAccessDayofMonth", lastAccessDayOfMonth);
        editor.commit();
    }

    private void checkDaysLeftonServer() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        if (!AndyUtils.isNetworkAvailable(MainActivity.this)) {
            AndyUtils.showToast(
                    "Internet is not available!",
                    MainActivity.this);
            return;
        }
        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AndyConstants.URL, AndyConstants.ServiceType.LOGIN);

        map.put(AndyConstants.Params.IMEI, IMEI);
        map.put(AndyConstants.Params.VERSION, VersionNumber);
        map.put(AndyConstants.Params.DAYS_LEFT, String.valueOf(daysLeft));
        map.put(AndyConstants.Params.LAST_ACCESS, currentDateTimeString);
        map.put(AndyConstants.Params.NOTIFICATION_TOKEN, appdata.getString("firebase_token", null));

        new HttpRequester(MainActivity.this, map,
                AndyConstants.ServiceCode.LOGIN, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case AndyConstants.ServiceCode.LOGIN:

                if (parseContent.isSuccess(response)) {

                    SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = appdata.edit();

                    alldetails = parseContent.getDetaillogin(response);
                    System.out.println("DAYS LEFT HTTP SERVER: " + alldetails.get(0).get(AndyConstants.Params.DAYS_LEFT));
                    editor.putInt("daysLeft", Integer.parseInt(alldetails.get(0).get(AndyConstants.Params.DAYS_LEFT)));
                    editor.commit();
                }else {
                    String msg = parseContent.getErrorCode(response);
                    AndyUtils.showToast(
                            msg,
                            MainActivity.this);
                    Log.d("msg", msg);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        userRegistered = appdata.getInt("userRegistered", 0);

        if (userRegistered == 0)
        {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(network_enabled)
            {
                locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        //             Log.d("loc", "Inside LocationChanged");
                        locationString = location.toString();
                        SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        String name = appdata.getString("userName", "");
                        String number = appdata.getString("phnNumber", "");
                        String key = appdata.getString("keyUsed", "Web Service 1 (Jan 31,2017)");

                        PostDataTask postDataTask = new PostDataTask();
                        postDataTask.execute(URL, name, number, key, locationString, IMEI, simID, operatorName, VersionNumber);
                        Log.d("loc", "POST CALLED");
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
            }
            else
            {
                String name = appdata.getString("userName", "");
                String number = appdata.getString("phnNumber", "");
                String key = appdata.getString("keyUsed", "Web Service 1 (Jan 31,2017)");

                PostDataTask postDataTask = new PostDataTask();
                postDataTask.execute(URL, name, number, key, "Location Disabled", IMEI, simID, operatorName, VersionNumber);
                Log.d("loc", "POST CALLED WITHOUT LOCATION");
            }
        }

        daysLeft = appdata.getInt("daysLeft", 0);
        if(daysLeft == 0)
            isExpired = true;
        else if(daysLeft != 0)
            isExpired = false;
        if(isExpired)
        {
            SharedPreferences.Editor editor = appdata.edit();
            editor.putBoolean("trialExpired", true);
            editor.commit();
        }
        else
        {
            SharedPreferences.Editor editor = appdata.edit();
            editor.putBoolean("trialExpired", false);
            editor.commit();
        }
    }

    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String Name = contactData[1];
            String Phone = contactData[2];
            String Key = contactData[3];
            String location = contactData[4];
            String IMEI = contactData[5];
            String simID = contactData[6];
            String operatorName = contactData[7];
            String versionNumber = contactData[8];
            String postBody = "";
            Log.d("loc", "POST FUNCTION START");
            try {
                Log.d("loc", "TRY BLOCK1");
                //all values must be URL encoded to make sure that special characters like & | ",etc.
                //do not cause problems
                postBody = NAME_KEY + "=" + URLEncoder.encode(Name, "UTF-8") +
                        "&" + PHN_KEY + "=" + URLEncoder.encode(Phone, "UTF-8") +
                        "&" + KEY_KEY + "=" + URLEncoder.encode(Key, "UTF-8") +
                        "&" + LOCATION_KEY + "=" + URLEncoder.encode(location, "UTF-8") +
                        "&" + IMEI_KEY + "=" + URLEncoder.encode(IMEI, "UTF-8") +
                        "&" + SIM_KEY + "=" + URLEncoder.encode(simID, "UTF-8") +
                        "&" + SIM_PROVIDER_KEY + "=" + URLEncoder.encode(operatorName, "UTF-8") +
                        "&" + VERSION + "=" + URLEncoder.encode(versionNumber, "UTF-8");

            } catch (UnsupportedEncodingException ex) {
                result = false;
            }

            try {
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                //Send the request
                Response response = client.newCall(request).execute();

            } catch (IOException exception) {
                result = false;
            }
            if (result == false) {
                SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = appdata.edit();
                editor.putInt("userRegistered", 0);
                editor.commit();
                Log.d("loc", "changed to 0");
                /*TextView registeredCheck = (TextView) findViewById(R.id.registeredCheckTxt);
                registeredCheck.setText(appdata.getString("userRegistered", ""));*/
            } else {
                SharedPreferences appdata = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = appdata.edit();
                editor.putInt("userRegistered", 1);
                editor.commit();
               // Log.d("loc", "changed to 1");
                /*TextView registeredCheck = (TextView) findViewById(R.id.registeredCheckTxt);
                registeredCheck.setText(appdata.getString("userRegistered", ""));*/
            }
            //Log.d("loc", String.valueOf(result));
            return result;
        }
    }   //Async POST END

        @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFragment = null;
            ActionBar actionBar = getSupportActionBar();
        switch (position)
        {
            case 0:
                objFragment = new ginning_Fragment();
                mTitle = getString(R.string.title_section1);
                actionBar.setTitle(mTitle);
                break;
            case 1:
                objFragment = new import_Fragment();
                mTitle = getString(R.string.title_section2);
                actionBar.setTitle(mTitle);
                break;
            case 2:
                objFragment = new oilCake_fragment();
                mTitle = getString(R.string.title_section3);
                actionBar.setTitle(mTitle);
                break;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
