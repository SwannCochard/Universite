package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Formation extends GroupeEtudiants {
    private ArrayList<Module> modules;
    private ArrayList<Etudiant> etudiants;
    private String nom, code;
    private Salle salleCM, salleTD;

    public Formation(String nom, String code, Salle salleCM, Salle salleTD) {
        this.nom = nom;
        this.code = code;
        this.salleCM = salleCM;
        this.salleTD = salleTD;
        this.modules = new ArrayList<Module>();
        this.etudiants = new ArrayList<Etudiant>();
    }
    
    public void addModule(Module module){
        this.modules.add(module);
    }
    
    public void addEtudiant(Etudiant etudiant){
        this.etudiants.add(etudiant);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Salle getSalleCM() {
        return salleCM;
    }

    public void setSalleCM(Salle salleCM) {
        this.salleCM = salleCM;
    }

    public Salle getSalleTD() {
        return salleTD;
    }

    public void setSalleTD(Salle salleTD) {
        this.salleTD = salleTD;
    }
    
}