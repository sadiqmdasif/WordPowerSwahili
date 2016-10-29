package com.phonegap.wordpowerswahili;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LoadingActivity extends Activity {
    private static final int REQUEST_WRITE_STORAGE = 112;
    //Common Word Sound
    // JSON Node names
    private static final String TAG_ENGLISH = "English";
    private static final String TAG_SWAHILI = "Swahili";
    private static final String TAG_WORDID = "WordID";
    private static final String TAG_CATEGORYID = "CategoryID";
    private static final String TAG_SOUND = "Sound";
    private static final String TAG_HASMP3 = "HasMP3";
    private static String TAG = "PermissionDemo";
    //Word Downloader
    // URL
    private static String wordUrl = "http://wordpowerswahili.org/api/wordsapi";
    private static String soundZipUrl = "http://wordpowerswahili.org/sounds/sounds.zip";
    private static String soundUrl = "http://wordpowerswahili.org/sounds/";


    // File url to download

    private static String CATEGORYID = "";
    private static String FILENAME = "";
    int id = 1;
    int counter = 0;
    ArrayList<AsyncTask<String, String, String>> arr;
    SqliteController controller;
    // user JSONArray
    JSONArray items = null;

    //Sound Downloader
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> wordList;
    DownloadWordFromURL wordFromURL;

    //DownloadSoundFromURL soundFromURL;
    AlertDialog.Builder builder;
    TextView txtLoadingInfo;
    UnZipTask unZipTask;
    //private NotificationReceiver nReceiver;
    private boolean isWordDownloaded = false;
    private DownloadZipFromURL zipFromURL;
    private DownloadSoundFromURL soundFromURL;

    public static boolean isDeviceOnline(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isOnline = (networkInfo != null && networkInfo.isConnected());
        if (!isOnline)
            Toast.makeText(context, " Internet Connection Required for Initial Setup ", Toast.LENGTH_SHORT).show();

        return isOnline;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        txtLoadingInfo = (TextView) findViewById(R.id.txtLoadingInfo);
        wordFromURL = new DownloadWordFromURL();
        wordList = new ArrayList<HashMap<String, String>>();
        // soundFromURL = new DownloadSoundFromURL();
        zipFromURL = new DownloadZipFromURL();
        soundFromURL = new DownloadSoundFromURL();

        unZipTask = new UnZipTask();

        arr = new ArrayList<AsyncTask<String, String, String>>();

        arr.add(wordFromURL);
        arr.add(zipFromURL);
        arr.add(soundFromURL);
        arr.add(unZipTask);
        //check permissions
        hasPermission();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        killTasks();
        controller.close();
        //    unregisterReceiver(nReceiver);
    }

    private void mLoader() {

        //Database
        controller = new SqliteController(getApplication().getApplicationContext());

        HashMap downloadStatus = controller.getDownloadStatus("1");
        if (downloadStatus.get("STATUS").equals("2")) {

            if (new ArrayList<HashMap>(controller.getAllWordsHasNoMp3()).size() != 0) {
               soundFromURL.execute(soundUrl);
            } else {

                finish();
                startActivity(new Intent(LoadingActivity.this, CategoryListActivity.class));
            }

        } else {
            //Dialog
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth);
            } else {
                builder = new AlertDialog.Builder(this);
            }

            mDownloader();


        }

    }

    private boolean hasPermission() {

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(LoadingActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.this);
                builder.setMessage("Permission to access the SD-CARD is required for this app")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                makeRequest();

                // makeRequest();
            }
            return false;

        } else {
            mLoader();
            return true;
        }

      /*  ContentResolver contentResolver = getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = getPackageName();

        // check to see if the enabledNotificationListeners String contains our
        // package name
        if (enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName)) {
            // in this situation we know that the user has not granted the app
            // the Notification access permission
            // Check if notification is enabled for this application
            Log.i("ACC", "Dont Have Notification access");
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        } else {
            Log.i("ACC", "Have Notification access");
        }*/
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(LoadingActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_STORAGE) {
            // for each permission check if the user granted/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        showRationale = shouldShowRequestPermissionRationale(permission);
                    }
                    if (!showRationale) {

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_WRITE_STORAGE);
                        hasPermission();
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                    } else if (Manifest.permission.WRITE_CONTACTS.equals(permission)) {
                        //showRationale(permission, R.string.permission_denied_contacts);
                        // user did NOT check "never ask again"
                        // this is a good place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                        hasPermission();
                    }
                } else {
                    mLoader();
                }
            }
        }
    }

    public void unzip(String fileName, String filePath, String destinationPath) {
        String fullPath = filePath + "/" + fileName + ".zip";
        Log.d(TAG, "unzipping " + fileName + " to " + destinationPath);
        unZipTask.execute(fullPath, destinationPath);
    }

    void mDownloader() {

        builder.setTitle("Internet Required");
        builder.setMessage("Setup will download files (60 MB) and press OK");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (!isDeviceOnline(LoadingActivity.this)) {

                            builder.show();
                        } else {
                            wordFromURL.execute();
                        }


                    }
                });
        AlertDialog alert = builder.create();

        ArrayList<HashMap<String, String>> wordListCount = controller.getAllWords();
        if (wordListCount.size() < 876) {
            isWordDownloaded = false;
        } else {
            isWordDownloaded = true;
        }
        if (!isWordDownloaded) {
            alert.show();
//            builder.show();
        }

        if (isWordDownloaded) {
            //soundFromURL.execute("http://www.wordpowerswahili.org/sounds/");
            zipFromURL.execute(soundZipUrl);
        }
    }


    private void killTasks() {
        if (null != arr & arr.size() > 0) {
            for (AsyncTask<String, String, String> a : arr) {
                if (a != null) {
                    Log.i("NotificationReceiver", "Killing download thread");
                    a.cancel(true);
                }
            }
            //    mNotifyManager.cancelAll();
        }
    }

    /*class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String event = intent.getExtras().getString(NLService.NOT_EVENT_KEY);
            Log.i("NotificationReceiver", "NotificationReceiver onReceive : " + event);
            if (event.trim().contentEquals(NLService.NOT_REMOVED)) {
                killTasks();
            }
        }
    }*/


    private class DownloadWordFromURL extends AsyncTask<String, String, String> {

        String wordID = "";
        String mEnglish = "";
        String mSwahili = "";
        String categoryID = "";
        String mSound = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
         /*   pDialog = new ProgressDialog(LoadingActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/
            txtLoadingInfo.setText("Loading...");

        }

        @Override
        protected String doInBackground(String... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(wordUrl, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    // JSONArray jsonArray = null;
                    try {
                        items = new JSONArray(jsonStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Getting JSON Array node
                    // items = jsonObj.getJSONArray(TAG_ITEMS);

                    // looping through All Contacts
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        //Log.d("length: ",String.valueOf(items.length()));
                        wordID = c.getString(TAG_WORDID);

                        if (c.isNull(c.getString(TAG_ENGLISH))) {
                            mEnglish = c.getString(TAG_ENGLISH);
                        } else {
                            mEnglish = "";
                        }

                        if (c.isNull(c.getString(TAG_SWAHILI))) {
                            mSwahili = c.getString(TAG_SWAHILI);
                        } else {
                            mSwahili = "";
                        }

                        if (c.isNull(c.getString(TAG_CATEGORYID))) {
                            categoryID = c.getString(TAG_CATEGORYID);
                        } else {
                            categoryID = "00";
                        }

                        if (c.isNull(c.getString(TAG_SOUND))) {
                            mSound = c.getString(TAG_SOUND);
                        } else {
                            mSound = "Recording00";
                        }

                        // tmp hashmap for single wordMap
                        HashMap<String, String> wordMap = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        wordMap.put(TAG_WORDID, wordID);
                        wordMap.put(TAG_ENGLISH, mEnglish);
                        wordMap.put(TAG_SWAHILI, mSwahili);
                        wordMap.put(TAG_CATEGORYID, categoryID);
                        wordMap.put(TAG_SOUND, mSound);
                        wordMap.put(TAG_HASMP3, "0");
                        // adding user to user list
                        wordList.add(wordMap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /*if (pDialog.isShowing())
                pDialog.dismiss();*/

            Iterator iterator = wordList.iterator();
            while (iterator.hasNext()) {
                // Log.d("Post Execute","-");
                HashMap map = (HashMap) iterator.next();
                HashMap mapInsert = controller.getWordInfo(map.get(TAG_WORDID).toString());
                if (mapInsert.get(TAG_WORDID).equals("0")) {
                    controller.insertWords(map);
                }

            }

            isWordDownloaded = true;
            mDownloader();
            //  startActivity(new Intent(PremiumWordDownloader.this,PremiumSoundDownloader.class));
        }

    }

    private class DownloadSoundFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   mNotifyManager.notify(id, mBuilder.build());
            //  mBuilder.setAutoCancel(true);
            txtLoadingInfo.setText("Please Wait! This may take a while");
            // showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                ArrayList<HashMap<String, String>> wordList = controller.getAllWordsHasNoMp3();
                Iterator iterator = wordList.iterator();

                while (iterator.hasNext()) {
                    HashMap map = (HashMap) iterator.next();
                    CATEGORYID = String.valueOf(map.get(TAG_CATEGORYID));
                    FILENAME = String.valueOf(map.get(TAG_SOUND));
                    //WORDID = String.valueOf(map.get(TAG_WORDID));
                    int lenghtOfFile;
                    if (!map.get(TAG_HASMP3).equals("1")) {

                        File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "/SWAHILI/SOUND/" + CATEGORYID);
                        if (!cacheDir.exists())
                            cacheDir.mkdirs();

                        File f = new File(cacheDir, FILENAME + ".mp3");
                        if (!f.exists()) {
                            controller.updateHasMp3(map.get(TAG_WORDID).toString(), "0");
                            URL url = new URL(f_url[0] + map.get(TAG_CATEGORYID) + "/" + map.get(TAG_SOUND) + ".mp3");
                            URLConnection conection = url.openConnection();
                            try {
                                conection.connect();
                                // this will be useful so that you can show a tipical 0-100% progress bar
                                lenghtOfFile = conection.getContentLength();


                                // download the file
                                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                                FileOutputStream output = new FileOutputStream(f);

                                byte data[] = new byte[1024];
                                long total = 0;

                                while ((count = input.read(data)) != -1) {
                                    total += count;
                                    // publishing the progress....
                                    // After this onProgressUpdate will be called
                                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                                    // writing data to file
                                    output.write(data, 0, count);

                                    Log.d("Writing", String.valueOf(FILENAME));
                                }

                                // flushing output

                                output.flush();

                                // closing streams
                                output.close();

                                input.close();

                                controller.updateHasMp3(map.get(TAG_WORDID).toString(), "1");
                            } catch (Exception e) {

                            }
                        } else if (f.exists()) {
                            controller.updateHasMp3(map.get(TAG_WORDID).toString(), "1");
                        } else {

                        }
                    }
                }

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //   pd.setProgress(Integer.parseInt(progress[0]));

            // pd.setMessage("Please Wait! Downloading\n"+FILENAME+".mp3 " + String.valueOf(Integer.parseInt(progress[0]))+"%");
            // int per = (int) (((counter + 1) / len) * 100f);

            txtLoadingInfo.setText("Please Wait! Downloading\n" + FILENAME + ".mp3 " + String.valueOf(Integer.parseInt(progress[0])) + "%");
            Log.i("Counter", "Counter : " + counter + ", per : " + progress[0]);
            //mBuilder.setContentText("Please Wait! Downloading\n" + FILENAME + ".mp3 " + String.valueOf(Integer.parseInt(progress[0])) + "%");
            //     mBuilder.setProgress(100, Integer.parseInt(progress[0]), false);
            // Displays the progress bar for the first time.
            //    mNotifyManager.notify(id, mBuilder.build());
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded

            // Displaying downloaded image into image view
            // Reading image path from sdcard

            //  startActivity(new Intent(PremiumSoundDownloader.this,CategoryListActivity.class));
            Log.i("Async-Example", "onPostExecute Called");

            // When the loop is finished, updates the notification

            //mBuilder.setContentTitle("Done.");
            // mBuilder.setContentText("Sound Download complete")
            // Removes the progress bar
            //      .setProgress(0, 0, false);
            //    mNotifyManager.notify(id, mBuilder.build());

            startActivity(new Intent(LoadingActivity.this, CategoryListActivity.class));


        }

    }

    private class DownloadZipFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // mNotifyManager.notify(id, mBuilder.build());
            // mBuilder.setAutoCancel(true);
            txtLoadingInfo.setText("Please Wait! This may take a while");
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());

                File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "/SWAHILI/");
                if (!cacheDir.exists())
                    cacheDir.mkdirs();

                File f = new File(cacheDir, "sounds.zip");

                if (f.exists()) {

                }
                if (!f.exists()) {
                    FileOutputStream output = new FileOutputStream(f);
                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                }
                input.close();
                if (f.exists()) {
                    if (f.length() == lenghtOfFile) {
                        Log.d("fileSize", "File Size ok!! ");
                    } else {
                        f.delete();
                        mDownloader();
                    }
                }

/*
                URL u = new URL(aurl[0]);
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

                File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "/SWAHILI/");
                if (!cacheDir.exists())
                    cacheDir.mkdirs();

                File f = new File(cacheDir, "sound.zip");
                if (!f.exists()) {
                    DataOutputStream fos = new DataOutputStream(new FileOutputStream(f));
                    fos.write(buffer);
                    fos.flush();
                    fos.close();
                }

                */

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            txtLoadingInfo.setText("Please Wait! Downloading Sounds " + String.valueOf(Integer.parseInt(progress[0])) + "%");
            Log.i("Counter", "Counter : " + counter + ", per : " + progress[0]);
            ///    mBuilder.setContentText("Please Wait! Downloading Sounds" + String.valueOf(Integer.parseInt(progress[0])) + "%");
            //   mBuilder.setProgress(100, Integer.parseInt(progress[0]), false);
            // Displays the progress bar for the first time.
            //   mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected void onPostExecute(String unused) {
            // dismiss the dialog after the file was downloaded

            // Displaying downloaded image into image view
            // Reading image path from sdcard

            //  startActivity(new Intent(PremiumSoundDownloader.this,CategoryListActivity.class));
            Log.i("Async-Example", "onPostExecute Called");

            // When the loop is finished, updates the notification


            //   mBuilder.setContentTitle("Done.");
            //    mBuilder.setContentText("Sound Download complete")
            // Removes the progress bar
            //          .setProgress(0, 0, false);
            //    mNotifyManager.notify(id, mBuilder.build());
            String mFilePath = Environment.getExternalStorageDirectory() + "/SWAHILI/";
            String mDestinationFile = Environment.getExternalStorageDirectory() + "/SWAHILI/SOUND/";
            unzip("sounds", mFilePath, mDestinationFile);


        }
    }

    private class UnZipTask extends AsyncTask<String, String, String> {

        @SuppressWarnings("rawtypes")
        @Override
        protected String doInBackground(String... params) {
            String filePath = params[0];
            String destinationPath = params[1];

            File archive = new File(filePath);
            try {
                ZipFile zipfile = new ZipFile(archive);
                for (Enumeration e = zipfile.entries(); e.hasMoreElements(); ) {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    unzipEntry(zipfile, entry, destinationPath);
                    // controller.updateStatus("1","2");
                }
            } catch (Exception e) {
                // controller.updateStatus("1","0");
                Log.e(TAG, "Error while extracting file " + archive, e);
                return String.valueOf(0);
            }

            return String.valueOf(1);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("1")) {
                controller.updateStatus("1", "2");
            } else {
                controller.updateStatus("1", "0");
            }
            String mFilePath = Environment.getExternalStorageDirectory() + "/SWAHILI/";
            String mDestinationFile = Environment.getExternalStorageDirectory() + "/SWAHILI/SOUND/";
            Decompress decompress = new Decompress("sounds", mFilePath, mDestinationFile);


            File f = new File(mDestinationFile, "" + ".nomedia");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoadingActivity.this, CategoryListActivity.class));
                    // mNotifyManager.cancelAll();
                    finish();
                }
            }, 5000);
        }

        private void unzipEntry(ZipFile zipfile, ZipEntry entry,
                                String outputDir) throws IOException {

            if (entry.isDirectory()) {
                createDir(new File(outputDir, entry.getName()));
                return;
            }

            File outputFile = new File(outputDir, entry.getName());
            if (!outputFile.getParentFile().exists()) {
                createDir(outputFile.getParentFile());
            }

            Log.v(TAG, "Extracting: " + entry);
            BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

            try {
                IOUtils.copy(inputStream, outputStream);
            } finally {
                outputStream.close();
                inputStream.close();


            }
        }

        private void createDir(File dir) {
            if (dir.exists()) {
                return;
            }
            Log.v(TAG, "Creating dir " + dir.getName());
            if (!dir.mkdirs()) {
                throw new RuntimeException("Can not create dir " + dir);
            }
        }
    }
}





