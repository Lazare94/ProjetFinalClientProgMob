package com.example.projetfinalclientprogmob;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
    }

    public void ConfirmeLasuppression(View view) {
        Intent intent = getIntent();
        DatabaseHandler db = new DatabaseHandler(this);
        SQLiteDatabase bd = db.getWritableDatabase();
        if (intent.hasExtra("id")) {
           String ida = intent.getStringExtra("id");
           int id= Integer.parseInt(ida);
            db.Delete( id  , bd);
            Context context= getApplicationContext();
            Intent intente = new Intent(context,AffichageAllProduit.class);
            startActivity(intente);
        }
    }

    public void Annuler(View view) {
        Context context= getApplicationContext();
        Intent intente = new Intent(context,AffichageAllProduit.class);
        startActivity(intente);
    }
}