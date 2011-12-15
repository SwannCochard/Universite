/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author Swann
 */
public class Formation {
    private ArrayList<Module> modules;
    private String nom, code;

    public Formation(String nom) {
        this.nom = nom;
    }

    public void ajouterModule(Module m) {
        modules.add(m);
    }
    
    public void calculerMoyenne() {
        
    }
    
    
}
