/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

/**
 *
 * @author gaelvarlet
 */
public abstract class Personne implements Comparable {
    protected String login;
    protected String motDePasse;
    protected String nom;
    protected String prenom;
    protected Universite universite;
    
    public Personne(String motDePasse, String nom, String prenom, Universite universite) {
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.universite = universite;
    }

    public Personne(String login, String motDePasse, String nom, String prenom, Universite universite) {
        this.login = login;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.universite = universite;
    }
    
    public String getLogin() {
        return login;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
    @Override
    public String toString() {
       String res = "Personne :\n"
               + "nom : "+nom
               + "prenom : "+prenom;
       return res;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    String getMdp() {
        return this.motDePasse;
    }
}
