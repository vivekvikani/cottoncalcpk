package com.cottoncalc.vivek.cottoncalculatorpk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Parsania Hardik on 19/01/2016.
 */
public class PreferenceHelper {

    private final String USER_ID = "user_id";
    private final String PASSWORD = "Password";
    private final String EMAIL = "Email";
    private final String FIRSTNAME = "Firstname";
    private final String LASTNAME = "Lastname";
    public static final String LOGINOROUT = "loginorout";
    private final String ORDERID = "orderid";
    private final String ISFILTER = "isfilter";
    private final String CITY = "City";
    private final String STATE = "State";
    private final String COUNTRY = "Country";
    private final String BIODATA = "Biodata";
    private final String FLATRATEPRICE = "Flatrateprice";
    private final String TIMEBLOCK = "Timeblock";
    public final String AVAILIBILITY = "Availibility";
    public final String OCCUPATION = "Occupation";
    public final String SKYPEID = "Skypeid";
    public final String USERTYPE = "UserType";
    private final String PROFILEPICTURE = "Profilepicture";

    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences(AndyConstants.PREF_NAME,
                Context.MODE_PRIVATE);
        this.context = context;
    }

    //loginorout
    public void putsqliteexpirydate(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(OCCUPATION, loginorout);
        edit.commit();
    }
    public String getsqliteexpirydate() {
        return app_prefs.getString(OCCUPATION, "");
    }

    //loginorout
    public void putexpirydate(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(AVAILIBILITY, loginorout);
        edit.commit();
    }
    public String getexpirydate() {
        return app_prefs.getString(AVAILIBILITY, null);
    }

    //loginorout
    public void putotpdoneornot(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(TIMEBLOCK, loginorout);
        edit.commit();
    }
    public Boolean getotpdoneornot() {
        return app_prefs.getBoolean(TIMEBLOCK, false);
    }

    public void putcurrentvalidity(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(FLATRATEPRICE, email);
        edit.commit();
    }

    public String getcurrentvalidity() {
        return app_prefs.getString(FLATRATEPRICE, null);
    }

    //loginorout
    public void putaccessornot(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(BIODATA, loginorout);
        edit.commit();
    }
    public Boolean getaccessornot() {
        return app_prefs.getBoolean(BIODATA, true);
    }

    //loginorout
    public void putshouldlogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(LOGINOROUT, loginorout);
        edit.commit();
    }
    public Boolean getshouldlogin() {
        return app_prefs.getBoolean(LOGINOROUT, false);
    }

   //loginorout
    public void putisfilter(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(ISFILTER, loginorout);
        edit.commit();
    }
    public Boolean getisfilter() {
        return app_prefs.getBoolean(ISFILTER, true);
    }

    public void putispasscode(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(COUNTRY, loginorout);
        edit.commit();
    }
    public Boolean getispasscode() {
        return app_prefs.getBoolean(COUNTRY, false);
    }

      //user email
    public void putEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }

    public String getEmail() {
        return app_prefs.getString(EMAIL, null);
    }

     //user saveorder
    public void putMobile(String password) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PASSWORD, password);
        edit.commit();
    }
    public String getMobile() {
        return app_prefs.getString(PASSWORD, null);
    }

    //lastname
    public void putname(String lastname) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(LASTNAME, lastname);
        edit.commit();
    }
    public String getname() {
        return app_prefs.getString(LASTNAME, null);
    }


    //firstname
    public void putcity(String firstname) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(FIRSTNAME, firstname);
        edit.commit();
    }
    public String getcity() {
        return app_prefs.getString(FIRSTNAME, null);
    }

    //orderid
   public void putdateformat(String username) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ORDERID, username);
        edit.commit();
    }
    public String getdateformat() {
        return app_prefs.getString(ORDERID, "dd/mm/yyyy");
    }

    //mobilenum
    public void putcrrency(String mobilenum) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CITY, mobilenum);
        edit.commit();
    }
    public String getcurrency() {
        return app_prefs.getString(CITY, "INR");
    }

    //city
    public void putuname(String city) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(STATE, city);
        edit.commit();
    }
    public String getuname() {
        return app_prefs.getString(STATE, "Name");
    }

    //state
    public void putpasscode(String state) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(SKYPEID, state);
        edit.commit();
    }
    public String getpasscode() {
        return app_prefs.getString(SKYPEID, null);
    }

}

