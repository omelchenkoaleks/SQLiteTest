package com.omelchenkoaleks.sqlitetest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.omelchenkoaleks.sqlitetest.model.Contact;

import java.security.cert.Extension;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG_DB = "TAG_DB";
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contacts";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";

    public DBHelper(@Nullable Context context,
                    @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_PHONE + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COL_NAME, contact.getName());
            contentValues.put(COL_EMAIL, contact.getEmail());
            contentValues.put(COL_PHONE, contact.getPhone());

            db.insert(TABLE_NAME, null, contentValues);
        } catch (Exception ex) {
            Log.e(TAG_DB, ex.getMessage(), ex);
        }
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COL_NAME, contact.getName());
            contentValues.put(COL_EMAIL, contact.getEmail());
            contentValues.put(COL_PHONE, contact.getPhone());

            db.update(TABLE_NAME, contentValues, "id = ?",
                    new String[] {String.valueOf(contact.getId())});
        } catch (Exception ex) {
            Log.e(TAG_DB, ex.getMessage(), ex);
        }
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, "id = ?",
                    new String[] {String.valueOf(contact.getId())});
        } catch (Exception ex) {
            Log.e(TAG_DB, ex.getMessage(), ex);
        }
    }

    public ArrayList<Contact> getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex("id")));
                contact.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
                contact.setPhone(cursor.getString(cursor.getColumnIndex(COL_PHONE)));

                contacts.add(contact);
                cursor.moveToNext();
            }
            cursor.close();
            return contacts;
        } catch (Exception ex) {
            return null;
        }
    }
}
