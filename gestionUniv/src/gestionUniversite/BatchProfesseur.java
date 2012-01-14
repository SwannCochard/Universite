    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime
 */
public class BatchProfesseur {
    private Scanner scan;
    private ModeleApplication modeleApplication;
    private Universite universite;


    public BatchProfesseur(Scanner scan, ModeleApplication modeleApplication, Universite universite) {
        this.scan = scan;
        this.modeleApplication = modeleApplication;
        this.universite = universite;
    }
    
    public void afficherMenuPrincipal() {
        scan = scan.reset();
        afficherMenuPersonnel();
    }

    private void afficherMenuPersonnel() {
        try {
        
            System.out.println(" =======================================================");
            System.out.println("Bienvenue dans votre espace personnel. Vous pouvez :");
            System.out.println("1. Fixer un coefficient");
            System.out.println("8. Quitter");
            System.out.println(" =======================================================");
            System.out.println("Quel est votre choix ? (tapez le chiffre correspondant)");
            
            int choix = scan.nextInt();
            
            scan.nextLine();
            switch(choix){
                    case 1 : 
                        afficherFixerCoefficient();
                        break;
                    case 8 : 
                        return;
                    default : 
                        afficherChoixIncorrect();
                        break;
            }
        } catch(InputMismatchException e) {
            System.out.println("Ceci n'est pas un choix correct.");
            scan.next();
            this.afficherMenuPersonnel();
        }
        
    }

    private void afficherChoixIncorrect() {
        System.out.println("Le choix que vous avez fait ne correpond pas à une option valide. Veuillez recommencer.");
        this.afficherMenuPersonnel();
    }

    private void afficherFixerCoefficient() {
        System.out.println("Fixer un coefficient.");
        System.out.println("Liste de vos modules :");
        Professeur professeur = (Professeur) this.modeleApplication.getCurrent();
        ArrayList<Module> modules = this.universite.getModulesParProfesseur(professeur);
        
        if (modules.isEmpty()) {
            System.out.println("Vous n'avez aucun module");
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BatchProfesseur.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.afficherMenuPersonnel();
        }
        
        for (int i = 0; i< modules.size(); i++) {
            System.out.println(i+1+"- "+modules.get(i).getCode());
        }
        System.out.println("Pour quel module souhaitez vous fixer un coefficient ?");
        int indice = scan.nextInt();
        if (indice < 1 || indice > modules.size()+1) {
            System.out.println("Cet indice n'est pas bon. Retour au choix de module.");
            this.afficherFixerCoefficient();
        }
        System.out.println("Liste de vos modules : ");
        System.out.println("1- Coefficient du module");
        System.out.println("2- Coefficient du CM");
        System.out.println("3- Coefficient du TD");
        System.out.println("4- Coefficient du TP");
        System.out.println("Quel modules souhaitez vous modifier ?");
        // On recupere le type qu'on souhaite modifier
        int type = scan.nextInt();
        if (type < 1 || indice > 4) {
            System.out.println("Cet indice n'est pas bon. Retour au choix de module.");
            this.afficherFixerCoefficient();
        }
        // On recupere le coefficient a ajouter
        System.out.println("Quel est le coefficient ?");
        int coefficient = scan.nextInt();
        if (coefficient < 0) {
            System.out.println("Cet indice n'est pas bon. Retour au choix de module.");
            this.afficherFixerCoefficient();
        }
        Boolean result = this.modeleApplication.setCoefficient(modules.get(indice-1), coefficient, type);
        if (!result) {
            System.out.println("Une erreur est survenue. Retour au choix de module.");
            this.afficherFixerCoefficient();
        }
//        System.out.println("Coefficients du module : ");
//        System.out.println("Module : "+modules.get(indice-1).getCoefModule());
//        System.out.println("CM : "+modules.get(indice-1).getCoefCM());
//        System.out.println("TD : "+modules.get(indice-1).getCoefTD());
//        System.out.println("TP : "+modules.get(indice-1).getCoefTP());
        this.afficherMenuPersonnel();    
    }

    
}
