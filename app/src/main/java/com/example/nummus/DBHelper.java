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
        super(context, "Userdata9.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails2(doT TEXT , time TEXT, amount TEXT, reference TEXT, paymentMethod TEXT, note TEXT,cat TEXT)");
        DB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        DB.execSQL("create Table Cost(otp TEXT, category TEXT, source TEXT, reason TEXT)");
        DB.execSQL("create Table Earnings(otp TEXT, category TEXT, source TEXT, reason TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails2");
        DB.execSQL("drop Table if exists users");
        DB.execSQL("drop Table if exists Cost");
    }

    public Boolean insertuserdata(String doT, String time, String amount, String reference, String paymentMethod, String note, String cat) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doT", doT);
        contentValues.put("time", time);
        contentValues.put("amount", amount);
        contentValues.put("reference", reference);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("note", note);
        contentValues.put("cat", cat);
        long result = DB.insert("Userdetails2", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateuserdata(String doT, String time, String amount, String reference, String paymentMethod, String note, String cat) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("amount", amount);
        contentValues.put("reference", reference);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("note", note);
        contentValues.put("Category", cat);
        Cursor cursor = DB.rawQuery("Select * from Userdetails2 where doT = ?", new String[]{doT});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails2", contentValues, "doT=?", new String[]{doT});
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
        Cursor cursor = DB.rawQuery("Select * from Userdetails2 where doT = ?", new String[]{doT});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails2", "doT=?", new String[]{doT});
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
        Cursor cursor = DB.rawQuery("Select * from Userdetails2 where doT = ?", new String[]{doT});
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
        Cursor cursor = MyDB.rawQuery("Select SUM(amount) from Userdetails2", null);
        if (cursor.moveToFirst()) result = cursor.getInt(0);
        cursor.close();
        MyDB.close();
        return result;

    }

    public Cursor getfilterdata(String filter) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails2 where paymentMethod = ? ORDER BY rowid DESC LIMIT 2", new String[]{filter});
        return cursor;
    }

    public Boolean insertCostdata(String otp, String category, String source, String reason) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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

    public Boolean insertEarningsdata(String otp, String category, String source, String reason) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
             cursor = DB.rawQuery("Select * from Userdetails2 where cat = ? ORDER BY rowid DESC LIMIT 2", new String[]{value});

        }
        if(value.equals("Earnings")) {
             cursor = DB.rawQuery("Select * from Userdetails2 where cat = ? ORDER BY rowid DESC LIMIT 2", new String[]{value});

        }
        if(value.equals("Cash")) {
            cursor = DB.rawQuery("Select * from Userdetails2 where paymentMethod = ? ORDER BY rowid DESC LIMIT 2", new String[]{value});

        }
        if(value.equals("Card")) {
            cursor = DB.rawQuery("Select * from Userdetails2 where paymentMethod = ? ORDER BY rowid DESC LIMIT 2", new String[]{value});

        }
        if(value.equals("Other")) {
            cursor = DB.rawQuery("Select * from Userdetails2 where paymentMethod = ? ORDER BY rowid DESC LIMIT 2", new String[]{value});

        }
        if(value.equals("Date")) {
            cursor = DB.rawQuery("Select * from Userdetails2  ORDER BY rowid DESC LIMIT 2", null );

        }
        return cursor;
    }
}
