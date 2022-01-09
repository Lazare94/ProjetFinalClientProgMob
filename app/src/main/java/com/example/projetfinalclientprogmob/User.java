package com.example.projetfinalclientprogmob;

public class User {

    private String Nom;
    private String Prenom;
    private String Phone;
    private String Email;
    private String Mdp;

    public User(String Email, String phone,String Mdp){
        this.Email = Email;
        this.Phone = phone;
        this.Mdp=Mdp;
    }
    public User(String Nom,String Prenom, String phone, String Email,String Mdp){
        this.Nom=Nom;
        this.Prenom=Prenom;
        this.Phone = phone;
        this.Email = Email;
        this.Mdp=Mdp;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String mdp) {
        Mdp = mdp;
    }

    public static String StringUser(User user){
        String Chaine;
        return Chaine="Nom : "+ user.getNom()+ System.getProperty ("line.separator")
                +"Prénom : "+ user.getPrenom()+ System.getProperty ("line.separator")
                +"Téléphone : "+ user.getPhone()+ System.getProperty ("line.separator")
                +"Email: "+ user.getEmail()+ System.getProperty ("line.separator")
                +"Mot de passe : "+ user.getMdp()+ System.getProperty ("line.separator");
    }

}
