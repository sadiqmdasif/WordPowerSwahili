package com.phonegap.wordpowerswahili;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SadiqMdAsif on 09-09-2016.
 */
public class MyDBHelperCategory extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Category";
    // table name
    private static final String TABLE_NAME_PROFILES = "profiles";


    private static final String KEY_ID = "id";
    private static final String KEY_Fname = "firstName";
    private static final String KEY_Lname = "lastName";
    private static final String KEY_Phone = "phone";
    private static final String KEY_Interest = "interest";
    private static final String KEY_Picture = "picture";

    private ArrayList<String> allProfiles = new ArrayList<String>();


    public MyDBHelperCategory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_PROFILE_TABLE = "CREATE TABLE WORDS ( " + "WordID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "English nvarchar maxl , " +
                "firstName TEXT, " +
                "lastName TEXT," + "interest TEXT," + "picture TEXT)";
      /*  CREATE TABLE [dbo].[Words](
                [WordId] [bigint] IDENTITY(1,1) NOT NULL,
        [English] [nvarchar](max) NOT NULL,
        [Swahili] [nvarchar](max) NOT NULL,
        [CategoryID] [nvarchar](128) NULL,
        [Sound] [nvarchar](max) NULL.
        [NewWord] int)
        db.execSQL(CREATE_PROFILE_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS profiles");

        this.onCreate(db);
    }


    public void saveProfile(String fname, String lname, String phone, String intrest, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Fname, fname);
        values.put(KEY_Lname, lname);
        values.put(KEY_Interest, intrest);
        values.put(KEY_Phone, phone);
        values.put(KEY_Picture, imagePath);
        db.close();

    }

   /* public List getAllData() {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME_PROFILES;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(KEY_ID));
                String fname = cursor.getString(cursor.getColumnIndex(KEY_Fname));
                String lname = cursor.getString(cursor.getColumnIndex(KEY_Lname));
                String phone = cursor.getString(cursor.getColumnIndex(KEY_Phone));
                String interest = cursor.getString(cursor.getColumnIndex(KEY_Interest));
                String picture = cursor.getString(cursor.getColumnIndex(KEY_Picture));

                allProfiles.add(""+id+","+fname+", "+lname+", "+phone+", "+interest+", "+picture);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allProfiles;
    }
    */
}