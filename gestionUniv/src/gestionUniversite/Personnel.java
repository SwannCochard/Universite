/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

/**
 *
 * @author Swann
 */
public class Personnel extends Personne {

    public Personnel(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Personnel(int identifiant, String login, String motDePasse, String nom, String prenom) {
        super(identifiant, login, motDePasse, nom, prenom);
    }
    
    public void ajouterEtudiant(String nom, String prenom, String mdp) {
        universite.ajouterPersonne(nom, prenom, mdp, "etudiant");
    }
    
    public void ajouterProfesseur(String nom, String prenom, String mdp) {
        universite.ajouterPersonne(nom, prenom, mdp, "professeur");
    }
    
    public void ajouterPersonnel(String nom, String prenom, String mdp) {
        universite.ajouterPersonne(nom, prenom, mdp, "personnel");
    }

    public void inscrireEtudiant() {
        
        
    }
    
    public void ajouterModule() {
        
    }
    
    public void ajouterFormation(String nom, String filliere, String grade) { 
        String code = filliere+"-"+grade;
        universite.ajouterFormation(nom, code);
    }
    
    public void mettreEnPlaceFormation() {
        
    }
    
    public void mettreEnPlaceModule() {
        
    }
    
    public void calculerMoyennes() {
        calculerMoyennesEtudiants();
        calculerMoyennesModules();
        calculerMoyennesFormations();
    }
    
    
    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void calculerMoyennesEtudiants() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void calculerMoyennesModules() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void calculerMoyennesFormations() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
