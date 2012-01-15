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
            System.out.println("2. Afficher Emploi du temps");
            System.out.println("3. Ajouter une Séance");
            System.out.println("8. Quitter");
            System.out.println(" =======================================================");
            System.out.println("Quel est votre choix ? (tapez le chiffre correspondant)");
            
            int choix = scan.nextInt();
            
            scan.nextLine();
            switch(choix){
                    case 1 : 
                        afficherFixerCoefficient();
                        break;
                    case 2 : 
                        afficherEDT();
                        break;
                    case 3 : 
                        ajouterSeance();
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
    
    private void afficherEDT() {
        Date dateDebut = null;
        Date dateFin = null;
        
        System.out.println("Entrez la date de début d'affichage de l'emploi du temps");
        dateDebut = this.calculerDate();
        
        System.out.println("Entrez la date de fin d'affichage de l'emploi du temps");
        dateFin = this.calculerDate();
        
        
        ArrayList<Seance> seances = this.modeleApplication.consulterEDTProfesseur(dateDebut, dateFin, (Professeur) this.modeleApplication.getCurrent());
        
        System.out.println("\n==============================");
        
        String dayOfWeek = "";
        if(seances != null & seances.size() > 0){
            for(Seance s : seances){
                SimpleDateFormat formatterJour = new SimpleDateFormat ("E");
                String jour = formatterJour.format(dateDebut);
                SimpleDateFormat formatterDate = new SimpleDateFormat ("dd.MM.yyyy");
                String date = formatterDate.format(dateDebut);
                if(!dayOfWeek.equals(jour)){
                    System.out.println("" + jour + " " + date);
                    dayOfWeek = jour;
                }
                
                System.out.println("\t" + s.getHeure() + "h00 " + (s.getHeure() + s.getDuree()) + "h00");
                System.out.println("\t  |" + s.getCodeModule() + ", Salle : " + s.getSalle().getNom());

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
    
    private String calculerTypeCours(){
        String type;
        type = scan.next();
        if(type.equals("CM") || type.equals("TD") || type.equals("TP")){
            return type;
        }else{
            System.out.println("Erreur dans le type de la séance (CM, TD ou TD)");
            return calculerTypeCours();
        }
    }

    private void ajouterSeance() {
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
        System.out.println("Pour quel module souhaitez vous ajouter une séance ?");
        int indice = scan.nextInt();
        if (indice < 1 || indice > modules.size()+1) {
            System.out.println("Cet indice n'est pas bon. Retour au choix de module.");
            this.afficherFixerCoefficient();
        }
        
        // Type
        System.out.println("Type de la séance (CM, TD ou TD)");
        String typeCours = this.calculerTypeCours();
        
        // Date
        System.out.println("Date de la séance");
        Date dateCours = this.calculerDate();
        
        // Heure
        System.out.println("Heure de la séance");
        int heureCours = this.calculerHeureCours();
        
        //Duree
        System.out.println("Durée de la séance");
        int dureeCours = calculerDureeCours();
        
        Seance s = new Seance(typeCours, modules.get(indice-1).getCode(), dateCours, heureCours, dureeCours, null);
        
        // Salle
        Salle salleCours = calculerSalleCours(s, (Formation)modules.get(indice - 1).getSuccessor());
    }

    private int calculerHeureCours() {
        int heure;
        heure = Integer.parseInt(scan.next());
        if(heure >= 0 & heure <= 23){
            return heure;
        }else{
            System.out.println("Erreur un cours ne peut avoir lieu à cette heure");
            System.out.println("Heure de la séance");
            return calculerHeureCours();
        }
    }

    private int calculerDureeCours() {
        int duree;
        duree = Integer.parseInt(scan.next());
        if(duree >= 0 & duree <= 23){
            return duree;
        }else{
            System.out.println("Erreur : La durée entrée n'est pas correcte");
            System.out.println("Durée de la séance");
            return calculerDureeCours();
        }
    }

    private Salle calculerSalleCours(Seance seance, Formation formation) {
        String typeCours = seance.getType();
        Salle s = null;
        
        if(typeCours.equals("CM")){
            s = formation.getSalleCM();
        }
        if(typeCours.equals("TD")){
            s = formation.getSalleTD();
        }
        if(typeCours.equals("TP")){
            String reponse;
            
            System.out.println("La séance est un TP, voulez-vous réserver une salle dès maintenant ? (y or n)");
            reponse = scan.next();
            if(reponse.equals("y") || reponse.equals("n")){
                if(reponse.equals("y")){
                    this.modeleApplication.reserverSalle(seance, formation);
                }else{
                    System.out.println("Vous pourrez ajouter la séance plus tard");
                }
            }else{
                calculerSalleCours(seance, formation);
            }
        }
        
        return s;
    }    
}
