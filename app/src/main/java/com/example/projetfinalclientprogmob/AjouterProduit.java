package com.example.projetfinalclientprogmob;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AjouterProduit extends AppCompatActivity {
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);
    }

    public void ajouterProduit(View view) {
    }

    public void chercherPhoto(View view) {
         photo = (ImageView) findViewById(R.id.ImageRechercher);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,1);
        
    }
    public void OnActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if(requestCode == 1 && resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor =this.getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                String  imgPath= cursor.getString(columnIndex);
                cursor.close();
                Bitmap image= BitmapFactory.decodeFile(imgPath);
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
}