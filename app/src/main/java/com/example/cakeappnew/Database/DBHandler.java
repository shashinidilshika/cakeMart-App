package com.example.cakeappnew.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Cakes.CakeItem.TABLE_NAME + " (" +
                    Cakes.CakeItem._ID + " INTEGER PRIMARY KEY," +
                    Cakes.CakeItem.COLUMN_1+ " TEXT," +
                    Cakes.CakeItem.COLUMN_2 + " TEXT," +
                    Cakes.CakeItem.COLUMN_3 + " TEXT," +
                    Cakes.CakeItem.COLUMN_4 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Cakes.CakeItem.TABLE_NAME;


    public long addInfo (String itemCode, String itemName, String itemType, String itemPrice) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Cakes.CakeItem.COLUMN_1, itemCode);
        values.put(Cakes.CakeItem.COLUMN_2, itemName);
        values.put(Cakes.CakeItem.COLUMN_3, itemType);
        values.put(Cakes.CakeItem.COLUMN_4, itemPrice);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Cakes.CakeItem.TABLE_NAME, null, values);

        return newRowId;
    }

    // update function

    public Boolean updateInfo (String itemCode, String itemName, String itemType, String itemPrice){

        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Cakes.CakeItem.COLUMN_1, itemCode);
        values.put(Cakes.CakeItem.COLUMN_2, itemName);
        values.put(Cakes.CakeItem.COLUMN_3, itemType);
        values.put(Cakes.CakeItem.COLUMN_4, itemPrice);

        // Which row to update, based on the title
        String selection = Cakes.CakeItem.COLUMN_2+ " LIKE ?";
        String[] selectionArgs = { "itemName" };

        int count = db.update(
                Cakes.CakeItem.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count >= 1){
            return true;
        }
        else {
            return false;
        }
    }

    //Delete function

    public void deleteInfo (String itemName) {

        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = Cakes.CakeItem.COLUMN_2 + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { itemName };
        // Issue SQL statement.
        int deletedRows = db.delete(Cakes.CakeItem.TABLE_NAME, selection, selectionArgs);

    }

    //Read all

    public List readAllInfo(){

        String itemName = "Chocolate cake";
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                BaseColumns._ID,
                Cakes.CakeItem.COLUMN_1,
                Cakes.CakeItem.COLUMN_2,
                Cakes.CakeItem.COLUMN_3,
                Cakes.CakeItem.COLUMN_4,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Cakes.CakeItem.COLUMN_2 + " = ?";
        String[] selectionArgs = { itemName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Cakes.CakeItem.COLUMN_2+ " ASC";

        Cursor cursor = db.query(
                Cakes.CakeItem.TABLE_NAME,      // The table to query
                projection,                     // The array of columns to return (pass null to get all)
                null,                   // The columns for the WHERE clause
                null,                // The values for the WHERE clause
                null,                   // don't group the rows
                null,                    // don't filter by row groups
                sortOrder                      // The sort order
        );

        List itemnames = new ArrayList<>();
        while(cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndexOrThrow(Cakes.CakeItem.COLUMN_2));
            itemnames.add(item);
        }
        cursor.close();
        return itemnames;
    }

    public List readAllInfo(String itemName){

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                BaseColumns._ID,
                Cakes.CakeItem.COLUMN_1,
                Cakes.CakeItem.COLUMN_2,
                Cakes.CakeItem.COLUMN_3,
                Cakes.CakeItem.COLUMN_4,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Cakes.CakeItem.COLUMN_2 + " = LIKE ?";
        String[] selectionArgs = { itemName };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Cakes.CakeItem.COLUMN_2 + " ASC";

        Cursor cursor = db.query(
                Cakes.CakeItem.TABLE_NAME,      // The table to query
                projection,                     // The array of columns to return (pass null to get all)
                selection,                       // The columns for the WHERE clause
                selectionArgs,                   // The values for the WHERE clause
                null,                   // don't group the rows
                null,                    // don't filter by row groups
                sortOrder                      // The sort order
        );

        List itemInfo = new ArrayList<>();
        while(cursor.moveToNext()) {
            String itemCode = cursor.getString(cursor.getColumnIndexOrThrow(Cakes.CakeItem.COLUMN_1));
            String item = cursor.getString(cursor.getColumnIndexOrThrow(Cakes.CakeItem.COLUMN_2));
            String itemType = cursor.getString(cursor.getColumnIndexOrThrow(Cakes.CakeItem.COLUMN_3));
            String itemPrice = cursor.getString(cursor.getColumnIndexOrThrow(Cakes.CakeItem.COLUMN_4));
            itemInfo.add(itemCode);
            itemInfo.add(item);
            itemInfo.add(itemType);
            itemInfo.add(itemPrice);
        }
        cursor.close();
        return itemInfo;
    }
}
