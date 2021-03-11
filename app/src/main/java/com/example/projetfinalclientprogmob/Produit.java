package com.example.projetfinalclientprogmob;

import android.graphics.Bitmap;
import android.provider.BaseColumns;

import java.sql.Blob;

public class Produit implements BaseColumns {


    private int IdProduit;
    private String NomProduit;
    private String Description;
    private double Prix;
    private Bitmap LienImage;


    public static final String TABLE_PRODUIT="produits";

    public Produit(String NomProduit,String Description,double Prix,Bitmap LienImage){

        this.NomProduit=NomProduit;
        this.Description=Description;
        this.Prix=Prix;
        this.LienImage=LienImage;
    }
    public Bitmap getLienImage() {
        return LienImage;
    }

    public void setLienImage(Bitmap lienImage) {
        this.LienImage = lienImage;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String nomProduit) {
        NomProduit = nomProduit;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        Prix = prix;
    }

    public int getIdProduit() {
        return IdProduit;
    }

    public void setIdProduit(int idProduit) {
        IdProduit = idProduit;
    }


}
