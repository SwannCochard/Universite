package gestionUniversite;

/**
 *
 * @author gaelvarlet
 */
public class Personnel extends Personne{
    private Formation currentFormation; //Formation sur laquelle travaille le personnel en ce moment
    public Personnel(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Personnel(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }

    void ajouterEtudiant(String nom, String prenom, String mdp) {
        //Etudiant etudiant = new Etudiant(String login, String motDePasse, String nom, String prenom, Universite universite);
        this.universite.ajouterEtudiant(nom, prenom, mdp);
    }

    void ajouterProfesseur(String nom, String prenom, String mdp) {
        this.universite.ajouterProfesseur(nom, prenom, mdp);
    }

    void ajouterPersonnel(String nom, String prenom, String mdp) {
        this.universite.ajouterPersonnel(nom, prenom, mdp);
    }

    boolean ajouterModule(String nomModule, String loginResponsable) {
        this.universite.ajouterModule(currentFormation, nomModule, loginResponsable);
        return true;//Pourquoi pas hein merde
    }
    //this.modeleApplication.ajouterFormation(nom);

    
    @Override
    public int compareTo(Object t) {
        String premPartLoginThis = this.login.substring(0,4);
        int deuxPartLoginThis = Integer.parseInt(this.login.substring(4,8));
        String premPartLoginP = ((Personne)(t)).getLogin().substring(0,4);
        int deuxPartLoginP = Integer.parseInt(((Personne)(t)).getLogin().substring(4,8));
        //System.out.println("Courant : nom = "+premPartLoginThis+", num = "+deuxPartLoginThis+"(à partir de "+this.login.substring(4,8)+")");
        if (premPartLoginThis.equals(premPartLoginP)) {
            if(deuxPartLoginThis < deuxPartLoginP) {
                //System.out.println(this.login+" vient avant "+((Personne)(t)).getLogin());
                return -1;
            }
            else {
                //System.out.println(this.login+" vient après "+((Personne)(t)).getLogin());
                return 1;
            }
        }
        else {
            if(premPartLoginThis.compareTo(premPartLoginP) < 0) {
                //System.out.println("comparaison de "+premPartLoginThis+" et de "+premPartLoginP+" : "+premPartLoginThis.compareTo(premPartLoginP));

                return -1;
            }
            else {
                return 1;
            }
            //return premPartLoginThis.compareTo(premPartLoginP);
        }
    }

    void ajouterFormation(String nom) {
        this.currentFormation = this.universite.ajouterFormation(nom);
    }

   
}
