package com.example.projetfinalclientprogmob;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AjouterProduit extends AppCompatActivity {
    ImageView photo;
    Bitmap image;
   private ByteArrayOutputStream objectBytearrayOutputStream;
   private byte[] ImageInbytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);

    }

    public void ajouterProduit(View view) {
        DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase bd=db.getWritableDatabase();
       String Pri= ((EditText)findViewById(R.id.Prix)).getText().toString();
        String NomProduit= ((EditText)findViewById(R.id.NomProduit)).getText().toString();
        String Description= ((EditText)findViewById(R.id.Description)).getText().toString();
        if(Pri.equals("")||NomProduit.equals("")||Description.equals("")){
            Toast toast = Toast.makeText(this,"veuillez entrer le Nom le prix et la description du produit", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(image == null){
            Toast toast = Toast.makeText(this,"veuillez choisir une image", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        double Prix=Double.valueOf(Pri).doubleValue();
        Produit produit=new Produit(NomProduit,Description,Prix,image);
       db.addProduit(bd,produit);
        Toast toast = Toast.makeText(this,"Produit ajouté avec succès", Toast.LENGTH_LONG);
        toast.show();
        Context context= getApplicationContext();
        Intent intent = new Intent(context,AffichageAllProduit.class);
        startActivity(intent);


    }

    public void chercherPhoto(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            startActivityForResult(galleryIntent,1);
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, 2);
            }
            else{
                Toast toast = Toast.makeText(this,"Veuillez activer les permissions dans les paramètres de l'application.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        photo = (ImageView) findViewById(R.id.ImageRechercher);

        try{

            if(requestCode == 1 && resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor =this.getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                String  imgPath= cursor.getString(columnIndex);
                cursor.close();
                 image= BitmapFactory.decodeFile(imgPath);
                photo.setImageBitmap(image);

            }
            else{
                Toast toast = Toast.makeText(this,"Aucune  image sélectionnée", Toast.LENGTH_LONG);
                toast.show();
            }


        } catch(Exception e){

            Toast toast = Toast.makeText(this,"La permission n'a pa été accepté ", Toast.LENGTH_LONG);
            toast.show();
        }


    }

    public void Retour(View view) {
        Context context= getApplicationContext();
        Intent intent = new Intent(context,AffichageAllProduit.class);
        startActivity(intent);
    }
}