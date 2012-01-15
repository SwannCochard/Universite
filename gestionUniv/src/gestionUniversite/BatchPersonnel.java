/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Swann
 */
public class BatchPersonnel {
    private Scanner scan;
    private ModeleApplication modeleApplication;
    private Universite universite;

    public BatchPersonnel(Scanner scan, ModeleApplication modeleApplication, Universite universite) {
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
        
            System.out.println("=======================================================");
            System.out.println("Bienvenue dans votre espace personnel. Vous pouvez :");
            System.out.println("1. Ajouter une personne");
            System.out.println("2. Ajouter une formation et les modules correspondants");
            System.out.println("3. Modifier une formation");
            System.out.println("4. Modifier un module");
            System.out.println("5. Mettre en place une formation");
            System.out.println("6. Inscrire un étudiant à une formation");
            System.out.println("7. Calculer les moyennes");
            System.out.println("9. Vous deconnecter");
            System.out.println("=======================================================");
            System.out.println("Quel est votre choix ? (tapez le chiffre correspondant)");
            
            int choix = scan.nextInt();
            
            scan.nextLine();
            switch(choix){
                    case 1 : 
                        afficherAjouterPersonne();
                        break;
                    case 2 : 
                        afficherAjouterFormation();
                        break;
                    case 3 :
                        afficherModifierFormation();
                        break;
                    case 4 : 
                        afficherModifierModule();
                        break;
                    case 5 :
                        afficherMettreEnPlaceFormation();
                        break;
                    case 6 :
                        afficherInscrireEtudiant();
                        break;
                    case 7 :
                        afficherCalculerMoyennes();
                        break;     
                    case 9 :
                        afficherSeDeconnecter();
                        break;
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

    private void afficherAjouterPersonne() {
        try {
        System.out.println("Ajouter une personne : ");
        System.out.print("nom : ");
        String nom = scan.nextLine();
        while(nom.isEmpty()) {
            System.out.println("Veuillez taper un nom valide.");
            System.out.print("nom : ");
            nom = scan.nextLine();
        }
        System.out.print("prenom : ");
        
        String prenom = scan.nextLine();
        while(prenom.isEmpty()) {
            System.out.println("Veuillez taper un prenom valide.");
            System.out.print("prenom : ");
            prenom = scan.nextLine();
        }
        System.out.print("mot de passe : ");
        String mdp = scan.nextLine();
        while(mdp.isEmpty()) {
            System.out.println("Veuillez taper un mot de passe valide.");
            System.out.print("mdp : ");
            mdp = scan.nextLine();
        }
        int type = 0;
        System.out.print("type ? ( 1 : etudiant, 2 : professeur, 3 : personnel)");
        type = scan.nextInt();
//        while (type != 1 || type !=2 || type !=3) {
//            System.out.println("Type invalide."+type);
//            System.out.print("type ? ( 1 : etudiant, 2 : professeur, 3 : personnel)");
//            type = scan.nextInt();
//            scan.next();
//        }
        String typePersonne  ="";
        switch(type){
            case 1 :
                typePersonne = "etudiant";
                break;
            case 2 :
                typePersonne = "professeur";
                break;
            case 3 : 
                typePersonne = "personnel";
                break;
            default :
                System.out.println("Ceci n'est pas une entrée valide. Veuillez recommencer");
                this.afficherAjouterPersonne();
                break;
        }
        scan.nextLine();
        System.out.println("Type : "+typePersonne);
        if (typePersonne.equals("etudiant")) {
           ((Personnel) this.modeleApplication.getCurrent()).ajouterEtudiant(nom, prenom, mdp);
        }
        if (typePersonne.equals("professeur")) {
           ((Personnel) this.modeleApplication.getCurrent()).ajouterProfesseur(nom, prenom, mdp);
        }
        if (typePersonne.equals("personnel")) {
           ((Personnel) this.modeleApplication.getCurrent()).ajouterPersonnel(nom, prenom, mdp);
        }
        System.out.println("La personne a bien été ajoutée");
        this.afficherMenuPersonnel();
        } catch(InputMismatchException e) {
            System.out.println("Ceci n'est pas un choix correct.");
            scan.next();
            this.afficherAjouterPersonne();
        }
    }

    private void afficherAjouterFormation() {
        System.out.println("Ajouter une formation");
        System.out.println("nom complet :");
        String nom = scan.nextLine();
        while (nom.isEmpty()) {
            System.out.println("Veuillez entrer un nom correct.");
            nom = scan.nextLine();
        }
        ((Personnel) this.modeleApplication.getCurrent()).ajouterFormation(nom);
        //this.modeleApplication.ajouterFormation(nom);
        System.out.println("Formation ajoutée.");
        this.afficherAjouterModule();
        //this.afficherMenuPersonnel();
    }

    private void afficherModifierFormation() {
        System.out.println("Code de la formation à modifier ?");
        this.modeleApplication.afficherLesFormations();
        String code = scan.nextLine();
        while(code.isEmpty()) {
                System.out.println("Veuillez entrer un code de formation correct.");
                System.out.println("Code de la formation : ");
                code = scan.nextLine();
        }
        System.out.println("Nouvean nom de la formation : ");
        String nom = scan.nextLine();
        while(nom.isEmpty()) {
                System.out.println("Veuillez entrer un nom de formation correct.");
                System.out.println("Nom de la formation : ");
                nom = scan.nextLine();
        }
        boolean res = this.modeleApplication.modifierFormation(code, nom);
        while (!res) {
//            System.out.println("Formation inconnue.");
//            System.out.println("Code de la formation à modifier ?");
//            code = scan.nextLine();
//            System.out.println("Nouvean nom de la formation : ");
//            nom = scan.nextLine();
//            res = this.modeleApplication.modifierFormation(code, nom);
            this.afficherModifierFormation();
        }
        System.out.println("Modification enregistrée.");
        this.afficherMenuPersonnel();
    }

    private void afficherModifierModule() {
        System.out.println("Modifier un module.");
        this.modeleApplication.afficherLesModules();
        System.out.println("Code du module à modifier : ");
        String codeModule = scan.nextLine();
        while(codeModule.isEmpty()) {
                System.out.println("Veuillez entrer un code de module correct.");
                System.out.println("Code du module à modifier : ");
                codeModule = scan.nextLine();
        }
        System.out.println("Nouveau nom du module : ");
        String nomModule = scan.nextLine();
        while(nomModule.isEmpty()) {
                System.out.println("Veuillez entrer un nom de module correct.");
                System.out.println("Nouveau nom du module : ");
                nomModule = scan.nextLine();
            }
        this.modeleApplication.afficherLesProfesseurs();
        System.out.println("Login du nouveau professeur responsable : ");
        String codeProfesseur = scan.nextLine();
        while(codeProfesseur.isEmpty()) {
                System.out.println("Veuillez entrer un login de responsable correct.");
                System.out.println("Login du professeur responsable : ");
                codeProfesseur = scan.nextLine();
            }
        boolean modifOk = this.modeleApplication.modifierModule(codeModule, codeProfesseur, nomModule);
        while(!modifOk) {
            this.afficherModifierModule();
//            System.out.println("Veuillez recommencer.");
//            System.out.println("Code du module à modifier : ");
//            codeModule = scan.nextLine();
//            System.out.println("Nouveau nom du module : ");
//            nomModule = scan.nextLine();
//            System.out.println("Login du nouveau professeur responsable : ");
//            codeProfesseur = scan.nextLine();
//            modifOk = this.modeleApplication.modifierModule(codeModule, codeProfesseur, nomModule);
        }
        this.afficherMenuPersonnel();
    }

    private void afficherMettreEnPlaceFormation() {
        System.out.println("Désolé je vois pas à quoi ça correspond...");
        this.afficherMenuPersonnel();
    }

    private void afficherCalculerMoyennes() {
        System.out.println("Celle là c'est pour toi Max ! :) ");
        this.afficherMenuPersonnel();
    }

    private void afficherSeDeconnecter() {
        this.modeleApplication.commit();
        System.out.println("Modifications enregistrées.");
        this.modeleApplication.deconnecter();
        System.out.println("Vous êtes déconnecté. ");
        this.afficherMenuPrincipal();  
    }

    private void afficherChoixIncorrect() {
        System.out.println("Le choix que vous avez fait ne correpond pas à une option valide. Veuillez recommencer.");
        this.afficherMenuPersonnel();
    }

    private void afficherAjouterModule() {
        boolean continuer = true;
        while(continuer) {
            System.out.println("Rattacher un module à cette formation. ");
            System.out.println("Nom complet du module : ");
            String nomModule = scan.nextLine();
            while(nomModule.isEmpty()) {
                System.out.println("Vueillez entrer un nom correct.");
                System.out.println("Nom complet du module : ");
                nomModule = scan.nextLine();
            }
            this.modeleApplication.afficherLesProfesseurs();
            System.out.println("Login du professeur responsable : ");
            String loginResponsable = scan.nextLine();
            while(loginResponsable.isEmpty()) {
                System.out.println("Veuillez entrer un login de résponsable correct.");
                System.out.println("Login du professeur responsable : ");
                loginResponsable = scan.nextLine();
            }
            boolean profTrouve = ((Personnel) this.modeleApplication.getCurrent()).ajouterModule(nomModule, loginResponsable);
            while(!profTrouve) {
                System.out.println("Le professeur n'existe pas. Veuillez ");
                System.out.println("Login du professeur responsable : ");
                loginResponsable = scan.nextLine();
                profTrouve = ((Personnel) this.modeleApplication.getCurrent()).ajouterModule(nomModule, loginResponsable);
            }
            System.out.println("Continuer à ajouter des modules ? (O : oui, N : non)");
            String dmdContinue = scan.nextLine();
            continuer = dmdContinue.equals("O");
        }
        this.afficherMenuPersonnel();
    }

    private void afficherCommiter() {
        System.out.println("Enregistrement en cours...");
        this.modeleApplication.commit();
        System.out.println("Modifications enregistrées.");
        this.afficherMenuPersonnel();
    }

    private void afficherInscrireEtudiant() {
        System.out.println("Inscrire un étudiant à une formation");
        this.modeleApplication.afficherLesEtudiants();
        System.out.println("Login de l'étudiant : ");
        String login = scan.nextLine();
        while(login.isEmpty()) {
                System.out.println("Veuillez entrer un login correct.");
                System.out.println("Login de l'étudiant : ");
                login = scan.nextLine();
            }
        this.modeleApplication.afficherLesFormations();
        System.out.println("Code de la formation : ");
        String code = scan.nextLine();
        while(code.isEmpty()) {
                System.out.println("Veuillez entrer un code de formation correct.");
                System.out.println("Code de la formation : ");
                code = scan.nextLine();
            }
        boolean res = this.modeleApplication.inscrireEtudiant(login, code);
        while(!res) {
            this.afficherInscrireEtudiant();
//            System.out.println("Veuillez recommencer.");
//            System.out.println("Login de l'étudiant : ");
//            login = scan.nextLine();
//            System.out.println("Code de la formation : ");
//            code = scan.nextLine();
//            res = this.modeleApplication.inscrireEtudiant(login, code);
        }
        System.out.println("Etudiant inscrit ! ");
        this.afficherMenuPersonnel();
    }
}
