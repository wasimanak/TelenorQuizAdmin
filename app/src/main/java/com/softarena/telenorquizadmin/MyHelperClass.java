package com.softarena.telenorquizadmin;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.print.PrintHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyHelperClass {
    public String getYYYYMMDD(String date) {
        String d1 = date.replace("-", "");
        String d2 = d1.replaceAll("/", "");

        return d2.trim();
    }

    public String removeSpecialCharectersAndTrim(String str) {
        String d1 = str.replaceAll("[^a-zA-Z0-9]", "");

        return d1.trim();
    }

    public String removeSpecialCharectersAndAddSpace(String str) {
        String d1 = str.replaceAll("[^a-zA-Z0-9]", " ");
        return d1.trim();
    }

    public boolean checkSpecialChar(String str) {
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(str);

        boolean b = m.find();
        if (b)
            return true;
        else {
            return false;
        }
    }

    public static String getCurrentDateHHMMSS_24() {
        String currentDateAndTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return currentDateAndTime;


    }

    public static String getCurrentDateHHMMSS_12() {
        String currentDateAndTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String[] str1 = currentDateAndTime.split(":");
        String newdate = "";
        String hours = "";
        if (Integer.valueOf(str1[0]) <= 12) {
            hours = str1[0];
            newdate = hours + ":" + str1[1] + ":" + str1[2] + " " + "pm";
        } else {
            hours = String.valueOf(Integer.valueOf(str1[0]) - 12);
            newdate = hours + ":" + str1[1] + ":" + str1[2] + " " + "pm";
        }

        return newdate;


    }

    public static String getCurrentDateYYYYMMDD() {
        String currentDateAndTime = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return currentDateAndTime;


    }

    public String getRandom15() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        String ninedigitrandom = String.format("%09d", number);

        String finalKey = ninedigitrandom + formattedDate.replaceAll(":", "");
        return finalKey;
    }

    public String getCurrentDateYYYYMMDDhhmmsss() {
        String currentDateAndTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        return currentDateAndTime;
    }

    public String removeSpecialCharectersAndAmPmFromTime(String time) {
        String t1 = time.replaceAll(":", "");
        String t2 = t1;
        if (t2.contains("am") || t2.contains("AM")) {
            t2 = time.toLowerCase().replaceAll("am", "");
        }
        if (t2.contains("pm") || t2.contains("PM")) {
            t2 = time.toLowerCase().replaceAll("pm", "");
        }
        return t2.trim();
    }

    public String getHtmlFormatedText(String message) {

        String htmltextstart = "<HTML>";
        String htmltextclose = "</HTML>";
        String bodytextstart = "<body>";
        String bodytextclose = "</body>";

        return htmltextstart + bodytextstart + message + bodytextclose + htmltextclose;

    }

    public String toBase64(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 3508, 2480, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public String getTravelModedriving() {


        return ",13z/data=!3m1!4b1!4m2!4m1!3e0";
    }

    public String getTravelModewalking() {


        return ",14z/data=!3m1!4b1!4m2!4m1!3e2";
    }

    public String getTravelModebicycling() {


        return ",14z/data=!3m1!4b1!4m2!4m1!3e2";
    }

    public String getTravelModetransit() {


        return ",12z/data=!3m1!4b1!4m2!4m1!3e3";
    }

    public long timeToMillis_yyyymmddhhmmss(String yyyymmddhhmmss) {
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

    public boolean cnicValidate(String txtNic) {
        if (!(txtNic.matches("^[0-9]{9}[vVxX]$"))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean emailValidate(String email) {
        if (!(email.matches("^(.+)@(.+)$"))) {
            return false;
        } else {
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
        String uuid = new UUID(uniquePseudoID.hashCode(), serial.hashCode()).toString();
        String brand = Build.BRAND;
        String modelno = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        return "UUID:" + uuid + "\n" +
                "Brand:" + brand + "\n" +
                "Model:" + modelno + "\n" +
                "version:" + version + "\n" +
                "uniquePseudoId:" + uniquePseudoID;


    }

//    public  boolean isRooted() {
//
//        // get from build info
//        String buildTags = android.os.Build.TAGS;
//        if (buildTags != null && buildTags.contains("test-keys")) {
//            return true;
//        }
//
//        // check if /system/app/Superuser.apk is present
//        try {
//            File file = new File("/system/app/Superuser.apk");
//            if (file.exists()) {
//                return true;
//            }
//        } catch (Exception e1) {
//            // ignore
//        }
//
//        // try executing commands
//        return canExecuteCommand("/system/xbin/which su")
//                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
//    }

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


    public   boolean isRooted() {
        return findBinary("su");
    }

    public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
            for (String where : places) {
                if (new File(where + binaryName).exists()) {
                    found = true;

                    break;
                }
            }
        }
        return found;
    }



    public void shareOnWhatsapp(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        context.startActivity(sendIntent);
    }

    public void shareapp(Context context,String message) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.putExtra(Intent.EXTRA_TEXT, message+ "\n https://play.google.com/store/apps/details?id=" + context.getPackageName());
        intentShare.setType("text/plain");
        context.startActivity(Intent.createChooser(intentShare, "settings_share"));
    }
    public void shareText(Context context,String text) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.putExtra(Intent.EXTRA_TEXT, text +"\n Download app for more islamic information"+ "\n https://play.google.com/store/apps/details?id=" + context.getPackageName());
        intentShare.setType("text/plain");
        context.startActivity(Intent.createChooser(intentShare, "settings_share"));
    }
    public void shareMedia(Context context,Bitmap bitmap) {
        String bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"\n Download app for more islamic information"+ "\n https://play.google.com/store/apps/details?id=" + context.getPackageName(), null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        context.startActivity(Intent.createChooser(intent, "\n Download app for more islamic information"+ "\n https://play.google.com/store/apps/details?id=" + context.getPackageName()));
    }
    public void shareMedia(Context context,ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"\n Download app for more islamic information"+ "\n https://play.google.com/store/apps/details?id=" + context.getPackageName(), null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        context.startActivity(Intent.createChooser(intent, "\n Download app for more islamic information"+ "\n https://play.google.com/store/apps/details?id=" + context.getPackageName()));
    }

    public void copyText(Context context, String text, String tag) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(tag, text);
        clipboard.setPrimaryClip(clip);
    }

    public String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public void log(String tag, String message) {
        Log.d(tag, getCurrentDateHHMMSS_12() + " : " + message);
    }

    public void toast(Context context, int duration, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setText(message);
        toast.show();

    }

    public void customToast(Context context, View layout) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public boolean isPrime(int n) {
        boolean result = false;
        if (n == 2) {
            result = true;
        }
        int squareRoot = (int) Math.sqrt(n);
        for (int i = 1; i <= squareRoot; i++) {
            if (n % i == 0 && i != 1) {
                result = false;
            }
            result = true;
        }
        return result;
    }

    public boolean validateEditText(List<EditText> editTextList) {
        boolean result = false;
        for (int i = 0; i < editTextList.size(); i++) {
            if (editTextList.get(i).getText().toString().trim().equals("")) {
                result = true;
//                toast(context, Toast.LENGTH_SHORT, " Required fields are not empty !");
                i = editTextList.size();
            }
        }

        return result;

    }

    private void printPhoto(Context context, Bitmap image) {
        PrintHelper photoPrinter = new PrintHelper(context);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("droids.jpg - photo print", image);
        log("PhotoPrint", "Application work is done !");
    }
    public String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }
    public String removeEndoflineandnextline(String str) {

        str = str.replaceAll("([\\r\\n])", "");
        return str;
    }
    // Context context , Activity activity e.g MainActivity.this , Permission -> Manifest.permission.WRITE_EXTERNAL_STORAGE , requestCode int=12 etc
    public void askForPermission(Context c, Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(c, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(c, "Please grant the requested permission to get your task done!", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }
    public void setImageTint(ImageView imageView,Context context,int color){
        imageView.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.MULTIPLY);
    }
    public void setVectorDrawableTint(ImageView imageView,Context context,int color){
        imageView.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    // don't forget to get permission
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
//            toast(context,Toast.LENGTH_SHORT,"Message Send");
        } catch (Exception ex) {

//            toast(context,Toast.LENGTH_SHORT,ex.getMessage());
            ex.printStackTrace();
        }
    }


   public boolean deleteRecursive(Context context) {
        String filePath = context.getFilesDir().getPath();
        boolean result=false;

        //Create androiddeft folder if it does not exist
        File fileOrDirectory = new File(filePath);

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {

                child.delete();
            }
        }else {
            log("Delete pdf","no directory found");
            return false;
        }

        if (fileOrDirectory.listFiles().length<2)
        {
            result=true;
        }else {
            result=false;
       }


       return  result;

    }

}
