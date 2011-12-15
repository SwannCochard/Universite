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
public class Professeur extends Personne {
    private Module estResponsable;

    public Professeur(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }


    public Professeur(int identifiant, String login, String motDePasse, String nom, String prenom) {
        super(identifiant, login, motDePasse, nom, prenom);
    }

    public void saisirNote() {
        
    }
    
    public void setCoefs(int coefCM, int coefTD, int coefTP, int coefModule) {
        estResponsable.setCoefCM(coefCM);
        estResponsable.setCoefModule(coefModule);
        estResponsable.setCoefTD(coefTD);
        estResponsable.setCoefTP(coefTP);      
    }
    
    public void consulterNotes() {
        
    }
    
    public void fixerEDT() {
        
    }
    
    public void modifierEDT() {
        
    }
    
    public void reserverSalleMachine() {
        
    }
    
    public void consulterEDT(Date dateDebut, Date dateFin) {
        
    }
    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
