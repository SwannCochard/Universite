package gestionUniversite;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
            System.out.println("3. Ajouter une séance");
            System.out.println("4. Réserver/Modifier une salle");
            System.out.println("5. Saisir une/des notes");
            System.out.println("6. Afficher les notes de vos etudiants");
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
                    case 4 : 
                        reserverSalle();
                        break;
                    case 5 :
                        afficherSaisieNote();
                        break;
                    case 6 :
                        afficherNoteEtudiants();
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
        
        Collections.sort(seances);
        
        System.out.println("\n==============================");
        
        String dayOfWeek = "";
        if(seances != null & seances.size() > 0){
            for(Seance s : seances){
                SimpleDateFormat formatterJour = new SimpleDateFormat ("E");
                String jour = formatterJour.format(s.getDate());
                SimpleDateFormat formatterDate = new SimpleDateFormat ("dd.MM.yyyy");
                String date = formatterDate.format(s.getDate());
                if(!dayOfWeek.equals(date)){
                    System.out.println("" + jour + " " + date);
                    dayOfWeek = date;
                }
                
                System.out.println("\t" + s.getHeure() + "h00 " + (s.getHeure() + s.getDuree()) + "h00");
                System.out.print("\t  |" + universite.getModule(s.getCodeModule()).getNom() + "(" + s.getType() + ")" + ", Salle : ");
                if(s.getSalle() == null){
                    System.out.println("null");
                }else{
                    System.out.println(s.getSalle().getNom());
                }

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
            dateALire = scan.next();
            
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
            System.out.println("Erreur dans le type de la séance (CM, TD ou TP)");
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
        }
        
        // Type
        System.out.println("Type de la séance (CM, TD ou TP)");
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
        universite.addSeance(s);
        
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
            seance.setSalle(s);
        }
        if(typeCours.equals("TD")){
            s = formation.getSalleTD();
            seance.setSalle(s);
        }
        if(typeCours.equals("TP")){
            String reponse;
            
            System.out.println("La séance est un TP, voulez-vous réserver une salle dès maintenant ? (y or n)");
            reponse = scan.next();
            if(reponse.equals("y") || reponse.equals("n")){
                if(reponse.equals("y")){
                    boolean b = this.modeleApplication.reserverSalle(seance, formation);
                    if(!b) System.out.println("Erreur : Aucune salle n'est disponible pour cet horaire");
                }else{
                    System.out.println("Vous pourrez ajouter la séance plus tard");
                }
            }else{
                calculerSalleCours(seance, formation);
            }
        }        
        return s;
    }

    private void reserverSalle() {
        System.out.println("Liste de vos modules :");
        Professeur professeur = (Professeur) this.modeleApplication.getCurrent();
        ArrayList<Seance> seances = this.universite.getSeancesParProfesseur(professeur);
        
        if (seances.isEmpty()) {
            System.out.println("Vous n'avez aucune séances");
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BatchProfesseur.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.afficherMenuPersonnel();
        }
        
        for (int i = 0; i< seances.size(); i++) {
            System.out.println(i+1+"- "+seances.get(i).toString());
        }
        System.out.println("Pour quel module souhaitez vous fixer une séance ?");
        int indice = scan.nextInt();
        if (indice < 1 || indice > seances.size()+1) {
            System.out.println("Cet indice n'est pas bon. Retour au choix de module.");
        }
        
        Seance s = seances.get(indice - 1);
        Module m = universite.getModule(s.getCodeModule());
        
        this.modeleApplication.reserverSalle(s, (Formation)m.getSuccessor());
        
        System.out.println("Salle réservée : " + s.getSalle().getNom());
    }
    
    public void afficherSaisieNote() {
        System.out.println("Saisie de notes : ");
        System.out.println("Liste des modules dont vous etes responsable : ");
        Professeur professeur = (Professeur) this.modeleApplication.getCurrent();
        ArrayList<Module> modules = this.universite.getModulesParProfesseur(professeur);
        for (int i = 0; i< modules.size(); i++) {
            System.out.println(i+1+"- "+modules.get(i).getCode());
        }
        if (modules.size() == 0) {
            System.out.println("Vous n'êtes responsable d'aucun module ... ");
            this.afficherMenuPrincipal();
        }
        
        System.out.println("Pour quel module souhaitez vous fixer un coefficient ?");
        int indice = scan.nextInt();
        while ((indice <= 0) || (indice > modules.size())) {
            indice = scan.nextInt();
        }
        Module module = modules.get(indice-1);
        ArrayList<Etudiant> etudiants = ((Formation)module.getSuccessor()).getEtudiants();
        if (etudiants.size() == 0) {
            System.out.println("Il n'y a pas d'etudiants pour cette formation");
            this.afficherMenuPrincipal();
        }
            
        System.out.println("Pour quel etudiant souhaitez vous saisir les notes ?");
        for (int i = 0; i< etudiants.size(); i++) {
            System.out.println(i+1+"- "+etudiants.get(i).getLogin());
        }
        System.out.println("Saisissez son numero :");
        indice = scan.nextInt();
        while ((indice <= 0) || (indice > etudiants.size())) {
            indice = scan.nextInt();
        }
        Etudiant etudiant = etudiants.get(indice-1);
        System.out.println("Note de CM : ");
        double cm = scan.nextDouble();
        while ((cm<0)||(cm>20)) {
            System.out.println("Note non valide ... Veuillez recommencer.");
            cm = scan.nextDouble();
        }
        System.out.println("Note de TD : ");
        double td = scan.nextDouble();
        while ((td<0)||(td>20)) {
            System.out.println("Note non valide ... Veuillez recommencer.");
            td = scan.nextDouble();
        }
        System.out.println("Note de TP : ");
        double tp = scan.nextDouble();
        while ((tp<0)||(tp>20)) {
            System.out.println("Note non valide ... Veuillez recommencer.");
            tp = scan.nextDouble();
        }
        Resultat result = this.universite.getResult(etudiant, module);
        
        Boolean flag = false;
        if (result == null) {
            result = new Resultat(etudiant,module);
            flag  = true;
        }    
        result.setNoteCM(cm);
        result.setNoteTD(td);
        result.setNoteTP(tp);
        
        if (flag) {
            this.universite.getLesResultats().add(result);
        }      
        System.out.println("Notes enregistrées.");
        
        this.afficherMenuPrincipal();
    }

    public void afficherNoteEtudiants() {
        ArrayList<Module> modules = this.universite.getModulesParProfesseur((Professeur)this.modeleApplication.getCurrent());
        for (Module m : modules) {
            Formation f = (Formation) m.getSuccessor();
            for (Etudiant etudiant : f.getEtudiants()) {
                System.out.println(etudiant.getNom()+" "+etudiant.getPrenom()+" ("+etudiant.getLogin()+") : "+etudiant.getMoyenne()  );
            }
        }
    }
}
