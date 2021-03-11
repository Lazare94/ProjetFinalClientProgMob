package com.example.projetfinalclientprogmob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private ByteArrayOutputStream objectBytearrayOutputStream;
    private byte[] ImageInbytes;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Catalogeu";
    // Contacts table name

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOMPRODUIT = "NomProduit";
    private static final String KEY_Prix= "Prix";
    private static final String KEY_Description= "Description";
    private static final String KEY_LIENIMAGE= "LienImage";

    //Créer la table
    private static final String CREER_TABLE=
            "CREATE TABLE " + Produit.TABLE_PRODUIT + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOMPRODUIT + " TEXT," +  KEY_Prix + " double," + KEY_LIENIMAGE + " BLOB," + KEY_Description + " TEXT)";
    private static final String SUPPRIMER_TABLE = "DROP TABLE IF EXISTS " + Produit.TABLE_PRODUIT;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SUPPRIMER_TABLE);
        onCreate(db);
    }

    public List<Produit> getAllProduit() {
        List ListeProduits= new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Produit.TABLE_PRODUIT + " ORDER BY " + KEY_NOMPRODUIT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                byte[] ima=cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(ima, 0, ima.length);

                Produit produit=new Produit(cursor.getString(1),cursor.getString(4),cursor.getDouble(2),bitmap);

                produit.setNomProduit(cursor.getString(cursor.getColumnIndex(KEY_NOMPRODUIT)));
                produit.setDescription(cursor.getString(cursor.getColumnIndex(KEY_Description)));
                produit.setLienImage(bitmap);
                produit.setIdProduit (cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                produit.setPrix (cursor.getDouble(cursor.getColumnIndex(KEY_Prix)));
                ListeProduits.add(produit);
            } while (cursor.moveToNext());
        }
        cursor.close();
         db.close();
        return  ListeProduits;
    }

    public void addProduit(SQLiteDatabase bd,Produit produit){
        Bitmap imageToStoreBitmap=produit.getLienImage();
        objectBytearrayOutputStream= new ByteArrayOutputStream();

        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,objectBytearrayOutputStream);
        ImageInbytes=objectBytearrayOutputStream.toByteArray();
        ContentValues valeurs=new ContentValues();
        valeurs.put(KEY_NOMPRODUIT,produit.getNomProduit());
        valeurs.put(KEY_Description,produit.getDescription());
        valeurs.put(KEY_LIENIMAGE,ImageInbytes);
        valeurs.put(KEY_Prix,produit.getPrix());
        bd.insert(Produit.TABLE_PRODUIT,null,valeurs);

    }

    public void UpdateProduit(Produit produit ,SQLiteDatabase bd){
        ContentValues valeurs = new ContentValues();
        valeurs.put(KEY_NOMPRODUIT,produit.getNomProduit());
        valeurs.put(KEY_Description,produit.getDescription());
       // valeurs.put(KEY_LIENIMAGE,produit.getLienImage());
        valeurs.put(KEY_Prix,produit.getPrix());
        bd.update(Produit.TABLE_PRODUIT,valeurs, KEY_ID + "= ?", new String[]{String.valueOf(Produit._ID)});
    }
    public void Delete(int id  ,SQLiteDatabase bd){
        bd.delete(Produit.TABLE_PRODUIT, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }
}
