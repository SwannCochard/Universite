/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Swann
 */
public class Batch {
    private ModeleApplication modeleApplication;
    private Scanner scan;
    private Universite universite;
    
    public Batch() {
        this.scan = new Scanner(System.in);
        this.modeleApplication = new ModeleApplication();
        this.universite = new Universite("UHP",this.modeleApplication);
        this.modeleApplication.setUniversite(universite);
        //Collections.sort(this.universite.getLesPersonnes());
    }
    
    public void afficherMenuPrincipal() {
        scan = scan.reset();
        System.out.println("Bienvenue dans votre université !");
        System.out.println("Veuillez vous connecter ");
        do {
            System.out.print("login : ");
            String login = scan.nextLine();
            System.out.print("mot de passe : ");
            String mdp = scan.nextLine();
            Boolean connect = this.modeleApplication.connecter(login, mdp);
            if (!connect) {
                System.out.println("erreur de login/mot de passe. Veuillez réessayer");
            }
        } while (!this.modeleApplication.isConnected());
        Personne user = this.modeleApplication.getCurrent();
        if(user instanceof Personnel) {
            BatchPersonnel igPersonnel = new BatchPersonnel(scan, modeleApplication, universite);
            igPersonnel.afficherMenuPrincipal();
        }
        if(user instanceof Etudiant) {
            BatchEtudiant igPersonnel = new BatchEtudiant(scan, modeleApplication, universite);
            igPersonnel.afficherMenuPrincipal();
        }
        if(user instanceof Professeur) { 
            BatchProfesseur igProfesseur = new BatchProfesseur(scan, modeleApplication, universite);
            igProfesseur.afficherMenuPrincipal();
        }
    }
}
