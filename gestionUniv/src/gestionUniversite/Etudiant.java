/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Swann
 */
public class Etudiant extends Personne {
    private Formation formation;
    public Etudiant(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Etudiant(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }
    
    public void consulterNotes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList<Seance> consulterEDT(Date dateDebut, Date dateFin) {
        return null;
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }   
}
