package com.example.projetfinalclientprogmob;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context= getApplicationContext();
        Intent intent = new Intent(context,AffichageAllProduit.class);
        startActivity(intent);
    }

    public void Loggin(View view) {
       // String url ="https://mobile1.dinf.cll.qc.ca/Cataloggeur";
        String url ="http://192.168.5.139/QuiGoAuBled/connect.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
       String Email=((EditText)findViewById(R.id.idEmail)).getText().toString();
        String Password=((EditText)findViewById(R.id.idPassword)).getText().toString();


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
        /*try {
            Context context= getApplicationContext();
            if(response.getString("valide").equals("IsConnect")){

                Intent intent = new Intent(context,AffichageAllProduit.class);
                startActivity(intent);

            }
            else{

                Toast toast = Toast.makeText(context,"Email ou mot de passe invalide", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }


}