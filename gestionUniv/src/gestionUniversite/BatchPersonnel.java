/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.Scanner;

/**
 *
 * @author Swann
 */
public class BatchPersonnel {
    private Scanner scan;
    private ModeleApplication modeleApplication;
    private Universite universite;
    public BatchPersonnel() {
        this.scan = new Scanner(System.in);
        this.modeleApplication = new ModeleApplication();
        this.universite = new Universite("UHP",this.modeleApplication);
        this.modeleApplication.setUniversite(universite);
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
        afficherMenuPersonnel();
    }

    private void afficherMenuPersonnel() {
        scan = scan.reset();
        System.out.println(" =======================================================");
        System.out.println("Bienvenue dans votre espace personnel. Vous pouvez :");
        System.out.println("1. Ajouter une personne");
        System.out.println("2. Ajouter une formation et les modules correspondants");
        System.out.println("3. Modifier une formation");
        System.out.println("4. Mettre en place un module");
        System.out.println("5. Mettre en place une formation");
        System.out.println("6. Inscrire un étudiant à une formation");
        System.out.println("7. Calculer les moyennes");
        System.out.println("8. Enregistrer les modifications");
        System.out.println("9. Vous deconnecter");
        System.out.println(" =======================================================");
        System.out.println("Quel est votre choix ? (tapez le chiffre correspondant)");
        int choix = scan.nextInt();
        scan.nextLine();
        switch(choix){
                case 1 : 
                    afficherAjouterPeronne();
                    break;
                case 2 : 
                    afficherAjouterFormation();
                    break;
                case 3 :
                    afficherModifierFormation();
                    break;
                case 4 : 
                    afficherMettreEnPlaceModule();
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
                case 8 :
                    afficherCommiter();
                    break;
                case 9 :
                    afficherSeDeconnecter();
                    break;
                default : 
                    afficherChoixIncorrect();
                    break;
        }
        
    }

    private void afficherAjouterPeronne() {
        
        System.out.println("Ajouter une personne : ");
        System.out.print("nom : ");
        String nom = scan.nextLine();
        System.out.print("prenom : ");
        String prenom = scan.nextLine();
        System.out.print("mot de passe : ");
        String mdp = scan.nextLine();
        //System.out.print("type (etudiant, professeur, personnel)");
        int type = 0;
        System.out.print("type ? ( 1 : etudiant, 2 : professeur, 3 : personnel)");
        type = scan.nextInt();
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
                this.afficherChoixIncorrect();
                this.afficherAjouterPeronne();
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
    }

    private void afficherAjouterFormation() {
        System.out.println("Ajouter une formation");
        System.out.println("nom complet :");
        String nom = scan.nextLine();
        this.modeleApplication.ajouterFormation(nom);
        System.out.println("Formation ajoutée.");
        this.afficherAjouterModule();
    }

    private void afficherModifierFormation() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void afficherMettreEnPlaceModule() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void afficherMettreEnPlaceFormation() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void afficherCalculerMoyennes() {
        System.out.println("Celle là c'est pour toi Max ! :) ");
        this.afficherMenuPersonnel();
    }

    private void afficherSeDeconnecter() {
        this.modeleApplication.deconnecter();
        System.out.println("Vous êtes déconnecté.");
        this.afficherMenuPrincipal();
    }

    private void afficherChoixIncorrect() {
        System.out.println("Le choix que vous avez fait ne correpond pas à une option valide. Veuillez recommencer.");
    }

    private void afficherAjouterModule() {
        boolean continuer = true;
        while(continuer) {
            System.out.println("Rattacher un module à cette formation. ");
            System.out.println("Nom complet du module : ");
            String nomModule = scan.nextLine();
            System.out.println("Login du professeur responsable : ");
            String loginResponsable = scan.nextLine();
            ((Personnel) this.modeleApplication.getCurrent()).ajouterModule(nomModule, loginResponsable);
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
        System.out.println("Login de l'étudiant : ");
        String login = scan.nextLine();
        System.out.println("Code de la formation : ");
        String code = scan.nextLine();
        this.modeleApplication.inscrireEtudiant(login, code);
        System.out.println("Etudiant inscrit ! ");
        this.afficherMenuPersonnel();
    }
}
