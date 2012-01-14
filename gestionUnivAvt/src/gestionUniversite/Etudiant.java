package gestionUniversite;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Etudiant extends Personne implements ComposanteFac {
    private Universite universite;
    
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
    public double getMoyenne() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNbEtudiants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
