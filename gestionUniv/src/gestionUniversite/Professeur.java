/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Swann
 */
public class Professeur extends Personne {
    private ArrayList<Module> estResponsable = new ArrayList<Module>();

    public Professeur(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }


    public Professeur(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(0, login, motDePasse, nom, prenom, universite);
    }

    public void saisirNote() {
        
    }
    
    public void setCoefs(Module module, int coefCM, int coefTD, int coefTP, int coefModule) {
        estResponsable.get(estResponsable.indexOf(module)).setCoefCM(coefCM);
        estResponsable.get(estResponsable.indexOf(module)).setCoefModule(coefModule);
        estResponsable.get(estResponsable.indexOf(module)).setCoefTD(coefTD);
        estResponsable.get(estResponsable.indexOf(module)).setCoefTP(coefTP);      
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
    
    public void ajouterModule(Module module) {
        this.estResponsable.add(module);
    }
    
    
}
