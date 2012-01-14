package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Universite extends GroupeEtudiants {
    private String nom;
    private ArrayList<Formation> lesFormations;
    private ArrayList<Personnel> lesPersonnels;
    private ArrayList<Professeur> lesProfesseurs;
    private ArrayList<Salle> lesSalles;
    private ArrayList<Seance> lesSeances;
    private ModeleApplication modeleApplication;  
    
    public Universite(String nom, ModeleApplication modeleApplication){
        this.nom = nom;
        this.modeleApplication = modeleApplication;
        this.lesFormations = new ArrayList<Formation>();
        this.lesPersonnels = new ArrayList<Personnel>();
        this.lesProfesseurs = new ArrayList<Professeur>();
        this.lesSalles = new ArrayList<Salle>();
        this.lesSeances = new ArrayList<Seance>();
    }

    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;
    }

    public ArrayList<Personnel> getLesPersonnels() {
        return lesPersonnels;
    }

    public void setLesPersonnels(ArrayList<Personnel> lesPersonnels) {
        this.lesPersonnels = lesPersonnels;
    }

    public ArrayList<Professeur> getLesProfesseurs() {
        return lesProfesseurs;
    }

    public void setLesProfesseurs(ArrayList<Professeur> lesProfesseurs) {
        this.lesProfesseurs = lesProfesseurs;
    }

    public ArrayList<Salle> getLesSalles() {
        return lesSalles;
    }

    public void setLesSalles(ArrayList<Salle> lesSalles) {
        this.lesSalles = lesSalles;
    }

    public ArrayList<Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(ArrayList<Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public ArrayList<Etudiant> getEtudiants(){
        ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
        for(Formation f : this.lesFormations){
            etudiants.addAll(f.getEtudiants());
        }
        
        return etudiants;
    }
    
    public ArrayList<Etudiant> getEtudiants(String codeFormation){
        if(this.getFormation(codeFormation) == null){
            return null;
        }
        return this.getFormation(codeFormation).getEtudiants();
    } 
    
    public Formation getFormation(String code){
        Formation f = null;
        int i = 0;
        while(i < this.lesFormations.size() && f == null){
            if(this.lesFormations.get(i).getCode().equals(code))
                f = this.lesFormations.get(i);
        }
        
        return f;
    }
}
