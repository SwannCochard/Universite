 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

/**
 *
 * @author Swann
 */
public abstract class Personne {
    private int identifiant;
    private String login;
    private String motDePasse;
    private String nom;
    private String prenom;
    protected Universite universite;
    
    public Personne(String motDePasse, String nom, String prenom, Universite universite) {
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.universite = universite;
    }

    public Personne(int identifiant, String login, String motDePasse, String nom, String prenom) {
        this.identifiant = identifiant;
        this.login = login;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    
    
    public abstract void save();

    public int getIdentifiant() {
        return identifiant;
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
    
    
    
}
