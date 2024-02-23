package com.cinntra.standalone.databse;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cinntra.standalone.model.SaleEmployeeResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "salesApp_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(SaleEmployeeResponse.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SaleEmployeeResponse.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /************************** SalesEmployee Data Manager **************************/


    public List<SalesEmployeeItem> getAllEmployees(Context con) {
        List<SalesEmployeeItem> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SaleEmployeeResponse.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SalesEmployeeItem note = new SalesEmployeeItem();
                note.setSalesEmployeeCode(cursor.getString(cursor.getColumnIndex(SaleEmployeeResponse.COLUMN_SalesEmployeeCode)));
                note.setSalesEmployeeName(cursor.getString(cursor.getColumnIndex(SaleEmployeeResponse.COLUMN_SalesEmployeeName)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + SaleEmployeeResponse.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public long insertNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SaleEmployeeResponse.COLUMN_SalesEmployeeCode, note);
        values.put(SaleEmployeeResponse.COLUMN_SalesEmployeeName, note);

        // insert row
        long id = db.insert(SaleEmployeeResponse.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public SaleEmployeeResponse getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SaleEmployeeResponse.TABLE_NAME,
                new String[]{ SaleEmployeeResponse.COLUMN_SalesEmployeeCode, SaleEmployeeResponse.COLUMN_SalesEmployeeName},
                "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        SaleEmployeeResponse note = new SaleEmployeeResponse(
                cursor.getString(cursor.getColumnIndex(SaleEmployeeResponse.COLUMN_SalesEmployeeCode)),
                cursor.getString(cursor.getColumnIndex(SaleEmployeeResponse.COLUMN_SalesEmployeeName)));


        // close the db connection
        cursor.close();

        return note;
    }

}
