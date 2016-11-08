package com.phonegap.wordpowerswahili;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PremiumSoundDownloader extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    private static final String TAG_ITEMS = "items";
    private static final String TAG_WORDID = "WordID";
    private static final String TAG_ENGLISH = "English";
    private static final String TAG_SWAHILI = "Swahili";
    private static final String TAG_CATEGORYID = "CategoryID";
    private static final String TAG_SOUND = "Sound";
    private static final String TAG_HASMP3 = "HasMP3";
    public static SharedPreferences.Editor editor;
    // File url to download
    private static String url = "";
    private static String CATEGORYID = "";
    private static String FILENAME = "";
    private static String WORDID = "";
    SharedPreferences sharedpreferences;
    SqliteController controller;
    ProgressDialog pd;
    private ProgressDialog pDialog;
    private int progress_bar_type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new SqliteController(PremiumSoundDownloader.this);
        pd = new ProgressDialog(PremiumSoundDownloader.this);
        pDialog = new ProgressDialog(PremiumSoundDownloader.this, progress_bar_type);
      //  setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Downloading Sounds, Don't Stop");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
        url = "http://www.wordpowerswahili.org/sounds/";
        new DownloadSoundFromURL().execute(url);
    }


    /**
     * Background Async Task to download file
     */
    class DownloadSoundFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    WORDID = String.valueOf(map.get(TAG_WORDID));
                    int lenghtOfFile;
                    if (!map.get(TAG_HASMP3).equals("1")) {

                        File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "/Android/data/com.phonegap.wordpowerswahili/SOUND/" + CATEGORYID);
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
            pd.setMessage("Please Wait! Downloading\n" + FILENAME + ".mp3 " + String.valueOf(Integer.parseInt(progress[0])) + "%");
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            pd.dismiss();
            // Displaying downloaded image into image view
            // Reading image path from sdcard

            startActivity(new Intent(PremiumSoundDownloader.this, CategoryListActivity.class));


        }

    }
}
