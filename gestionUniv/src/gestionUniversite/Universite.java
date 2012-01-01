package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author Swann
 */
public class Universite {
    private ArrayList<Personne> lesPersonnes;
    private ArrayList<Etudiant> lesEtudiants;
    private ArrayList<Personnel> lesPersonnels;
    private ArrayList<Professeur> lesProfesseurs;
    private ArrayList<Formation> lesFormations;
    private ArrayList<Salle> lesSalles;
    private ArrayList<Sceance> lesSceances;
    private boolean leftToCommit;
    private String nom;
    private ModeleApplication modeleApplication;

    public Universite(String nom, ModeleApplication modeleApplication) {
        this.nom = nom;
        this.modeleApplication = modeleApplication;
        this.lesEtudiants = new ArrayList<Etudiant>();
        this.lesFormations = new ArrayList<Formation>();
        this.lesPersonnels = new ArrayList<Personnel>();
        this.lesPersonnes = new ArrayList<Personne>();
        this.lesProfesseurs = new ArrayList<Professeur>();
        this.lesSalles = new ArrayList<Salle>();
        this.lesSceances = new ArrayList<Sceance>();
        this.leftToCommit = false;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void ajouterPersonne(Personne personne) {
        String login;
        if (this.lesPersonnes.isEmpty() || this.dernierDuNom(personne.getNom()).isEmpty()) {
            login = this.modeleApplication.calculerLogin(personne.getNom());
        }
        else {
            //
            login = this.loginSuivant(this.dernierDuNom(personne.getNom()));
        }
        personne.setLogin(login);
        lesPersonnes.add(personne);
    }
    
    public String dernierDuNom(String nom) {
        nom = nom.substring(0, 4);
        String nomRes ="";
        for(Personne p : lesPersonnes) {
            String nomCourant = p.getNom().substring(0, 4);
            if (nomCourant.equals(nom)) {
                nomRes = p.getLogin();
            }
        }
        return nomRes;
    }
    
    public String loginSuivant(String login) {
        String res = login.substring(0,login.lastIndexOf("0"));
        int dernierNb = Integer.parseInt(login.substring(login.lastIndexOf("0")+1));
        dernierNb++;
        res = res+dernierNb;
        return res;
    }

    public void ajouterFormation(Formation formation) {
        if (formation.getLesModules() == null) {
            formation.setLesModules(new ArrayList<Module>());
        }
        lesFormations.add(formation);
        //modeleApplication.ajouterFormation(nom, code);
    }

    public boolean ajouterModule(Module module, Formation formation, String codeResponsable) {
        Professeur p = this.getProfesseur(codeResponsable);
        if (p == null) {
            return false;
        }
            
        else {
            module.setResponsable(p);
            int indexDeLaFormation = this.lesFormations.indexOf(formation);
            if (indexDeLaFormation != -1) {
                this.lesFormations.get(indexDeLaFormation).ajouterModule(module);
            }
            p.ajouterModule(module);
            return true;
        }
    }

    public boolean inscrireEtudiant(String loginEtu, String codeFormation) {
        Etudiant etudiant = this.getEtudiant(loginEtu);
        Formation formation = this.getFormation(codeFormation);
        if (etudiant == null) {
            System.out.println("Etudiant inconnu.");
            return false;
        }
        if (formation == null){
            System.out.println("Formation inconnue");
            return false;
        }
        etudiant.setFormation(formation);
        int index = -1;
        int i = 0;
        while(index == -1 && i < lesEtudiants.size()) {
            if (lesEtudiants.get(i).getLogin().equals(loginEtu)) {
                index = i;
                
            }
            i++;
        }
        if (index == -1) {
            System.out.println("Etudiant inconnu (mais si on arrive ici c'est qu'il y a un gros soucis...)");
            return false;
        }
        lesEtudiants.set(index,etudiant);
        return true;
        //this.modeleApplication.inscrireOrUpdateEtudiant(etudiant, formation);
    }
    
    public void commitModifications() {
        this.commitFormations();
        this.commitModules();
        this.commitPersonnes();
    }

    private void commitFormations() {
        for(Formation formation : lesFormations) {
            this.modeleApplication.ajouterFormation(formation);
        }
    }

    private void commitModules() {
        for(Formation formation : lesFormations) {
            for (Module module : formation.getLesModules())
            this.modeleApplication.ajouterModule(module, formation);
        }
        lesFormations.clear();
    }

    private void commitPersonnes() {
        
        for(Personne personne : lesPersonnes) {
            this.modeleApplication.ajouterPersonne(personne);
        }
        for (Etudiant etudiant : lesEtudiants) {
            this.modeleApplication.inscrireOrUpdateEtudiant(etudiant, etudiant.getFormation());
        }
        lesPersonnes.clear();
        this.lesEtudiants.clear();
        this.lesPersonnels.clear();
        this.lesProfesseurs.clear();
    }

    public boolean leftToCommit() {
        this.leftToCommit = this.lesEtudiants.isEmpty() && this.lesFormations.isEmpty() && this.lesPersonnels.isEmpty() && this.lesProfesseurs.isEmpty() && this.lesSalles.isEmpty() && this.lesPersonnes.isEmpty();
        return !leftToCommit;
    }

    public Professeur getProfesseur(String codeResponsable) {
        this.ajouterProfesseursTable();
        Professeur professeur = null;
        for (Professeur p : lesProfesseurs) {
            if (p.getLogin().equals(codeResponsable)) {
                professeur = p;
            }
        }
        return professeur;
    }

    public void ajouterProfesseursTable() {
        ArrayList<Professeur> professeursTable = new ArrayList<Professeur>();
        String dejaPresents = "";
        for (Personne professeur : lesPersonnes) {
            dejaPresents+="'"+professeur.getLogin()+"',";
        }
        if(!dejaPresents.isEmpty()){
            dejaPresents = dejaPresents.substring(0, dejaPresents.lastIndexOf(","));
        }
        professeursTable = this.modeleApplication.getProfesseurs(dejaPresents);
        lesProfesseurs.addAll(professeursTable);
        lesPersonnes.addAll(professeursTable);
    }
    
    public Etudiant getEtudiant(String login) {
        this.ajouterEtudiantsTable();
        Etudiant etudiant = null;
        for (Etudiant e : lesEtudiants) {
            if (e.getLogin().equals(login)) {
                etudiant = e;
            }
        }
        return etudiant;
    }

    private void ajouterEtudiantsTable() {
        ArrayList<Etudiant> etudiantsTable = new ArrayList<Etudiant>();
        String dejaPresents = "";
        for (Etudiant etudiant : lesEtudiants) {
            dejaPresents+="'"+etudiant.getLogin()+"',";
        }
        if(!dejaPresents.isEmpty()){
            dejaPresents = dejaPresents.substring(0, dejaPresents.lastIndexOf(","));
        }
        etudiantsTable = this.modeleApplication.getEtudiant(dejaPresents);
        lesEtudiants.addAll(etudiantsTable);
        lesPersonnes.addAll(etudiantsTable);
    }

    public Module getModule(String codeModule) {
        ArrayList<Module> modulesTable = new ArrayList<Module>();
        ArrayList<Module> lesModules = new ArrayList<Module>();
        String dejaPresents = "";
        for (Formation formation : lesFormations) {
            for (Module module : formation.getLesModules()) {
                dejaPresents+="'"+module.getCode()+"',";
                lesModules.add(module);
            }
        }
        if(!dejaPresents.isEmpty()){
            dejaPresents = dejaPresents.substring(0, dejaPresents.lastIndexOf(","));
        }
        modulesTable = this.modeleApplication.getModules(dejaPresents);
        lesModules.addAll(modulesTable);
        Module res = null;
        for(Module module : lesModules) {
            if (module.getCode().equals(codeModule)) {
                res = module;
            }
        }
        return res;
    }

    public Formation getFormation(String codeFormation) {
        this.ajouterFormationsTable();
        Formation res = null;
        for(Formation formation : lesFormations) {
            formation.setLesModules(this.modeleApplication.reconstruireModules(formation.getCode()));
        }
        for(Formation formation : lesFormations) {
            if (formation.getCode().equals(codeFormation)) {
                res = formation;
            }
        }
        return res;   
    }
    
    public void ajouterFormationsTable() {
        ArrayList<Formation> formationsTable = new ArrayList<Formation>();
        String dejaPresents = "";
        for (Formation formation : lesFormations) {
            dejaPresents+="'"+formation.getCode()+"',";
        }
        if(!dejaPresents.isEmpty()){
            dejaPresents = dejaPresents.substring(0, dejaPresents.lastIndexOf(","));
        }
        formationsTable = this.modeleApplication.getFormations(dejaPresents);
        lesFormations.addAll(formationsTable);
    }
    
    public void afficherLesProfesseurs() {
        System.out.println("Professeurs repertoriés : ");
        this.ajouterProfesseursTable();
        for (Professeur p : lesProfesseurs) {
            System.out.println(p.getLogin());
        }
    }
    
    public void afficherLesEtudiants() {
        System.out.println("Etudiants repertoriés : ");
        this.ajouterEtudiantsTable();
        for (Etudiant e : lesEtudiants) {
            System.out.println(e.getLogin());
        }
    }
    
    public void afficherLesFormations() {
        System.out.println("Formations répertoriées : ");
        this.ajouterFormationsTable();
        for(Formation f : lesFormations) {
            System.out.println(f.getCode());
        }
    }
    
    public void afficherLesModules() {
        System.out.println("Modules repertoriés : ");
        this.ajouterFormationsTable();
        for(Formation f : lesFormations) {
            for(Module m : f.getLesModules()) {
                System.out.println(m.getCode());
            }
        }  
    }
}
