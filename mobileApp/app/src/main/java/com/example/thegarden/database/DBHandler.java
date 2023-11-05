package com.example.thegarden.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "myappdb";
    private static final int DB_VERSION = 1;
    private static final String USERS_TABLE_NAME = "users";
    private static final String PLANTS_TABLE_NAME = "plants";

    // Columns for the Users table
    private static final String USER_ID_COL = "user_id";
    private static final String USER_FIRST_NAME_COL = "user_first_name";
    private static final String USER_LAST_NAME_COL = "user_last_name";
    private static final String USER_EMAIL_COL = "user_email";
    private static final String USER_PASSWORD_COL = "user_password"; // Password should be encrypted

    // Columns for the Plants table
    private static final String PLANT_ID_COL = "plant_id";
    private static final String PLANT_NAME_COL = "plant_name";
    private static final String USER_ID_FK_COL = "user_id_fk"; // Foreign key to link plants to users

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Users table
        String createUsersTableQuery = "CREATE TABLE " + USERS_TABLE_NAME + " ("
                + USER_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_FIRST_NAME_COL + " TEXT, "
                + USER_LAST_NAME_COL + " TEXT, "
                + USER_EMAIL_COL + " TEXT, "
                + USER_PASSWORD_COL + " TEXT"
                + ")";
        db.execSQL(createUsersTableQuery);

        // Create the Plants table
        String createPlantsTableQuery = "CREATE TABLE " + PLANTS_TABLE_NAME + " ("
                + PLANT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PLANT_NAME_COL + " TEXT, "
                + USER_ID_FK_COL + " INTEGER, "
                + "FOREIGN KEY (" + USER_ID_FK_COL + ") REFERENCES " + USERS_TABLE_NAME + "(" + USER_ID_COL + ")"
                + ")";
        db.execSQL(createPlantsTableQuery);
    }

    public void addUser(String userFirstName,String userLastName, String userEmail, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME_COL, userFirstName);
        values.put(USER_LAST_NAME_COL, userLastName);
        values.put(USER_EMAIL_COL, userEmail);
        values.put(USER_PASSWORD_COL, userPassword);
        db.insert(USERS_TABLE_NAME, null, values);
        db.close();
    }

    public void addPlants(int userId, List<String> plantNames) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (String plantName : plantNames) {
            ContentValues values = new ContentValues();
            values.put(PLANT_NAME_COL, plantName);
            values.put(USER_ID_FK_COL, userId);
            db.insert(PLANTS_TABLE_NAME, null, values);
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLANTS_TABLE_NAME);
        onCreate(db);
    }
}
