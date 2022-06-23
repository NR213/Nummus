package com.example.nummus;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.os.IResultReceiver;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
        //DB.execSQL("create Table UserGoals(GoalId INTEGER primary key, GoalName TEXT, thevalue TEXT)");
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

    public Boolean deletedata(String number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where number = ?", new String[]{number});
        if (cursor.getCount() > 0) {
           long result = DB.delete("Userdetails", "number=?", new String[]{number});

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

    public Cursor getViewdata(String date, String category, String amountmin, String amountmax, String payment) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = null;
        if(amountmin.isEmpty() && amountmax.isEmpty() && category.equals("none") && date.isEmpty() && payment.equals("none")) {
             cursor = DB.rawQuery("Select * from Userdetails ORDER BY rowid DESC", null);

        }else if (category.equals("none") && amountmin.isEmpty() && amountmax.isEmpty() && payment.equals("none")) {

             cursor = DB.rawQuery("Select * from Userdetails where doT = ? ORDER BY rowid DESC ", new String[]{date});

        }
        else if (amountmin.isEmpty() && amountmax.isEmpty() && !date.isEmpty() && !category.equals("none") && payment.equals("none"))
         {
            cursor = DB.rawQuery("Select * from Userdetails where doT = ? and cat = ? ORDER BY rowid DESC ", new String[]{date, category});

        }
        else if (amountmin.isEmpty() && amountmax.isEmpty() && !date.isEmpty() && !category.equals("none") && !payment.equals("none"))
        {
            cursor = DB.rawQuery("Select * from Userdetails where doT = ? and cat = ? and paymentMethod = ? ORDER BY rowid DESC ", new String[]{date, category, payment});

        }
        else if(!date.isEmpty() && !amountmin.isEmpty() && !amountmax.isEmpty() && !category.equals("none") && !payment.equals("none"))
        {
            cursor = DB.rawQuery("Select * from Userdetails where doT = ? and cat = ? and paymentMethod = ? and amount >= ? and amount <= ? ORDER BY rowid DESC ", new String[]{date, category, payment, amountmin, amountmax});

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
        Cursor cursor  = DB.rawQuery("Select * from Userdetails ORDER BY rowid DESC LIMIT 1", null);
        return cursor;
    }

    public Cursor getprimarykeycost()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from Cost ", null);
        return cursor;
    }

    public Cursor getprimarykeyearnings()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor  = DB.rawQuery("Select * from Earnings ", null);
        return cursor;
    }

    // Armin Queries--------------

    public Boolean insertGoalsdata(int GoalId, String GoalName, String thevalue) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("GoalId", GoalId);
        contentValues.put("UserGoals", GoalName);
        contentValues.put("thevalue", thevalue);

        long result = DB.insert("UserGoals", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean updateGoalsdata(int GoalId, String GoalName, String thevalue) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("GoalId", GoalId);
        contentValues.put("UserGoals", GoalName);
        contentValues.put("thevalue", thevalue);

        long result = DB.update("UserGoals", contentValues, "GoalName = ?", new String[]{GoalName});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String costgoal(String TheGoalName) {
        String result = "800";
        String query;
        SQLiteDatabase MyDB = this.getWritableDatabase();

        if (TheGoalName.equals("expense")){
            query = "SELECT thevalue FROM UserGoals WHERE GoalName = 'expense'";
        }
        else{
            query = "SELECT thevalue FROM UserGoals WHERE GoalName = 'earning'";
        }

        Cursor c = MyDB.rawQuery(query, null);
        c.moveToFirst();

        //if(c.getString(c.getColumnIndex("thevalue"))!= null){
            //result = c.getString(c.getColumnIndex("thevalue"));
        //}

        c.close();
        MyDB.close();
        return result;

    }


    public ArrayList CostPointer(float CostGoal){

        float TheTotal = TestAmount();
        if (TheTotal >= CostGoal){
            CostGoal = TheTotal;
        }
        float costpercent = TheTotal/(CostGoal*2);
        float beforePoint = (float) (costpercent - 0.01);
        float afterpoint = (float) (0.50-costpercent);

        ArrayList<PieEntry> qrestult = new ArrayList<>();
        qrestult.add(new PieEntry( beforePoint, "before"));
        qrestult.add(new PieEntry( 0.01f, "value"));
        qrestult.add(new PieEntry(afterpoint, "after"));
        qrestult.add(new PieEntry(0.50f, "bellow"));

        return qrestult;
    }

    @SuppressLint("Range")
    public ArrayList<Entry> TimeSeriesData(String SelDuration){

        ArrayList<Entry> Arrayrestult = new ArrayList<>();

        SQLiteDatabase MyDB = getWritableDatabase();

        String query;

        if (SelDuration.equals("7 days")){
            query = "SELECT SUM(amount) as Samount FROM Userdetails GROUP BY doT ORDER BY doT LIMIT(7) ";
        }
        else if (SelDuration.equals("15 days")){
            query = "SELECT SUM(amount) as Samount FROM Userdetails GROUP BY doT ORDER BY doT LIMIT(15)";
        }
        else if (SelDuration.equals("30 days")){
            query = "SELECT SUM(amount) as Samount FROM Userdetails GROUP BY doT ORDER BY doT LIMIt(30)";
        }
        else if (SelDuration.equals("6 months")){
            query = "SELECT SUM(amount) as Samount FROM Userdetails GROUP BY doT ORDER BY doT LIMIt(180)";
        }
        else if (SelDuration.equals("1 year")){
            query = "SELECT SUM(amount) as Samount FROM Userdetails GROUP BY doT ORDER BY doT LIMIt(365)";
        }
        else{
            query = "SELECT SUM(amount) as Samount FROM Userdetails GROUP BY doT ORDER BY doT ";
        }


        //String query = "SELECT SUM(amount) as Samount FROM Userdetails2 GROUP BY doT ORDER BY doT ";

        Cursor c = MyDB.rawQuery(query, null);
        c.moveToFirst();
        int xval = 0;
        while (!c.isAfterLast()){
            int myAmount = c.getInt(c.getColumnIndex("Samount"));
            xval += 1;

            Arrayrestult.add(new Entry( xval ,myAmount));

            c.moveToNext();
        }

        return Arrayrestult;
    }


    @SuppressLint("Range")
    public String databaseTostring(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM Userdetails WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("amount")) != null) {

                dbString += c.getString(c.getColumnIndex("amount"));
                dbString +="\n";
            }
            c.moveToNext();

        }

        db.close();
        return dbString;
    }


    public int TestAmount() {
        int result = 0;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT SUM(amount) as Samount FROM Userdetails ORDER BY doT LIMIt(30)", null);
        if (cursor.moveToFirst()) result = cursor.getInt(0);
        cursor.close();
        MyDB.close();
        return result;

    }


    //------------------------------------------------------


}
