package com.example.projetfinalclientprogmob;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.projetfinalclientprogmob.R.drawable.carrot;

public class AffichageAllProduit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_all_produit);
        final ListView list = findViewById(R.id.idListViewAll);
        ImageView imageview;
        imageview=(ImageView) findViewById(R.id.idimage);
       // ListView listView;
        DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase bd = db.getReadableDatabase();
        String var= "com.my.app:drawable/";
        List ListProduit;
        ListProduit=db.getAllProduit();
        ArrayList<HashMap<String, Object>> liste = new ArrayList<>();
        for (int i = 0; i <ListProduit.size(); i++) {
           Produit  produit =( Produit)ListProduit.get(i);
          // String vare=var+ produit.getLienImage();
           // int id = getResources().getIdentifier(, type, package);
           // int id = getResources().getIdentifier(vare, null, null);
           int id = getResources().getIdentifier( produit.getLienImage(), "drawable", getPackageName());
            HashMap<String, Object> map = new HashMap<>();
            map.put("NomProduit", produit.getNomProduit());
            map.put("Description",produit.getDescription());
            map.put("Prix", produit.getPrix());
            map.put("LienImage",id);
            liste.add(map);
        }
        String[] from = {"NomProduit","Description","Prix","LienImage"};
        int to[] = {R.id.idNomProduit,R.id.idDescription,R.id.idPrix,R.id.idimage};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), liste,  R.layout.itemlist , from,to);
        list.setAdapter(simpleAdapter);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
    public void AjouteProduit(View view) {
        dispatchTakePictureIntent();
    }


}