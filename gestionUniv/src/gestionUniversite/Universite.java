package gestionUniversite;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author gaelvarlet
 */
public class Universite extends GroupeEtudiants {
    private String nom;
    private ArrayList<Formation> lesFormations;
    private ArrayList<Personnel> lesPersonnels;
    private ArrayList<Professeur> lesProfesseurs;
    private ArrayList<Salle> lesSalles;
    private ArrayList<Seance> lesSeances;
    private ArrayList<Personne> lesPersonnes;
    private ArrayList<Etudiant> lesEtudiants;
    
    private ArrayList<Resultat> lesResultats;

    private ModeleApplication modeleApplication;  
    
    public Universite(String nom, ModeleApplication modeleApplication){
        this.nom = nom;
        this.modeleApplication = modeleApplication;
        this.lesFormations = new ArrayList<Formation>();
        this.lesPersonnels = new ArrayList<Personnel>();
        this.lesProfesseurs = new ArrayList<Professeur>();
        this.lesSalles = new ArrayList<Salle>();
        this.lesSeances = new ArrayList<Seance>();
        this.lesEtudiants = new ArrayList<Etudiant>();
        this.lesPersonnes = new ArrayList<Personne>();
        this.lesResultats = new ArrayList<Resultat>();
    }

    public ArrayList<Personne> getLesPersonnes() {
        return lesPersonnes;
    }

    public void setLesPersonnes(ArrayList<Personne> lesPersonnes) {
        this.lesPersonnes = lesPersonnes;
    }
    
    public String dernierDuNom(String nom) {
        int taille = Math.min(4,nom.length());
        nom = nom.substring(0, taille);
        String nomRes ="";
        for(Personne p : lesPersonnes) {
            String nomCourant = p.getLogin().substring(0, taille);
            System.out.println("nom : "+nom+", nomcourant : "+nomCourant);
            if (nomCourant.equals(nom)) {
                nomRes = p.getLogin();
                System.out.println("NomRes : "+nomRes);
            }
        }
        return nomRes;
    }
    
    public String loginSuivant(String nomPersonne) {
        String res = "";
        nomPersonne  = nomPersonne.toLowerCase();
        String dernierLogin = this.dernierDuNom(nomPersonne);
        if ("".equals(dernierLogin)) {
            int taille = Math.min(4,nomPersonne.length());
            String partieNom = nomPersonne.substring(0,taille);
            res = partieNom+"0001";
        }
        else {
            String partieNom = dernierLogin.substring(0,4);
            int partieNb = Integer.parseInt(dernierLogin.substring(4));
            partieNb++;
            String nb = ""+partieNb;
            while(nb.length() != 4) {
                nb = "0"+nb;
            }
            res = partieNom+nb;
        }
        return res;
    }
    

    public ArrayList<Etudiant> getLesEtudiants() {
        return lesEtudiants;
    }

    public void setLesEtudiants(ArrayList<Etudiant> lesEtudiants) {
        this.lesEtudiants = lesEtudiants;
        for(Etudiant etudiant : lesEtudiants) {
            lesPersonnes.add(etudiant);
        }
    }

    public ArrayList<Formation> getLesFormations() {
        return lesFormations;
    }

    public void setLesFormations(ArrayList<Formation> lesFormations) {
        this.lesFormations = lesFormations;
        
    }

    public ArrayList<Personnel> getLesPersonnels() {
        return lesPersonnels;
    }

    public void setLesPersonnels(ArrayList<Personnel> lesPersonnels) {
        this.lesPersonnels = lesPersonnels;
        for(Personnel personnel : lesPersonnels) {
            lesPersonnes.add(personnel);
        }
    }

    public ArrayList<Professeur> getLesProfesseurs() {
        return lesProfesseurs;
    }

    public void setLesProfesseurs(ArrayList<Professeur> lesProfesseurs) {
        this.lesProfesseurs = lesProfesseurs;
        for(Professeur professeur : lesProfesseurs) {
            lesPersonnes.add(professeur);
        }
    }

    public ArrayList<Salle> getLesSalles() {
        return lesSalles;
    }

    public void setLesSalles(ArrayList<Salle> lesSalles) {
        this.lesSalles = lesSalles;
    }

    public ArrayList<Seance> getLesSeances() {
        return lesSeances;
    }

    public void setLesSeances(ArrayList<Seance> lesSeances) {
        this.lesSeances = lesSeances;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public ArrayList<Etudiant> getEtudiants(){
        ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
        for(Formation f : this.lesFormations){
            etudiants.addAll(f.getEtudiants());
        }
        
        return etudiants;
    }
    
    public ArrayList<Etudiant> getEtudiants(String codeFormation){
        if(this.getFormation(codeFormation) == null){
            return null;
        }
        return this.getFormation(codeFormation).getEtudiants();
    } 
    
    public Formation getFormation(String code){
        Formation f = null;
        int i = 0;
        while(i < this.lesFormations.size() && f == null){
            if(this.lesFormations.get(i).getCode().equals(code))
                f = this.lesFormations.get(i);
            i++;
        }
        
        return f;
    }

    public Professeur getProfesseur(String loginResponsable) {
        boolean trouve = false;
        int i = 0;
        Professeur p = null;
        while (!trouve && i < this.lesProfesseurs.size()) {
            if (this.lesProfesseurs.get(i).getLogin().equals(loginResponsable)) {
                p = this.lesProfesseurs.get(i);
                trouve = true;        
            }
            i++;
        }
        return p;
    }
    
    public Personnel getPersonnel(String login) {
        boolean trouve = false;
        int i = 0;
        Personnel p = null;
        while (!trouve && i < this.lesPersonnels.size()) {
            System.out.println("" + this.lesPersonnels.get(i).getLogin());
            if (this.lesPersonnels.get(i).getLogin().equals(login)) {
                p = this.lesPersonnels.get(i);
                trouve = true;        
            }
            i++;
        }
        return p;
    }

    public void affichageTestContenu() {
        System.out.println("Etudiants : ");
        for(Etudiant e : this.lesEtudiants) {
            System.out.println(e.toString());
        }
        System.out.println("Formations : ");
        for(Formation f : this.lesFormations) {
            System.out.println(f.toString());
        }
        System.out.println("Personnels : ");
        for(Personnel p : this.lesPersonnels) {
            System.out.println(p.toString());
        }
        System.out.println("Professeurs : ");
        for(Professeur p : this.lesProfesseurs) {
            System.out.println(p.toString());
        }
    }

    void afficherFormations() {
        System.out.println("------- Formations connues : ---------");
        for(Formation f : this.lesFormations) {
            System.out.println(f.getCode());
        }
        System.out.println("--------------------------------------");
    }

    void afficherModules() {
        System.out.println("--------- Modules connus : -----------");
        for(Formation f : this.lesFormations) {
            System.out.println(" -- pour la formation "+f.getNom());
            for(Module m : f.getModules()) {
                System.out.println("  -"+m.getCode());
            } 
        }
        System.out.println("--------------------------------------");

    }

    void afficherProfesseurs() {
        System.out.println("--Professeurs : --");
        for(Professeur p : lesProfesseurs) {
            System.out.println(p.getNom()+" "+p.getPrenom()+" de login : "+p.getLogin());
        }
    }

    void afficherPersonnes() {
        System.out.println("Personnes : ");
        for(Personne e : this.lesPersonnes) {
            System.out.println(e.getLogin());
        }
    }
    
    public ArrayList<Module> getModulesParProfesseur(Professeur professeur) {
        ArrayList<Module> result = new ArrayList<Module>();
        for (Formation formation : this.lesFormations) {
            for (Module module : formation.getModules()) {
                if (module.getResponsable().getNom().compareTo(professeur.getNom()) == 0) {
                    result.add(module);
                }    
            }
        }
        return result;
    }

    void ajouterEtudiant(String nom, String prenom, String mdp, Formation formation) {
        Etudiant etud = new Etudiant(this.loginSuivant(nom),mdp,nom,prenom,this);
        formation.addEtudiant(etud);
        this.lesEtudiants.add(etud);
        this.lesPersonnes.add(etud);
        Collections.sort(this.getLesPersonnes());
        this.afficherPersonnes();
    }
    
    

    void ajouterProfesseur(String nom, String prenom, String mdp) {
        Professeur prof = new Professeur(this.loginSuivant(nom),mdp,nom,prenom,this);
        this.lesProfesseurs.add(prof);
        this.lesPersonnes.add(prof);
        Collections.sort(this.getLesPersonnes());
        this.afficherPersonnes();
    }

    void ajouterPersonnel(String nom, String prenom, String mdp) {
        Personnel pers = new Personnel(this.loginSuivant(nom),mdp,nom,prenom,this);
        this.lesPersonnels.add(pers);
        this.lesPersonnes.add(pers);
        Collections.sort(this.getLesPersonnes());
        this.afficherPersonnes();
    }


    public boolean ajouterModule(Formation formation, String nomModule, String loginResponsable) {
        if (formation == null) {
            System.out.println("Formation inconnue");
            return false;
        }
        String codeModule = this.calculerCodeModule(formation, nomModule);
        Module m = new Module(nomModule, codeModule);
        if (m == null) {
            System.out.println("Module inconnu.");
            return false;
        }
        Professeur responsable = this.getProfesseur(loginResponsable);
        if (responsable == null) {
            System.out.println("Professeur inconnu.");
            return false;
        }
        m.setResponsable(responsable);
        formation.addModule(m);
        //this.lesFormations.get(this.lesFormations.indexOf(formation)).addModule(m);
        return true;
    }


    public String calculerCodeModule(Formation formation, String nomModule) {
        if(formation == null){
            return null;
        }
        
        String codeFormationDuModule = formation.getCode();
        
        String codeModule = "";
        Scanner scanner=new Scanner(nomModule);
        String[] motsDuNom;
        /* delimiter */
        String delimiter = " ";
        // On boucle sur chaque champ detecté
        
        String line = scanner.nextLine();
        motsDuNom = line.split(delimiter);
        for (int i = 0; i < motsDuNom.length; i++) {
            codeModule+=motsDuNom[i].substring(0, 1).toUpperCase();
            //System.out.println("Mot : "+);
        }
        codeModule = codeFormationDuModule+"-"+codeModule;
        System.out.println("code : "+codeModule);
        return codeModule;
    }


    Formation ajouterFormation(String nom, String nomSalleCM, String nomSalleTD) {
        String codeFormation = this.calculerCodeFormation(nom);
        if (codeFormation == null) {
            return null;
        }
        Salle salleCM = this.getSalle(nomSalleCM);
        if (salleCM == null) {
            System.out.println("Salle CM inconnue.");
            return null;
        }
        Salle salleTD = this.getSalle(nomSalleTD);
        if(salleTD == null) {
            System.out.println("Salle TD inconnue");
            return null;
        }
        Formation form = new Formation(nom, codeFormation, salleCM, salleTD);//----------/!\ Gaël, les salles !! ----
        this.lesFormations.add(form);
        System.out.println("Formation ajoutée.");
        return form;
    }   
  

    private String calculerCodeFormation(String nom) {
        String code = "";
        Scanner scanner=new Scanner(nom);
        String[] motsDuNom;
        /* delimiter */
        String delimiter = " ";
        // On boucle sur chaque champ detecté
        
        String line = scanner.nextLine();
        motsDuNom = line.split(delimiter);
        for (int i = 0; i < motsDuNom.length; i++) {
            code+=motsDuNom[i].substring(0, 1).toUpperCase();
            //System.out.println("Mot : "+);
        }
        System.out.println("code : "+code);
        return code;
    }

    public Salle getSalle(String nom) {
        int i = 0;
        Salle s = null;
        while(s == null & i<this.lesSalles.size()){
            Salle salle = this.lesSalles.get(i);
            if(salle.getNom().equals(nom)){
                s = salle;
            }
            i++;
        }
        return s;
    }
    
    public ArrayList<Seance> getSeances(Date dateDebut, Date dateFin) {
        ArrayList<Seance> seances = new ArrayList<Seance>();
        for(Seance s : lesSeances){
            if((s.getDate().after(dateDebut) || s.getDate().equals(dateDebut)) & (s.getDate().before(dateFin) || s.getDate().equals(dateFin))){
                seances.add(s);
            }
        }
        return seances;
    }
    
    public ArrayList<Seance> getSeancesJourHeure(Date date, int heure, int duree){
        ArrayList<Seance> seances = new ArrayList<Seance>();
        for(Seance s : lesSeances){
            if((s.getDate().equals(date))){
                if(s.getHeure() + s.getDuree() > heure){
                    if(s.getHeure() < heure+duree){
                        seances.add(s);
                    }
                }
                
            }
        }
        return seances;
    }

    public Etudiant getEtudiant(String log) {
        int i = 0;
        Etudiant e = null;
        while(e == null & i<this.lesEtudiants.size()){
            Etudiant etudiant = this.lesEtudiants.get(i);
            if(etudiant.getLogin().equals(log)){
                e = etudiant;
            }
            i++;
        }
        return e;
    }

    public void ajouterSeance(Seance seance) {
        this.lesSeances.add(seance);
    }
    
    public Module getModule(String codeModule) {
        Module module = null;
        boolean trouve = false;
        int i = 0;
        while(!trouve && i < this.lesFormations.size()) {
           Formation formation = this.lesFormations.get(i);  
           for(Module m : formation.getModules()) {
               if(m.getCode().equals(codeModule)) {
                   module = m;
                }
           }
           i++;
           
        }
        return module;
    }

    ArrayList<Seance> getSeancesParProfesseur(Professeur professeur) {
        ArrayList<Seance> seances = new ArrayList<Seance>();
        ArrayList<Module> modules = getModulesParProfesseur(professeur);
        for(Seance s : lesSeances){
            Module m = getModule(s.getCodeModule());
            if(modules.contains(m)){
                seances.add(s);
            }
        }
        return seances;
    }

    public void addSeance(Seance s) {
        lesSeances.add(s);
    }
    
    public boolean modifierFormation(String code, String nom, String nomSalleCM, String nomSalleTD) {
        Formation formation = this.getFormation(code);
        if (formation == null) {
            System.out.println("Formation inconnue");
            return false;
        }
        Salle salleCM = this.getSalle(nomSalleCM);
        if (salleCM == null) {
            System.out.println("Salle CM inconnue");
            return false;
        }
        Salle salleTD = this.getSalle(nomSalleTD);
        if (salleTD == null) {
            System.out.println("Salle TD inconnue");
            return false;
        }
        formation.setNom(nom);
        formation.setSalleCM(salleCM);
        formation.setSalleTD(salleTD);
        return true;
    }

    public boolean modifierModule(String codeModule, String codeProfesseur, String nomModule) {
        Module module = this.getModule(codeModule);
        if (module == null) {
            System.out.println("Module inconnu.");
            return false;
        }
        Professeur professeur = this.getProfesseur(codeProfesseur);
        if (professeur == null) {
            System.out.println("Professeur inconnu.");
            return false;
        }
        
        module.setNom(nomModule);
        module.setResponsable(professeur);
        return true;
    }

    boolean inscrireEtudiant(String login, String code) {
        Etudiant etud = this.getEtudiant(login);
        if (etud == null) {
            System.out.println("Etudiant inconnu.");
            return false;
        }
        Formation formation = this.getFormation(code);
        if (formation == null) {
            System.out.println("Formation inconnue.");
            return false;
        }
        formation.addEtudiant(etud);
        return true;
    }
    
    void afficherLesEtudiants() {
        System.out.println("Etudiants connus : ");
        for(Etudiant etudiant : lesEtudiants){
            System.out.println("-- "+etudiant.getLogin());
        }
    }
    
    public ArrayList<Resultat> getLesResultats() {
        return lesResultats;
    }

    public void setLesResultats(ArrayList<Resultat> lesResultats) {
        this.lesResultats = lesResultats;
    }

}
