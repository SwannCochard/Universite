/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author gaelvarlet
 */
public class BatchEtudiant {
    private Scanner scan;
    private ModeleApplication modeleApplication;
    private Universite universite;
    
    public BatchEtudiant(Scanner scan, ModeleApplication modeleApplication, Universite universite) {
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
            System.out.println("1. Afficher Emploi du temps");
            System.out.println("2. Afficher Moyenne");
            System.out.println("8. Quitter");
            System.out.println(" =======================================================");
            System.out.println("Quel est votre choix ? (tapez le chiffre correspondant)");
            
            int choix = scan.nextInt();
            
            scan.nextLine();
            switch(choix){
                    case 1 : 
                        afficherEDT();
                        break;
                    case 2 :
                        consulterNotes();
                        break;
                    case 8 : 
                        this.modeleApplication.commit();
                        System.exit(0);
                    default : 
                        afficherChoixIncorrect();
                        break;
            }
            afficherMenuPrincipal();
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
    
    private void afficherEDT() {
        Date dateDebut = null;
        Date dateFin = null;
        
        System.out.println("Entrez la date de début d'affichage de l'emploi du temps");
        dateDebut = this.calculerDate();
        
        System.out.println("Entrez la date de fin d'affichage de l'emploi du temps");
        dateFin = this.calculerDate();
        
        
        ArrayList<Seance> seances = this.modeleApplication.consulterEDTEtudiant(dateDebut, dateFin, (Etudiant) this.modeleApplication.getCurrent());
        
        System.out.println("\n==============================");
        
        String dayOfWeek = "";
        if(seances != null & seances.size() > 0){
            for(Seance s : seances){
                SimpleDateFormat formatterJour = new SimpleDateFormat ("E");
                String jour = formatterJour.format(s.getDate());
                SimpleDateFormat formatterDate = new SimpleDateFormat ("dd.MM.yyyy");
                String date = formatterDate.format(s.getDate());
                if(!dayOfWeek.equals(jour)){
                    System.out.println("" + jour + " " + date);
                    dayOfWeek = jour;
                }
                
                System.out.println("\t" + s.getHeure() + "h00 " + (s.getHeure() + s.getDuree()) + "h00");
                System.out.print("\t  |" + universite.getModule(s.getCodeModule()).getNom() + "(" + s.getType() + ")" + ", Salle : ");

            }
        }else{
            System.out.println("Aucun cours à afficher");
        }
        
        System.out.println("==============================");
        
    }
    
    private Date calculerDate(){
        try{
            System.out.println("Format : yyyy-mm-dd");
            String dateALire;
            dateALire = scan.nextLine();
            
            return java.sql.Date.valueOf( dateALire );
        }catch(IllegalArgumentException e){
            System.out.println("Erreur dans le format de la date");
            return calculerDate();
        }
    }

    private void consulterNotes() {
        System.out.println("Votre moyenne : "+((Etudiant)this.modeleApplication.getCurrent()).getMoyenne());
    }
}
