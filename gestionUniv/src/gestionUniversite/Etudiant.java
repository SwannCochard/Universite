/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.Date;

/**
 *
 * @author Swann
 */
public class Etudiant extends Personne {

    public Etudiant(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Etudiant(int identifiant, String login, String motDePasse, String nom, String prenom) {
        super(identifiant, login, motDePasse, nom, prenom);
    }
    
    public void consulterNotes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void consulterEDT(Date dateDebut, Date dateFin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
