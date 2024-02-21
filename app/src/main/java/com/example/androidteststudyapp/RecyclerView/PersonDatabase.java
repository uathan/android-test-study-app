package com.example.androidteststudyapp.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidteststudyapp.pojo.Person;

import java.util.ArrayList;

public class PersonDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "famouspeople";

    public static final String TABLE_PEOPLE = "People";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FNAME = "fname";
    public static final String COLUMN_LNAME = "lname";
    public static final String COLUMN_AGE = "age";

    public static final String CREATE_PEOPLE_TABLE = "CREATE TABLE " +
            TABLE_PEOPLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_FNAME + " TEXT, " + COLUMN_LNAME + " TEXT, " +
            COLUMN_AGE + " TEXT)";

    public PersonDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PEOPLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FNAME, person.getFname());
        values.put(COLUMN_LNAME, person.getLname());
        values.put(COLUMN_AGE, person.getAge());
        db.insert(TABLE_PEOPLE, null, values);
        db.close();
        Log.d("SQL", "Game Added");
    }

    public Person getPerson(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Person person = null;
        Cursor cursor = db.query(
                TABLE_PEOPLE, new String[]{COLUMN_ID, COLUMN_FNAME, COLUMN_LNAME,
                        COLUMN_AGE}, COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.moveToFirst()){
            person = new
                    Person(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        }
        db.close();
        return person;
    }

    public ArrayList<Person> getAllPeople(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Person> people = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PEOPLE, null);
        while(cursor.moveToNext()){
            people.add(new
                    Person(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)));
        }
        db.close();
        return people;
    }

    public int updatePerson(Person person){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FNAME, person.getFname());
        values.put(COLUMN_LNAME, person.getLname());
        values.put(COLUMN_AGE, person.getAge());
        return db.update(TABLE_PEOPLE, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(person.getId())});
    }

    public void deletePerson(int game){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PEOPLE, COLUMN_ID + "=?",
                new String[]{String.valueOf(game)});
        db.close();
    }

    public void deleteAllPeople(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PEOPLE, null, null);
        db.close();
    }

}
