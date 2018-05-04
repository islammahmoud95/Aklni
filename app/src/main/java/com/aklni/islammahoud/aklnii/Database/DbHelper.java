package com.aklni.islammahoud.aklnii.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import com.aklni.islammahoud.aklnii.Data.Cart;

import java.util.ArrayList;

import com.aklni.islammahoud.aklnii.Database.Contract.*;

/**
 * Created by islam mahoud on 1/31/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cartOrder.db";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_ORDER_TABLE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FeedEntry.COLUMN_CAT + " TEXT, " +
                FeedEntry.COLUMN_ITEMNAME + " TEXT," +
                FeedEntry.COLUMN_CHEQUE + " TEXT," +
                FeedEntry.COLUMN_NUM + " TEXT," +
                FeedEntry.COLUMN_EXTRA + " TEXT," +
                FeedEntry.COLUMN_STATUES+  " INTEGER"+"); ";

        db.execSQL(CREATE_ORDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "  + Contract.FeedEntry.TABLE_NAME);
        onCreate(db);
    }
    public void addorder(Cart cart)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FeedEntry.COLUMN_CAT,cart.getFoodcategory());
        values.put(Contract.FeedEntry.COLUMN_ITEMNAME,cart.getName());
        values.put(Contract.FeedEntry.COLUMN_CHEQUE,cart.getPrice());
        values.put(Contract.FeedEntry.COLUMN_NUM,cart.getNum());
        values.put(FeedEntry.COLUMN_EXTRA,cart.getExtra());
        values.put(FeedEntry.COLUMN_STATUES,cart.getCheck());
        db.insert(Contract.FeedEntry.TABLE_NAME,null,values);
        db.close();
    }
    public ArrayList<Cart> getallcart() {
        ArrayList<Cart> carts = new ArrayList<>();
        String query ="SELECT  * FROM " + Contract.FeedEntry.TABLE_NAME + " WHERE "+FeedEntry.COLUMN_STATUES +"=1";
        SQLiteDatabase dp = this.getWritableDatabase();
        Cursor cursor = dp.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setId(cursor.getInt(0));
                cart.setFoodcategory(cursor.getString(1));
                cart.setName(cursor.getString(2));
                cart.setPrice(cursor.getString(3));
                cart.setNum(cursor.getString(4));
                cart.setExtra(cursor.getString(5));
                carts.add(cart);
            } while (cursor.moveToNext());
        }
return carts;
    }
    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME,null,null);
        db.close();
    }
    public void deleteContact(Cart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(FeedEntry.TABLE_NAME,FeedEntry._ID + " = ?",new String[] { String.valueOf(cart.getId()) });
        db.close();
    }
}
