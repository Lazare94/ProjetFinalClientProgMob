package com.example.projetfinalclientprogmob;

import android.provider.BaseColumns;

public class Produit implements BaseColumns {


    private int IdProduit;
    private String NomProduit;
    private String Description;
    private double Prix;
    private String LienImage;
    public static final String TABLE_PRODUIT="produits";

    public Produit(String NomProduit,String Description,double Prix,String LienImage){

        this.NomProduit=NomProduit;
        this.Description=Description;
        this.Prix=Prix;
        this.LienImage=LienImage;
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

    public String getLienImage() {
        return LienImage;
    }

    public void setLienImage(String lienImage) {
        LienImage = lienImage;
    }
}
