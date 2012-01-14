package gestionUniversite;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Etudiant extends Personne implements ComposanteFac {
    private Universite universite;
    
    public Etudiant(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Etudiant(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }
    
    public void consulterNotes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList<Seance> consulterEDT(Date dateDebut, Date dateFin) {
        return null;
    }

    @Override
    public double getMoyenne() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNbEtudiants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
}
