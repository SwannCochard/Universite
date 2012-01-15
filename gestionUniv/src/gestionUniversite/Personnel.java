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
    
    @Override
    public int compareTo(Object t) {
        String premPartLoginThis = this.login.substring(0,4);
        int deuxPartLoginThis = Integer.parseInt(this.login.substring(4,8));
        String premPartLoginP = ((Personne)(t)).getLogin().substring(0,4);
        int deuxPartLoginP = Integer.parseInt(((Personne)(t)).getLogin().substring(4,8));
        if (premPartLoginThis.equals(premPartLoginP)) {
            if(deuxPartLoginThis < deuxPartLoginP) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else {
            if(premPartLoginThis.compareTo(premPartLoginP) < 0) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

   
}
