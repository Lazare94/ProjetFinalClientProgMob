package com.example.projetfinalclientprogmob;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Loggin(View view) {
        String url ="http://192.168.5.139/QuiGoAuBled/Enregistrement.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        StringRequest RequestConnect = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                ValidationUser( response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(RequestConnect);


    }

    public void ValidationUser(String response) {
        try {
            Context context= getApplicationContext();
             JSONObject json=new JSONObject(response);
             String message=json.getString("reussir");
            if(message.equals("exist")){

                Toast toast = Toast.makeText(context,"Ce numéro de téléphone existe déja", Toast.LENGTH_LONG);
                toast.show();

            }
            else{

                Toast toast = Toast.makeText(context,"Inscription reussie ", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void log(View view) {
        Context context= getApplicationContext();
        Intent intent = new Intent(context,login.class);
        startActivity(intent);
    }

    public void insc(View view) {
        Context context= getApplicationContext();
        Intent intent = new Intent(context,Register.class);
        startActivity(intent);

    }


}