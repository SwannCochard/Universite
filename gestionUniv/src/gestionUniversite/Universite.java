/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author Swann
 */
public class Universite {
    private ArrayList<Personne> lesPersonnes;
    private String nom;
    private ModeleApplication ma;

    public Universite(String nom, ModeleApplication ma) {
        this.nom = nom;
        this.ma = ma;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void ajouterPersonne(Personne p) {
        lesPersonnes.add(p);
    }

    void ajouterPersonne(String nom, String prenom, String mdp, String type) {
        ma.ajouterPersonne(nom, prenom, mdp, type);
    }

    void ajouterFormation(String nom, String code) {
        ma.ajouterFormation(nom, code);
    }
    
    
}
