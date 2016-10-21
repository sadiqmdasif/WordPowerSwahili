package com.phonegap.wordpowerswahili;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SadiqMdAsif on 10-Sep-16.
 */
public class SqliteController extends SQLiteOpenHelper {
    private static final String LOGCAT = null;
    // MAP Node names

    private static final String TAG_WORDID = "WordID";
    private static final String TAG_ENGLISH = "English";
    private static final String TAG_SWAHILI = "Swahili";
    private static final String TAG_CATEGORYID = "CategoryID";
    private static final String TAG_SOUND = "Sound";
    private static final String TAG_HASMP3 = "HasMP3";

    public SqliteController(Context applicationcontext) {
        super(applicationcontext, android.os.Environment.getExternalStorageDirectory() + "/SWAHILI/SWAHILIDB.db", null, 1);
        Log.d(LOGCAT, "Created db");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

      /*  String query;
        query = "CREATE TABLE Students ( StudentId INTEGER PRIMARY KEY, StudentName TEXT)";
        database.execSQL(query);
*/
        String CREATE_TABLE_WORDS = "Create TABLE Words (" +
                "WordId VARCHAR (10, 1) NOT NULL PRIMARY KEY," +
                "English [NVARCHAR] (500) NOT NULL," +
                "Swahili  [NVARCHAR] (500) NOT NULL," +
                "CategoryID [NVARCHAR] (200)," +
                "Sound [NVARCHAR] (500)," +
                "hasMP3 [NVARCHAR] (5)," +
                "NewWord    INT (1) )";

        String CREATE_TABLE_DownloadStatus = "Create TABLE DownloadStatus (" +
                "SL VARCHAR (5) NOT NULL PRIMARY KEY, STATUS Varchar (1) DEFAULT 0)";


        database.execSQL(CREATE_TABLE_WORDS);
        database.execSQL(CREATE_TABLE_DownloadStatus);

        Log.d(LOGCAT, "Words Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS Words";
        database.execSQL(query);
        onCreate(database);
    }

    public void updateStatus (String sl , String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Update DownloadStatus set status='"+status+"' where sl='"+sl+"'");
        database.close();

    }



    public void insertWords(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TAG_WORDID, queryValues.get(TAG_WORDID));
        values.put(TAG_ENGLISH, queryValues.get(TAG_ENGLISH));
        values.put(TAG_SWAHILI, queryValues.get(TAG_SWAHILI));
        values.put(TAG_CATEGORYID, queryValues.get(TAG_CATEGORYID));
        values.put(TAG_SOUND, queryValues.get(TAG_SOUND));
        values.put(TAG_HASMP3, queryValues.get(TAG_HASMP3));
        database.insert("Words", null, values);
        database.close();
    }

    public int updateWords(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Word", queryValues.get("Words"));
        return database.update("Words", values, "wordId" + " = ?", new String[]{queryValues.get("wordId")});
        //String updateQuery = "Update words set txtWord='"+word+"' where txtWord='"+ oldWord +"'"; //Log.d(LOGCAT,updateQuery); //database.rawQuery(updateQuery, null); //return database.update("words", values, "txtWord = ?", new String[] { word }); } public void deleteStudent(String id) { Log.d(LOGCAT,"delete"); SQLiteDatabase database = this.getWritableDatabase();	String deleteQuery = "DELETE FROM Students where StudentId='"+ id +"'"; Log.d("query",deleteQuery);	database.execSQL(deleteQuery); } public ArrayList<HashMap<String, String>> getAllStudents() { ArrayList<HashMap<String, String>> wordList; wordList = new ArrayList<HashMap<String, String>>(); String selectQuery = "SELECT * FROM Students"; SQLiteDatabase database = this.getWritableDatabase(); Cursor cursor = database.rawQuery(selectQuery, null); if (cursor.moveToFirst()) { do { HashMap<String, String> map = new HashMap<String, String>(); map.put("StudentId", cursor.getString(0)); map.put("StudentName", cursor.getString(1)); wordList.add(map); } while (cursor.moveToNext()); } // return contact list return wordList; } public HashMap<String, String> getStudentInfo(String id) { HashMap<String, String> wordList = new HashMap<String, String>(); SQLiteDatabase database = this.getReadableDatabase(); String selectQuery = "SELECT * FROM Students where StudentId='"+id+"'"; Cursor cursor = database.rawQuery(selectQuery, null); if (cursor.moveToFirst()) { do { //HashMap<String, String> map = new HashMap<String, String>(); wordList.put("StudentName", cursor.getString(1)); //wordList.add(map); } while (cursor.moveToNext()); }	return wordList; }	}
    }

    public int updateHasMp3(String wordId, String hasMp3) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(TAG_WORDID, wordId);
        values.put(TAG_HASMP3, hasMp3);
        return database.update("Words", values, "wordId" + " = ?", new String[]{wordId});
        //String updateQuery = "Update words set txtWord='"+word+"' where txtWord='"+ oldWord +"'"; //Log.d(LOGCAT,updateQuery); //database.rawQuery(updateQuery, null); //return database.update("words", values, "txtWord = ?", new String[] { word }); } public void deleteStudent(String id) { Log.d(LOGCAT,"delete"); SQLiteDatabase database = this.getWritableDatabase();	String deleteQuery = "DELETE FROM Students where StudentId='"+ id +"'"; Log.d("query",deleteQuery);	database.execSQL(deleteQuery); } public ArrayList<HashMap<String, String>> getAllStudents() { ArrayList<HashMap<String, String>> wordList; wordList = new ArrayList<HashMap<String, String>>(); String selectQuery = "SELECT * FROM Students"; SQLiteDatabase database = this.getWritableDatabase(); Cursor cursor = database.rawQuery(selectQuery, null); if (cursor.moveToFirst()) { do { HashMap<String, String> map = new HashMap<String, String>(); map.put("StudentId", cursor.getString(0)); map.put("StudentName", cursor.getString(1)); wordList.add(map); } while (cursor.moveToNext()); } // return contact list return wordList; } public HashMap<String, String> getStudentInfo(String id) { HashMap<String, String> wordList = new HashMap<String, String>(); SQLiteDatabase database = this.getReadableDatabase(); String selectQuery = "SELECT * FROM Students where StudentId='"+id+"'"; Cursor cursor = database.rawQuery(selectQuery, null); if (cursor.moveToFirst()) { do { //HashMap<String, String> map = new HashMap<String, String>(); wordList.put("StudentName", cursor.getString(1)); //wordList.add(map); } while (cursor.moveToNext()); }	return wordList; }	}
    }


    public void deleteWords(String id) {
        Log.d(LOGCAT, "delete");
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM words where wordId='" + id + "'";
        Log.d("query", deleteQuery);
        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllWords() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM Words";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_WORDID, cursor.getString(0));
                map.put(TAG_ENGLISH, cursor.getString(1));
                map.put(TAG_SWAHILI, cursor.getString(2));
                map.put(TAG_CATEGORYID, cursor.getString(3));
                map.put(TAG_SOUND, cursor.getString(4));
                map.put(TAG_HASMP3, cursor.getString(5));

                wordList.add(map);
            } while (cursor.moveToNext());
            // return contact list
        }
        cursor.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getAllWordsHasNoMp3() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM Words where hasMp3 != 1";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_WORDID, cursor.getString(0));
                map.put(TAG_ENGLISH, cursor.getString(1));
                map.put(TAG_SWAHILI, cursor.getString(2));
                map.put(TAG_CATEGORYID, cursor.getString(3));
                map.put(TAG_SOUND, cursor.getString(4));
                map.put(TAG_HASMP3, cursor.getString(5));

                wordList.add(map);
            } while (cursor.moveToNext());
            // return contact list
        }
        cursor.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getWordsByCategory(String categoryID) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM Words where categoryId='" + categoryID + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TAG_WORDID, cursor.getString(0));
                map.put(TAG_ENGLISH, cursor.getString(1));
                map.put(TAG_SWAHILI, cursor.getString(2));
                map.put(TAG_CATEGORYID, cursor.getString(3));
                map.put(TAG_SOUND, cursor.getString(4));
                map.put(TAG_HASMP3, cursor.getString(5));

                wordList.add(map);
            } while (cursor.moveToNext());
            // return contact list
        }
        cursor.close();
        return wordList;
    }


    public HashMap<String, String> getWordInfo(String id) {
        HashMap<String, String> wordInfo = new HashMap<String, String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM Words where wordId='" + id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //HashMap<String, String> map = new HashMap<String, String>();
                wordInfo.put("WordID", cursor.getString(0));
                wordInfo.put("English", cursor.getString(1));
                wordInfo.put("Swahili", cursor.getString(2));
                wordInfo.put("CategoryID", cursor.getString(3));
                wordInfo.put("Sound", cursor.getString(4));
                wordInfo.put("NewWord", cursor.getString(5));

                // wordList.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor.getCount() == 0) {
            wordInfo.put("WordID", "0");
            wordInfo.put("English", "0");
            wordInfo.put("Swahili", "0");
            wordInfo.put("CategoryID", "0");
            wordInfo.put("Sound", "0");
            wordInfo.put("NewWord", "0");
        }
        cursor.close();
        return wordInfo;
    }

    public HashMap<String, String> getDownloadStatus(String sl) {
        HashMap<String, String> downloadInfo = new HashMap<String, String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM DownloadStatus where sl='" + sl + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //HashMap<String, String> map = new HashMap<String, String>();
                downloadInfo.put("SL", cursor.getString(0));
                downloadInfo.put("STATUS", cursor.getString(1));


                // wordList.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor.getCount() == 0) {
            downloadInfo.put("SL", "0");
            downloadInfo.put("STATUS", "0");

        }

        cursor.close();
        return downloadInfo;
    }

}

