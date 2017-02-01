package com.cottoncalc.vivek.cottoncalculatorpk.utils;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Parsania Hardik on 19/01/2016.
 */
public class AndyConstants {
    public static boolean loginorout = false;  //if false then logout else login
    public static final String URL = "url";
    public static final String PREF_NAME = "sharedPref";
    public static final String MANUAL = "manual";
    public static final String MENUFRAGMENT = "menufragment";
    public static final String MOREFRAGMENT = "morefragment";
    public static final String EVENTFRAGMENT = "eventfragment";
    public static final String POLLFRAGMENT = "pollfragment";
    public static final String BLOGFRAGMENT = "blogfragment";
    public static final String PROMOTIONFRAGMENT = "promotionfragment";
    public static final String DEALFRAGMENT = "hotdealfragment";
    public static final String SEARCHRESFRAGMENT = "searchresfragment";
    public static final String ONLINEORDERFRAGMENT = "onlineorderfragment";
    public static final String SAVEORDERFRAGMENT = "saveorderfragment";
    public static final String GALLERYFRAGMENT = "galleryfragment";
    public static boolean FROMACC = false;
    public static boolean FROMTRAN = false;
    public static boolean DIALOG= false;
    public static boolean FILTER= false;
    public static boolean SAVEORDERONCE = false;
    public static boolean PROGRESS = false;
    public static  double latitude = 0;
    public static  double longituude = 0;
    public static String IMEI="";
    public static String LATESTGROUP="";
    public static String LANDSCAPBAL="";
    public static String STARTDATE="";
    public static String ENDDATE = "";
    public static String STARTDATEPROPER="";
    public static String ENDDATEPROPER="";
    public static String ENDDATEPROPER22="";
    public static String STARTDATEPROPER22="";
    public static HashMap<String,String> FINALMAP;
    public static ArrayList<HashMap<String,String>> FINALITEMMAP;
    public static ArrayList<HashMap<String,String>> FINALTOPPINGMAP = new ArrayList<>();



    public static final int CHOOSE_PHOTO = 1;
    public static final int TAKE_PHOTO = 2;
    public static Uri TAKENPHOTOURI = null;


    public class ServiceCode {
        public static final int REGISTER = 1;
        public static final int LOGIN = 2;
        public static final int CHECKVERSION = 3;
        public static final int CHECKIMEI = 4;
    }

    // web service url constants
    public class ServiceType {
        public static final String BASE_URL = "http://madeovercode.com/pkcottoncalc/service/";
        public static final String LOGIN = BASE_URL + "login.php";
        public static final String REGISTERATION = BASE_URL + "register.php";
        public static final String CHECKVERSION = BASE_URL + "checkversion.php";
        public static final String CHECKIMEI = BASE_URL + "checkIMEI.php";

    }
    // webservice key constants
    public class Params {
        public static final String EMAIL = "email";
        public static final String IMEI = "imei";
        public static final String NAME = "name";
        public static final String MOBILE = "mobile";
        public static final String NOTIFICATION_TOKEN = "notification_token";
        public static final String DAYS_LEFT = "days_left";
        public static final String LAST_ACCESS = "last_access";
        public static final String INSTALL_DATE = "install_date";
        public static final String CITY = "city";
        public static final String ID = "id";
        public static final String VERSION = "version";
        public static final String VERSIONDB = "versionDB";
        public static final String ISCOMPLUSORY = "isCompulsory";
        public static final String ISACTIVE = "isActive";
        }
}

