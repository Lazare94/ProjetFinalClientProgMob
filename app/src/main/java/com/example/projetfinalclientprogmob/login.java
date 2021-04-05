package com.example.projetfinalclientprogmob;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
    }

    public void Login(View view) {

        String url ="http://192.168.5.139/QuiGoAuBled/Login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        String Email=((EditText)findViewById(R.id.idEmailLogin)).getText().toString();
        String Mdp=((EditText)findViewById(R.id.idMdpLogin)).getText().toString();
        String Phone=((EditText)findViewById(R.id.idPhoneLogin)).getText().toString();
        Context context=getApplicationContext();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(Mdp);
        ProgressDialog progressDialog= new ProgressDialog(login.this);
        progressDialog.setTitle("vérification des informations");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax( 100 );
        progressDialog.show();

        if (!Email.matches(emailPattern) && Phone.isEmpty())
        {
            Toast.makeText(context,"Veuillez remplir l'email ou le téléphone",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        if(!matcher.matches()){
            Toast.makeText(context,"Le mot de passe est incorrect il doit avoir au moins -1 MAJUSCULE -1 minuscule -1 caractére spécial et 1 nombre", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        StringRequest RequestConnect = new StringRequest( Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ValidationUser( response,Phone);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Email",Email);
                params.put("Phone",Phone);
                params.put("Mdp",Mdp);
                return params;
            }
        };

        requestQueue.add(RequestConnect);
    }

    public void ValidationUser(String response,String b) {
        try {
            Context context= getApplicationContext();
            JSONObject json=new JSONObject(response);
            String message=json.getString("reussir");
            if(message.equals("exist")){

                Toast toast = Toast.makeText(context,"Le numéro  téléphone "+ b+" existe déja", Toast.LENGTH_LONG);
                toast.show();

            }
            else if(message.equals("inscrit")){

                Toast toast = Toast.makeText(context,"Inscription a reussie ", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(context,"Inscription a échoué ", Toast.LENGTH_LONG);
                toast.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}