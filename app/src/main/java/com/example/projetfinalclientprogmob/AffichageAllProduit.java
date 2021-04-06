package com.example.projetfinalclientprogmob;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.projetfinalclientprogmob.R.drawable.carrot;

public class AffichageAllProduit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_affichage_all_produit );


        final ListView list = findViewById( R.id.idListViewAll );
        DatabaseHandler db = new DatabaseHandler( this );
        String var = "com.my.app:drawable/";
        List ListProduit;
        ListProduit = db.getAllProduit();
        String url = "http://192.168.5.139/QuiGoAuBled/Login.php";
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.start();
        ProgressDialog progressDialog = new ProgressDialog( AffichageAllProduit.this );
        progressDialog.setTitle( "vérification des informations" );
        progressDialog.setMessage( "Loading..." );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setMax( 100 );
        progressDialog.show();

        StringRequest RequestConnect = new StringRequest( Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        } );

        requestQueue.add( RequestConnect );

        ArrayList<HashMap<String, Object>> liste = new ArrayList<>();
        for (int i = 0; i <ListProduit.size(); i++) {
           Produit  produit =( Produit)ListProduit.get(i);
            HashMap<String, Object> map = new HashMap<>();
            map.put("NomProduit", produit.getNomProduit());
            map.put("Description",produit.getDescription());
            map.put("Prix", produit.getPrix()+" $");
            map.put("LienImage",produit.getLienImage());
            map.put("id",produit.getIdProduit());
            liste.add(map);
        }
        String[] from = {"NomProduit","Description","Prix","LienImage","id"};
        int to[] = {R.id.idNomProduit,R.id.idDescription,R.id.idPrix,R.id.idimageBIT,R.id.idsuppression};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), liste,  R.layout.itemlist , from,to);
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        list.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context= getApplicationContext();
                TextView IdProduit= (TextView) findViewById(R.id.idsuppression);
                Intent intent = new Intent(context,Confirmation.class);
                String var=IdProduit.getText().toString();
                intent.putExtra("id",var);
                startActivity(intent);
            }
        });
    }

   static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
        }
    }
    public void AjouteProduit(View view) {
        Context context= getApplicationContext();
        Intent intent = new Intent(context,AjouterProduit.class);
        startActivity(intent);
    }


}