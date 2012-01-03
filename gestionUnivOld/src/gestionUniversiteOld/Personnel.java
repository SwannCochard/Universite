package gestionUniversiteOld;

import java.util.Scanner;

/**
 *
 * @author Swann
 */
public class Personnel extends Personne {
    private Formation currentFormation; //Formation sur laquelle travaille le personnel en ce moment

    public Personnel(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Personnel(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }
    
    public void ajouterEtudiant(String nom, String prenom, String mdp) {
        Etudiant e = new Etudiant(mdp,nom, prenom, universite);
        universite.ajouterPersonne(e);
    }
    
    public void ajouterProfesseur(String nom, String prenom, String mdp) {
        Professeur p = new Professeur(mdp,nom, prenom,  universite);
        universite.ajouterPersonne(p);
    }
    
    public void ajouterPersonnel(String nom, String prenom, String mdp) {
        Personnel p = new Personnel(mdp,nom, prenom, universite);
        universite.ajouterPersonne(p);
    }

    public boolean inscrireEtudiant(String loginEtu, String codeFormation) {
        return universite.inscrireEtudiant(loginEtu, codeFormation);
        
    }
    
    public boolean ajouterModule(String nom, String codeResponsable) {
        String codeModule = this.calculerCodeModule(currentFormation, nom);
        Module module = new Module(nom, codeModule);
        boolean res = universite.ajouterModule(module, currentFormation, codeResponsable);
        return res;
        
    }
    
    public void ajouterFormation(String nom) { 
        String codeFormation = this.calculerCodeFormation(nom);
        Formation formation = new Formation(nom, codeFormation);
        this.currentFormation = formation; //La formation sur laquelle je travaille sera la dernière a avoir été ajoutée
        universite.ajouterFormation(formation);
    }
    
    public boolean modifierFormation(Formation formation,String nom) {
        if (formation == null) {
            return false;
        }
        this.currentFormation = formation;
        formation.setNom(nom);
        return true;
        
    }
    
    public boolean modifierModule(Module module, Professeur professeur, String nom) {
        System.out.println("On a modifié un module chez Personnel");
        module.setNom(nom);
        module.setResponsable(professeur);
        return true;
        
    }
    
    public void calculerMoyennes() {
        calculerMoyennesEtudiants();
        calculerMoyennesModules();
        calculerMoyennesFormations();
    }
    
    
    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void calculerMoyennesEtudiants() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void calculerMoyennesModules() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void calculerMoyennesFormations() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String calculerCodeFormation(String nom) {
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
    
    public String calculerCodeModule(Formation formation, String nom) {
        String codeFormationDuModule = formation.getCode();
        
        String codeModule = "";
        Scanner scanner=new Scanner(nom);
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

    public Formation getCurrentFormation() {
        return currentFormation;
    }
    
}