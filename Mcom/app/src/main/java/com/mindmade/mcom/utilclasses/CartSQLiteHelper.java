package com.mindmade.mcom.utilclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                + Const.PRICE_COLUMN + " TEXT," + Const.IMAGE_COLUMN + " TEXT," + Const.QTY_COLUMN + " INTEGER," + Const.TOTAL_PRICE_COLUMN + " TEXT" + ")";
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
    public void addToCart(CartProduct cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (getCartCount(cart) > 0) {
            //   Log.d("Success", "Updated Status:::: " + updateCart(cart));
        } else {
            ContentValues values = new ContentValues();
            values.put(Const.ID_COLUMN, cart.getId());
            values.put(Const.NAME_COLUMN, cart.getName());
            values.put(Const.PRICE_COLUMN, cart.getPrice());
            values.put(Const.IMAGE_COLUMN, cart.getImg_url());
            values.put(Const.QTY_COLUMN, cart.getQty());
            values.put(Const.TOTAL_PRICE_COLUMN, cart.getTotal());
            Log.w("Success", "ID:::: " + cart.getId());
            Log.w("Success", "Name:::: " + cart.getName());
            Log.w("Success", "Image:::: " + cart.getImg_url());
            Log.w("Success", "Price:::: " + cart.getPrice());
            Log.w("Success", "QTY:::: " + cart.getQty());
            Log.w("Success", "TOTAL:::: " + cart.getTotal());
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

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartProduct contact = new CartProduct();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setImg_url(cursor.getString(3));
                contact.setQty(cursor.getInt(4));
                contact.setTotal(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public boolean updateCart(CartProduct cartProduct) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        // values.put(Const.ID_COLUMN, cartProduct.getId());
        values.put(Const.NAME_COLUMN, cartProduct.getName());
        values.put(Const.PRICE_COLUMN, cartProduct.getPrice());
        values.put(Const.IMAGE_COLUMN, cartProduct.getImg_url());
        values.put(Const.QTY_COLUMN, cartProduct.getQty());
        values.put(Const.TOTAL_PRICE_COLUMN, cartProduct.getTotal());
        Log.w("Success", "ID:::: " + values.get(Const.ID_COLUMN));
        Log.w("Success", "Name:::: " + values.get(Const.NAME_COLUMN));
        Log.w("Success", "Image:::: " + values.get(Const.IMAGE_COLUMN));
        Log.w("Success", "Price:::: " + values.get(Const.PRICE_COLUMN));
        Log.w("Success", "QTY:::: " + values.get(Const.QTY_COLUMN));
        Log.w("Success", "TOTAL:::: " + values.get(Const.TOTAL_PRICE_COLUMN));

        String strSQL = "UPDATE " + Const.TABLE_CART + " SET " + Const.QTY_COLUMN + " = " + cartProduct.getQty() + "," + Const.PRICE_COLUMN + "='" + cartProduct.getPrice() + "' WHERE " + Const.ID_COLUMN + " = " + cartProduct.getId();
        Log.d("Success", "Update Query::: " + strSQL);
        try {
            db.execSQL(strSQL);
        } catch (SQLException e) {
            Log.e("SQLException", "" + e);
        }

        // updating row
        long i = db.update(Const.TABLE_CART, values, Const.ID_COLUMN + " = ?", new String[]{cartProduct.getId()});
        //long i = db.update(Const.TABLE_CART, values, Const.ID_COLUMN + "=?" + cartProduct.getId(), null);
        Log.d("Success", "Update Long::: " + i);
        if (i > 0)
            return true;  // 1 for successful
        else
            return false;  // 0 for unsuccessful
    }


    // Deleting single contact
    public void deleteCart(CartProduct cartProduct) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(Const.TABLE_CART, Const.ID_COLUMN + " = ?",
                new String[]{String.valueOf(cartProduct.getId())});
        db.close();
    }


    // Getting contacts Count
    public int getCartCount(CartProduct cartProduct) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + Const.TABLE_CART + " WHERE " + Const.ID_COLUMN + "=" + cartProduct.getId();

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        //  db.close();
        return count;
    }

    public String getTotalPrice(){
       float total = 0;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Const.TABLE_CART;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                /*CartProduct contact = new CartProduct();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setImg_url(cursor.getString(3));
                contact.setQty(cursor.getInt(4));
                contact.setTotal(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);*/
                total=total+Float.parseFloat(cursor.getString(5));
            } while (cursor.moveToNext());
        }
        Log.w("Success","Total Price:: "+total);
        return String.valueOf(total);
    }
}
