/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

/**
 *
 * @author Swann
 */
public class Test {
    
    public static void main(String[] args) {
        ModeleApplication mod = new ModeleApplication();
        Universite universite = new Universite("UHP", mod);
        mod.connecter("log1", "mdp");
        Personnel p = new Personnel("mdp", "toi", "moi", universite);
        p.ajouterFormation("je sais pas quoi", "info", "master");
        p.ajouterEtudiant("Thierry", "la", "Fronde");
        mod.deconnecter();
    }
    
}
