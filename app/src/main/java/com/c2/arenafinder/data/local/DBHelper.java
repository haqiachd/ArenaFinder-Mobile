package com.c2.arenafinder.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String ARENAFINDER_DATABASE = "ArenaFinderLite";
    public static final int ARENAFINDER_DATABASE_VERSION = 1;

    public static final String TABLE_ACCOUNT = "account";
    public static final String
            ACCOUNT_ID_ACCOUNT = "id_account",
            ACCOUNT_ID_USERS = "id_users",
            ACCOUNT_USERNAME = "username",
            ACCOUNT_EMAIL = "email",
            ACCOUNT_FULL_NAME = "fullname",
            ACCOUNT_PHOTO = "photo";

    public static final String TABLE_NOTIFICATION = "notif";
    public static final String
            NOTIFICATION_ID_NOTIF = "id_notif",
            NOTIFICATION_TITLE = "title",
            NOTIFICATION_BODY = "body",
            NOTIFICATION_TYPE = "type",
            NOTIFICATION_DATE = "date";

    public DBHelper(Context context) {
        super(context, ARENAFINDER_DATABASE, null, ARENAFINDER_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableNotification = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + " (" +
                NOTIFICATION_ID_NOTIF + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIFICATION_TITLE + " TEXT, " +
                NOTIFICATION_BODY + " TEXT, " +
                NOTIFICATION_TYPE + " TEXT, " +
                NOTIFICATION_DATE + " DATE " +
                ")";
        db.execSQL(tableNotification);

        String tableAccount = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + " (" +
                ACCOUNT_ID_ACCOUNT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_ID_USERS + " TEXT, " +
                ACCOUNT_USERNAME + " TEXT, " +
                ACCOUNT_EMAIL + " TEXT, " +
                ACCOUNT_FULL_NAME + " TEXT, " +
                ACCOUNT_PHOTO + " TEXT " +
                ")";

        db.execSQL(tableAccount);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
