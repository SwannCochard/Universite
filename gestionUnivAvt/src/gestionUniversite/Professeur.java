package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Professeur extends Personne{
    private ArrayList<Module> estResponsable = new ArrayList<Module>();

    public Professeur(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }
    
    public Professeur(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }

    public ArrayList<Module> getEstResponsable() {
        return estResponsable;
    }

    public void setEstResponsable(ArrayList<Module> estResponsable) {
        this.estResponsable = estResponsable;
    }
    
    
}
