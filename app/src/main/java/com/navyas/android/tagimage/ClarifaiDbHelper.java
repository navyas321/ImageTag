package com.navyas.android.tagimage;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClarifaiDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "image.db";

    public ClarifaiDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE " + ClarifaiContract.DataEntry.TABLE_NAME + " (" +
                ClarifaiContract.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION + " TEXT NOT NULL, " +

                ClarifaiContract.DataEntry.COLUMN_TAG1 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG2 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG3 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG4 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG5 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG6 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG7 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG8 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG9 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG10 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG11 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG12 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG13 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG14 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG15 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG16 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG17 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG18 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG19 + " TEXT NOT NULL, " +
                ClarifaiContract.DataEntry.COLUMN_TAG20 + " TEXT NOT NULL" +

                ");";
                sqLiteDatabase.execSQL(SQL_CREATE_IMAGE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClarifaiContract.DataEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
