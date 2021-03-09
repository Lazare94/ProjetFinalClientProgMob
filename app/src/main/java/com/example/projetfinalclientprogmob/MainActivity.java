package com.example.projetfinalclientprogmob;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        SQLiteDatabase bd = db.getReadableDatabase();
        db.onUpgrade(bd,1,1);

        db.addProduit(bd,new Produit("Blé",getString(R.string.description),15.59,"ble"));
        db.addProduit(bd,new Produit("Carrot",getString(R.string.description),14.22,"carrot"));
        db.addProduit(bd,new Produit("Poisson",getString(R.string.description),9.20,"poisson"));
        db.addProduit(bd,new Produit("Pomme",getString(R.string.description),10.22,"pomme"));
        db.addProduit(bd,new Produit("Porc",getString(R.string.description),45.59,"porc"));
        db.addProduit(bd,new Produit("Viande",getString(R.string.description),53.22,"viande"));
        db.addProduit(bd,new Produit("Blé",getString(R.string.description),15.59,"ble"));
        db.addProduit(bd,new Produit("Carrot",getString(R.string.description),14.22,"carrot"));
        db.addProduit(bd,new Produit("Poisson",getString(R.string.description),9.20,"poisson"));
        db.addProduit(bd,new Produit("Pomme",getString(R.string.description),10.22,"pomme"));
        db.addProduit(bd,new Produit("Porc",getString(R.string.description),45.59,"porc"));
        db.addProduit(bd,new Produit("Viande",getString(R.string.description),53.22,"viande"));
        db.close();

    }

    public void Loggin(View view) {
        String url ="https://mobile1.dinf.cll.qc.ca/Cataloggeur";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
       String Email=((EditText)findViewById(R.id.idEmail)).getText().toString();
        String Password=((EditText)findViewById(R.id.idPassword)).getText().toString();
       // url+="?username" +Email + "&Password=" + Password;
        JSONObject postData = new JSONObject();
        try {
            postData.put("email", Email);
            postData.put("mdp", Password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                ValidationUser( response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    public void ValidationUser(JSONObject response) {
        try {

            if(response.getString("valide").equals("IsConnect")){

                Context context= getApplicationContext();
                Intent intent = new Intent(context,AffichageAllProduit.class);
                startActivity(intent);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}