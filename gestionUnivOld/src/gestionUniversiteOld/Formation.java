/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversiteOld;

import java.util.ArrayList;

/**
 *
 * @author Swann
 */
public class Formation {
    private ArrayList<Module> modules;
    private String nom, code;

    public Formation(String nom, String code) {
        this.nom = nom;
        this.code = code;
        modules = new ArrayList<Module>();
    }

    public void ajouterModule(Module m) {
        modules.add(m);
    }
    
    public void calculerMoyenne() {
        
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Module> getLesModules() {
        return this.modules;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLesModules(ArrayList<Module> modules) {
        this.modules = modules;
    }
    
    
    
    
}
