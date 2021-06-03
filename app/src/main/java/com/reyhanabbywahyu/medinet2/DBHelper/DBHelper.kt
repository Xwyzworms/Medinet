package com.reyhanabbywahyu.medinet2.DBHelper
/*
import android.annotation.SuppressLint
import android.app.Person
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import com.reyhanabbywahyu.medinet2.`class`.User


val DATABASE_VER = 1
val DATABASE_NAME = "cek3.db"

//table structure
val TABLE_NAME = "user"
val COL_ID ="Id"
val COL_NAMA = "Nama"
val COL_EMAIL = "Email"
val COL_PASSWORD= "Password"
val COL_BALANCE ="Balance"
val COL_ALAMAT = "Alamat"
val COL_TGLAHIR = "TglLahir"
val COL_BERAT = "Berat"
val COL_TINGGI = "Tinggi"
class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VER){
    override fun onCreate(db: SQLiteDatabase?) {

        val Create_Table : String ="CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "$COL_NAMA VARCHAR(50) NOT NULL, $COL_EMAIL VARCHAR(50) NOT NULL, $COL_ALAMAT TEXT ," +
                "$COL_PASSWORD VARCHAR(30) NOT NULL, $COL_TINGGI FLOAT,$COL_BERAT FLOAT,$COL_BALANCE REAL NOT NULL, $COL_TGLAHIR TEXT)"
        db!!.execSQL(Create_Table)

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
//Get
@SuppressLint("Recycle")
fun getAllData() : List<User> {
        val semuaUser = ArrayList<User>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db : SQLiteDatabase = this.writableDatabase
        val cursor : Cursor = db.rawQuery(query,null)
            if (cursor.moveToFirst()) {
                do {
                    val person = User()
                        person.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                        person.nama = cursor.getString(cursor.getColumnIndex(COL_NAMA))
                        person.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                        person.balance = cursor.getFloat(cursor.getColumnIndex(COL_BALANCE))
                        person.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                        person.alamat = cursor.getString(cursor.getColumnIndex(COL_ALAMAT))

                    semuaUser.add(person)
                }while(cursor.moveToNext())
            }
        db.close()
        return semuaUser
    }
    //Insert data

    fun insert_data(person : User) {
        val db: SQLiteDatabase =this.writableDatabase
        val values = ContentValues()
        Log.d("SQLSQL",person.email)
        values.put(COL_BALANCE,person.balance)
        values.put(COL_NAMA,person.nama)
        values.put(COL_EMAIL,person.email)
        values.put(COL_PASSWORD,person.password)
        values.put(COL_TGLAHIR,person.tglLahir)
        values.put(COL_ALAMAT, "Blank")
        values.put(COL_BALANCE,0.0f)
        values.put(COL_BERAT,0.0f)
        values.put(COL_TINGGI,0.0f)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getDataBasedEmail(email : String)  : User?{
        val db : SQLiteDatabase = this.writableDatabase
        var orangyangDicari: User = User()
        var cursor: Cursor? =null
        var Query  : String = "SELECT * FROM $TABLE_NAME WHERE $COL_EMAIL='$email'"
        try {
             cursor = db.rawQuery(Query, null)
            if (cursor.moveToFirst()) {
                do {
                    orangyangDicari.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    orangyangDicari.balance = cursor.getFloat(cursor.getColumnIndex(COL_BALANCE))
                    orangyangDicari.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                    orangyangDicari.nama = cursor.getString(cursor.getColumnIndex(COL_NAMA))
                    orangyangDicari.alamat = cursor.getString(cursor.getColumnIndex(COL_ALAMAT))
                    orangyangDicari.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                    orangyangDicari.berat = cursor.getFloat(cursor.getColumnIndex(COL_BERAT))
                    orangyangDicari.tinggi = cursor.getFloat(cursor.getColumnIndex(COL_TINGGI))
                } while (cursor.moveToNext())
            }
        }
        catch (e : SQLiteException) {
            Log.d("SQLError",e.message.toString())
        }
        db.close()
        if (orangyangDicari.equals(null)) {
            return  null
        }
        return orangyangDicari
    }

    fun updateUserBiodata(person :User) : Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAMA, person.nama)
        values.put(COL_TGLAHIR,person.tglLahir)

        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(person.id.toString()))
    }

    fun updateUserAkun(person: User) : Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PASSWORD, person.password)
        values.put(COL_EMAIL, person.email)
        return  db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(person.id.toString()))
    }

    fun updateUserPersonal(person: User) : Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_BERAT, person.berat)
        values.put(COL_TINGGI, person.tinggi)
        return  db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(person.id.toString()))
    }

    fun deletePersonalData(person : User)  {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(person.id.toString()))
        db.close()
    }

}

 */