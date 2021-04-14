package com.example.projetfinalclientprogmob;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
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


    public void Inscription(View view) {

        String url ="http://192.168.5.139/QuiGoAuBled/Enregistrement.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        String Email=((EditText)findViewById(R.id.idEmailUser)).getText().toString();
        String Mdp=((EditText)findViewById(R.id.idMdp)).getText().toString();
        String Phone=((EditText)findViewById(R.id.idPhone)).getText().toString();
        String Nom  =((EditText)findViewById(R.id.idNomUser)).getText().toString();
        String Prenom=((EditText)findViewById(R.id.idPrenomUser)).getText().toString();
        Context context=getApplicationContext();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(Mdp);

        if (!Email.matches(emailPattern))
        {
            Toast.makeText(context,"Invalid email address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!matcher.matches()){
             Toast.makeText(context,"Le mot de passe est incorrect il doit avoir au moins -1 MAJUSCULE -1 minuscule -1 caractére spécial et 1 nombre", Toast.LENGTH_LONG).show();
            return;
        }
        if(Nom.isEmpty()){
            Toast.makeText(context,"Le nom est invalide",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Prenom.isEmpty()){
            Toast.makeText(context,"Le prénom est invalide",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Phone.isEmpty()){
            Toast.makeText(context,"Le téléphone est invalide",Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirmation d'inscription");
        builder.setMessage("Vérifies et confirmes les informations");
        builder.setPositiveButton("Confirmation",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressDialog progressDialog= new ProgressDialog(Register.this);
                        progressDialog.setTitle("Inscription en cours");
                        progressDialog.setMessage("Loading...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMax( 100 );
                        progressDialog.show();
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
                                params.put("Nom",Nom);
                                params.put("Prenom",Prenom);
                                params.put("Email",Email);
                                params.put("Phone",Phone);
                                params.put("Mdp",Mdp);
                                return params;
                            }
                        };

                        requestQueue.add(RequestConnect);

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }


}
