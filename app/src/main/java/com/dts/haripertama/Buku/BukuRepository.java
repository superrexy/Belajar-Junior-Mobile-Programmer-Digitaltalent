package com.dts.haripertama.Buku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BukuRepository extends SQLiteOpenHelper {
    // static variable
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "buku_db";
    private static final String TABLE_NAME = "buku";

    //    Columns
    private static final String KEY_ID = "id";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_DESKRIPSI = "deskripsi";
    private static final String KEY_TANGGAL = "tanggal";

    public BukuRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_JUDUL + " TEXT,"
                + KEY_DESKRIPSI + " TEXT," + KEY_TANGGAL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // on Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBuku(BukuModel buku) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_JUDUL, buku.getJudul());
        values.put(KEY_DESKRIPSI, buku.getDeskripsi());
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<BukuModel> getAllBuku() {
        ArrayList<BukuModel> bukuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                BukuModel buku = new BukuModel();
                buku.setId(Integer.parseInt(cursor.getString(0)));
                buku.setJudul(cursor.getString(1));
                buku.setDeskripsi(cursor.getString(2));
                bukuList.add(buku);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bukuList;
    }

    public BukuModel getBukuById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_JUDUL, KEY_DESKRIPSI}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        assert cursor != null;

        BukuModel buku = new BukuModel();
        buku.setId(Integer.parseInt(cursor.getString(0)));
        buku.setJudul(cursor.getString(1));
        buku.setDeskripsi(cursor.getString(2));

        cursor.close();
        return buku;
    }

    public void updateBuku(BukuModel buku) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_JUDUL, buku.getJudul());
        values.put(KEY_DESKRIPSI, buku.getDeskripsi());
        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(buku.getId())});

        db.close();
    }

    public void deleteBuku(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
