/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversiteOld;

/**
 *
 * @author Swann
 */
public class Salle {
    private String nom;
    private int capacite;

    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
}
