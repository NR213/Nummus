package com.example.nummus;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata16.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(number INTEGER primary key AUTOINCREMENT , doT TEXT , time TEXT, amount TEXT, currency TEXT,converteuro TEXT, paymentMethod TEXT, note TEXT,cat TEXT)");
        DB.execSQL("create Table users(username TEXT, password TEXT)");
        DB.execSQL("create Table Cost(numbercost TEXT,otp TEXT, category TEXT, source TEXT, reason TEXT)");
        DB.execSQL("create Table Earnings(numberearnings TEXT,otp TEXT, category TEXT, source TEXT, reason TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
        DB.execSQL("drop Table if exists users");
        DB.execSQL("drop Table if exists Cost");
        DB.execSQL("drop Table if exists Earnings");
    }

    public Boolean insertuserdata(String doT, String time, String amount, String currency,String converteuro, String paymentMethod, String note, String cat) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doT", doT);
        contentValues.put("time", time);
        contentValues.put("amount", amount);
        contentValues.put("Currency", currency);
        contentValues.put("converteuro", converteuro);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("note", note);
        contentValues.put("cat", cat);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateuserdata(String doT, String time, String amount, String currency, String paymentMethod, String note, String cat) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("amount", amount);
        contentValues.put("Currency", currency);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("note", note);
        contentValues.put("Category", cat);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where doT = ?", new String[]{doT});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "doT=?", new String[]{doT});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deletedata(String doT) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where doT = ?", new String[]{doT});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "doT=?", new String[]{doT});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata(String doT) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where doT = ?", new String[]{doT});
        return cursor;
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public int sumAmount() {
        int result = 0;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select SUM(amount) from Userdetails", null);
        if (cursor.moveToFirst()) result = cursor.getInt(0);
        cursor.close();
        MyDB.close();
        return result;

    }

    public Cursor getfilterdata(String filter) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where paymentMethod = ? ORDER BY rowid DESC LIMIT 2", new String[]{filter});
        return cursor;
    }

    public Boolean insertCostdata(String numbercost,String otp, String category, String source, String reason) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("numbercost", numbercost);
        contentValues.put("otp", otp);
        //contentValues.put("fixedpayment", fixedpayment);
        contentValues.put("category", category);
        contentValues.put("source", source);
        contentValues.put("reason", reason);

        long result = DB.insert("Cost", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertEarningsdata(String numberearnings,String otp, String category, String source, String reason) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("numberearnings", numberearnings);
        contentValues.put("otp", otp);
        //contentValues.put("fixedpayment", fixedpayment);
        contentValues.put("category", category);
        contentValues.put("source", source);
        contentValues.put("reason", reason);

        long result = DB.insert("Earnings", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getViewdata(String value) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = null;
        if(value.equals("Cost")) {
             cursor = DB.rawQuery("Select * from Userdetails where cat = ? ORDER BY rowid DESC ", new String[]{value});

        }
        if(value.equals("Earnings")) {
             cursor = DB.rawQuery("Select * from Userdetails where cat = ? ORDER BY rowid DESC ", new String[]{value});

        }
        if(value.equals("Cash")) {
            cursor = DB.rawQuery("Select * from Userdetails where paymentMethod = ? ORDER BY rowid DESC ", new String[]{value});

        }
        if(value.equals("Card")) {
            cursor = DB.rawQuery("Select * from Userdetails where paymentMethod = ? ORDER BY rowid DESC ", new String[]{value});

        }
        if(value.equals("Other")) {
            cursor = DB.rawQuery("Select * from Userdetails where paymentMethod = ? ORDER BY rowid DESC ", new String[]{value});

        }
        if(value.equals("Date")) {
            cursor = DB.rawQuery("Select * from Userdetails  ORDER BY rowid DESC ", null );

        }
        return cursor;
    }

    public Cursor getdata()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from Userdetails ORDER BY doT DESC", null);
        return cursor;
    }

    public Cursor getprimarykey()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from Userdetails ORDER BY doT DESC LIMIT 1", null);
        return cursor;
    }
}
