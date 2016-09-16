package com.phonegap.wordpowerswahili;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PremiumWordDownloader extends AppCompatActivity {

    // JSON Node names
    private static final String TAG_ITEMS = "items";
    private static final String TAG_WORDID = "WordID";
    private static final String TAG_ENGLISH = "English";
    private static final String TAG_SWAHILI = "Swahili";
    private static final String TAG_CATEGORYID = "CategoryID";
    private static final String TAG_SOUND = "Sound";
    private static final String TAG_HASMP3 = "HasMP3";
    // URL to get contacts JSON
    private static String url = "http://wordpowerswahili.org/api/wordsapi";
    // user JSONArray
    JSONArray items = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> wordList;
    SqliteController controller;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word_downloader);
        controller = new SqliteController(getApplication().getApplicationContext());
        wordList = new ArrayList<HashMap<String, String>>();
        // Calling async task to get json
        new GetNewWords().execute();

        Dialog dialog = new Dialog(PremiumWordDownloader.this);
        dialog.setTitle("Done");
        dialog.show();
    }

    private class GetNewWords extends AsyncTask<Void, Void, Void> {

        String wordID = "";
        String mEnglish = "";
        String mSwahili = "";
        String categoryID = "";
        String mSound = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PremiumWordDownloader.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Iterator iterator = wordList.iterator();
            while (iterator.hasNext()) {
                // Log.d("Post Execute","-");
                HashMap map = (HashMap) iterator.next();
                HashMap mapInsert = controller.getWordInfo(map.get(TAG_WORDID).toString());
                if (mapInsert.get(TAG_WORDID).equals("0")) {
                    controller.insertWords(map);
                }

            }

            startActivity(new Intent(PremiumWordDownloader.this, PremiumSoundDownloader.class));

        }

    }
}
