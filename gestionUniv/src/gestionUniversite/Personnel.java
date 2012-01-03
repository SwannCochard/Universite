package gestionUniversite;

/**
 *
 * @author gaelvarlet
 */
public class Personnel extends Personne{
    public Personnel(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Personnel(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }

    void ajouterEtudiant(String nom, String prenom, String mdp) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void ajouterProfesseur(String nom, String prenom, String mdp) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void ajouterPersonnel(String nom, String prenom, String mdp) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean ajouterModule(String nomModule, String loginResponsable) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
