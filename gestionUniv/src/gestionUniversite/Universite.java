/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
        System.out.println("Res : "+res);
        int dernierNb = Integer.parseInt(login.substring(login.lastIndexOf("0")+1));
        System.out.println("Dernier nb : "+dernierNb);
        dernierNb++;
        res = res+dernierNb;
        return res;
    }

    public void ajouterFormation(Formation formation) {
        lesFormations.add(formation);
        //modeleApplication.ajouterFormation(nom, code);
    }

    public void ajouterModule(Module module, Formation formation, String codeResponsable) {
        Professeur p = this.getProfesseur(codeResponsable);
        module.setResponsable(p);
        int indexDeLaFormation = this.lesFormations.indexOf(formation);
        if (indexDeLaFormation != -1) {
            this.lesFormations.get(indexDeLaFormation).ajouterModule(module);
        }
        p.ajouterModule(module);
    }

    public void inscrireEtudiant(String loginEtu, String codeFormation) {
        this.modeleApplication.inscrireEtudiant(loginEtu, codeFormation);
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
        lesPersonnes.clear();
    }

    public boolean leftToCommit() {
        this.leftToCommit = this.lesEtudiants.isEmpty() && this.lesFormations.isEmpty() && this.lesPersonnels.isEmpty() && this.lesProfesseurs.isEmpty() && this.lesSalles.isEmpty();
        return leftToCommit;
    }

//    void ajouterPersonne(String nom, String prenom, String login, String mdp, String type) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    private Professeur getProfesseur(String codeResponsable) {
        this.ajouterProfesseursTable();
        Professeur professeur = null;
        for (Professeur p : lesProfesseurs) {
            if (p.getLogin().equals(codeResponsable)) {
                professeur = p;
            }
        }
        return professeur;
    }

    private void ajouterProfesseursTable() {
        ArrayList<Professeur> professeursTable = new ArrayList<Professeur>();
        String dejaPresents = "";
        for (Personne professeur : lesProfesseurs) {
            dejaPresents+="'"+professeur.getLogin()+"',";
        }
        if(!dejaPresents.isEmpty()){
            dejaPresents = dejaPresents.substring(0, dejaPresents.lastIndexOf(",")-1);
        }
        System.out.println("Déjà là : " +dejaPresents);
        professeursTable = this.modeleApplication.getProfesseurs(dejaPresents);
        lesProfesseurs.addAll(professeursTable);
        lesPersonnes.addAll(professeursTable);
    }
}
