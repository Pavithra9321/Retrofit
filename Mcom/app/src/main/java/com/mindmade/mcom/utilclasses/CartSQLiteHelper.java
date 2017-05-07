package com.mindmade.mcom.utilclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mindmade.mcom.utilclasses.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public class CartSQLiteHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;


    public CartSQLiteHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Const.TABLE_CART + "("
                + Const.ID_COLUMN + " INTEGER PRIMARY KEY," + Const.NAME_COLUMN + " TEXT,"
                + Const.PRICE_COLUMN + " TEXT," + Const.IMAGE_COLUMN + " TEXT," + Const.QTY_COLUMN + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Const.TABLE_CART);
        // Create tables again
        onCreate(db);
    }


    // Adding new contact
    void addToCart(CartProduct cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (getCartCount() > 0) {
            updateCart(cart);
        } else {
            ContentValues values = new ContentValues();
            values.put(Const.ID_COLUMN, cart.getId());
            values.put(Const.NAME_COLUMN, cart.getName());
            values.put(Const.PRICE_COLUMN, cart.getPrice());
            values.put(Const.IMAGE_COLUMN, cart.getImg_url());
            values.put(Const.QTY_COLUMN, cart.getQty());
            // Inserting Row
            db.insert(Const.TABLE_CART, null, values);
        }

        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<CartProduct> getAllCartItems() {
        List<CartProduct> contactList = new ArrayList<CartProduct>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Const.TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartProduct contact = new CartProduct();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setImg_url(cursor.getString(3));
                contact.setQty(cursor.getInt(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateCart(CartProduct cartProduct) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Const.ID_COLUMN, cartProduct.getId());
        values.put(Const.NAME_COLUMN, cartProduct.getName());
        values.put(Const.PRICE_COLUMN, cartProduct.getPrice());
        values.put(Const.IMAGE_COLUMN, cartProduct.getImg_url());
        values.put(Const.QTY_COLUMN, cartProduct.getQty());

        // updating row
        return db.update(Const.TABLE_CART, values, Const.ID_COLUMN + " = ?",
                new String[]{String.valueOf(cartProduct.getId())});
    }


    // Deleting single contact
    public void deleteCart(CartProduct cartProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Const.TABLE_CART, Const.ID_COLUMN + " = ?",
                new String[]{String.valueOf(cartProduct.getId())});
        db.close();
    }


    // Getting contacts Count
    public int getCartCount() {
        String countQuery = "SELECT  * FROM " + Const.TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
