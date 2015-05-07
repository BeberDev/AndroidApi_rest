package com.example.beber.autoemprunteur;

/**
 * Created by Beber on 06/05/2015.
 */
public class Client {



    private String prenom;
    private String nom;
    private int idemprunteur;

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setIdemprunteur(int idemprunteur) {this.idemprunteur = idemprunteur;}

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public int getIdemprunteur() { return idemprunteur; }

}
