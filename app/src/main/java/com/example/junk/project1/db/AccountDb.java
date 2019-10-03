package com.example.junk.project1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.junk.project1.model.Account;

import java.util.ArrayList;

public class AccountDb {

    // data fields to hold database objects we'll be using
    public SQLiteDatabase database;

    public SQLiteOpenHelper openHelper;

    // create some constants for the database name and version
    public static final String DB_NAME = "accounts.db";
    public static final int DB_VERSION = 1;

    // create some constants for our table and fields
    public static final String ACCOUNT_TABLE = "Accounts";

    public static final String ID = "_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PRICE_RANGE = "priceRange";
    public static final String CLOTHES_CATEGORY = "clothesCategory";
    public static final String CARD_TYPE = "cardType";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String EXPIRY_DATE = "expiryDate";
    public static final String CVC_CODE = "cvcCode";


    // creating a format String to make it less error prone
    private final static String FORMAT =
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT,%s TEXT, %s TEXT, %s TEXT ,%s TEXT ,%s TEXT,%s TEXT,%s TEXT)";

    // now create the DDL query String for the table
    public static final String CREATE_ACCOUNT_TABLE =
            String.format(FORMAT, ACCOUNT_TABLE, ID, USERNAME, PASSWORD, PRICE_RANGE,
                    CLOTHES_CATEGORY, CARD_TYPE, CARD_NUMBER, EXPIRY_DATE, CVC_CODE);

    // Creating the constructor
    public AccountDb(Context context) {
        openHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);

    }

    public Account saveAccount(Account account) {

        database = openHelper.getWritableDatabase();

        // creating ContentValues to hold the field/name value pairs
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, account.getUsername());
        cv.put(PASSWORD, account.getPassword());
        cv.put(PRICE_RANGE, account.getPriceRange());
        cv.put(CLOTHES_CATEGORY, account.getClothesCategory());
        cv.put(CARD_TYPE, account.getCardType());
        cv.put(CARD_NUMBER, account.getCardNumber());
        cv.put(EXPIRY_DATE, account.getExpiryDate());
        cv.put(CVC_CODE, account.getCvcCode());

        // calling the insert on the database, which returns the ID
        long id = database.insert(ACCOUNT_TABLE, null, cv);

        // setting the ID to the student and return the student to the caller
        account.setDbId(id);
        return account;
    }

    public int deleteAccounts(String name) {

        database = openHelper.getWritableDatabase();

        String where = null;
        String[] whereArgs = null;

        // check to see if name is passed in
        if (name != null && name.length() > 0) {
            //specifying column username and finding rows that matches name
            where = "username = ?";
            whereArgs = new String[]{name};
        }

        // calling the delete on the database, which returns the ID
        int numRows = database.delete(ACCOUNT_TABLE, where, whereArgs);

        database.close();

        return numRows;
    }

    public ArrayList<Account> getAccounts(String name, String passWord) {

        database = openHelper.getReadableDatabase();

        ArrayList<Account> accounts = new ArrayList<>();

        String selection = null;
        String[] selectionArgs = null;


        if (name != null && name.length() > 0 &&
                passWord != null && passWord.length() > 0) {

            // specifying columns username and password
            // and finding row where it matches name and passWord
            selection = "username=? AND password=?";
            selectionArgs = new String[]{name, passWord};
        }

        // get the cursor from the table using query
        Cursor cursor = database.query(
                ACCOUNT_TABLE,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // looping through the cursor add accounts to the ArrayList
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String username = cursor.getString(cursor.getColumnIndex(USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
            String priceRange = cursor.getString(cursor.getColumnIndex(PRICE_RANGE));
            String clothesCategory = cursor.getString(cursor.getColumnIndex(CLOTHES_CATEGORY));
            String cardType = cursor.getString(cursor.getColumnIndex(CARD_TYPE));
            String cardNumber = cursor.getString(cursor.getColumnIndex(CARD_NUMBER));
            String expiryDate = cursor.getString(cursor.getColumnIndex(EXPIRY_DATE));
            String cvcCode = cursor.getString(cursor.getColumnIndex(CVC_CODE));

            accounts.add(new Account(username, password, priceRange,
                    clothesCategory, cardType, cardNumber, expiryDate, cvcCode, id));
        }

        cursor.close();
        database.close();

        return accounts;
    }


    // helper class to handle database creation and upgrades

    private static class DBHelper extends SQLiteOpenHelper {

        // Constructor for our DBHelper
        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //callback method will be invoked when there is a request for
        //a reference to a database that does not exist on the device
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ACCOUNT_TABLE);
        }

        /*
          callback method is called whenever there is a request to open
          a database that has a version number that is higher than the one
          that is currently on the device
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + ACCOUNT_TABLE);
            onCreate(db);

        }
    }
}
