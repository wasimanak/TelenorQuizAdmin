package com.softarena.telenorquizadmin;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHelperClass {
    public String getYYYYMMDD(String date){
        String d1=date.replace("-","");
        String d2=d1.replaceAll("/","");

        return d2.trim();
    }

    public String removeSpecialCharectersAndTrim(String str){
        String d1=str.replaceAll("[^a-zA-Z0-9]", "");

        return d1.trim();
    }
    public String removeSpecialCharectersAndAddSpace(String str){
        String d1=str.replaceAll("[^a-zA-Z0-9]", " ");
        return d1.trim();
    }
    public boolean checkSpecialChar(String str){
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(str);

        boolean b = m.find();
        if (b)
           return true;
        else {
            return false;
        }
    }
    public static String getCurrentDateHHMMSS_24(){
        String currentDateAndTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return  currentDateAndTime;


    }
    public static String getCurrentDateHHMMSS_12(){
        String currentDateAndTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String[] str1=currentDateAndTime.split(":");
        String newdate="";
        String hours="";
        if (Integer.valueOf(str1[0])<=12) {
            hours = str1[0];
            newdate=hours+":"+str1[1]+":"+str1[2]+" "+"pm";
        }else {
            hours=String.valueOf(Integer.valueOf(str1[0])-12);
            newdate=hours+":"+str1[1]+":"+str1[2]+" "+"pm";
        }

        return  newdate;


    }
    public static String getCurrentDateYYYYMMDD_Dashes(){
        String currentDateAndTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return  currentDateAndTime;


    }
    public static String getCurrentDateYYYYMMDD(){
        String currentDateAndTime = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return  currentDateAndTime;


    }
    public  String getCurrentDateYYYYMMDDhhmmsss(){
        String currentDateAndTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        return currentDateAndTime;
    }
    public String removeSpecialCharectersAndAmPmFromTime(String time){
        String t1=time.replaceAll(":","");
        String t2 = t1;
        if (t2.contains("am") || t2.contains("AM"))
        {
            t2=time.toLowerCase().replaceAll("am","");
        }
        if (t2.contains("pm") || t2.contains("PM"))
        {
            t2=time.toLowerCase().replaceAll("pm","");
        }
        return t2.trim();
    }
    public String getHtmlFormatedText(String message){

        String htmltextstart="<HTML>";
        String htmltextclose="</HTML>";
        String bodytextstart="<body>";
        String bodytextclose="</body>";

        return htmltextstart+bodytextstart+message+bodytextclose+htmltextclose;

    }
    public String toBase64(Bitmap bitmap){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 3508, 2480, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public String getTravelModedriving(){


        return ",13z/data=!3m1!4b1!4m2!4m1!3e0";
    }
    public String getTravelModewalking(){


        return ",14z/data=!3m1!4b1!4m2!4m1!3e2";
    }
    public String getTravelModebicycling(){


        return ",14z/data=!3m1!4b1!4m2!4m1!3e2";
    }
    public String getTravelModetransit(){


        return ",12z/data=!3m1!4b1!4m2!4m1!3e3";
    }
    public long timeToMillis_yyyymmddhhmmss(String yyyymmddhhmmss){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(yyyymmddhhmmss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        return millis;
    }
    public boolean cnicValidate(String txtNic){
        if(!(txtNic.matches("^[0-9]{9}[vVxX]$")))
        {
           return false;
        }
        else
        {
            return true;
        }
    }
    public boolean emailValidate(String email){
        if(!(email.matches("^(.+)@(.+)$")))
        {
           return false;
        }
        else
        {
            return true;
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public String getDeviceInfo() {
        // tested in android 10
        String uniquePseudoID = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;

        String serial = Build.getRadioVersion();
        String uuid=new UUID(uniquePseudoID.hashCode(), serial.hashCode()).toString();
        String brand=Build.BRAND;
        String modelno=Build.MODEL;
        String version=Build.VERSION.RELEASE;
        return "UUID:"+uuid +"\n"+
                "Brand:"+brand +"\n"+
                "Model:"+modelno +"\n"+
                "version:"+version +"\n"+
                "uniquePseudoId:"+uniquePseudoID ;


    }
    public static boolean isRooted() {

        // get from build info
        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }

        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }
    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }
    public void shareOnWhatsapp(Context context,String text){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        context.startActivity(sendIntent);
    }

    public void copyText(Context context,String text,String tag){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(tag, text);
        clipboard.setPrimaryClip(clip);
    }
    public String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
