package gestionUniversite;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Etudiant extends Personne implements ComposanteFac {
    private ComposanteFac successor;
    
    public Etudiant(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Etudiant(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }

    public ComposanteFac getSuccessor() {
        return successor;
    }

    public void setSuccessor(ComposanteFac successor) {
        this.successor = successor;
    }
    
    @Override
    public double getMoyenne() {
        ArrayList<Resultat> result = new ArrayList<Resultat>();
        for (Resultat r : this.universite.getLesResultats()) {
            if (r.getEtudiant().getLogin().equals(this.login)){
                result.add(r);
            }
        }
        int coefficient = 0;
        double moyenne = 0;
        for (Resultat r : result) {
            double cm = r.getNoteCM();
            double td = r.getNoteTD();
            double tp = r.getNoteTP();
            
            int coeffCM = r.getModule().getCoefCM();
            int coeffTD = r.getModule().getCoefTD();
            int coeffTP = r.getModule().getCoefTP();
            double temp = (cm*coeffCM + td*coeffTD + tp*coeffTP) / (coeffCM+coeffTD+coeffTP);
            
            moyenne += (temp*r.getModule().getCoefModule());
            coefficient += r.getModule().getCoefModule();
        }
        moyenne /= coefficient;
        return moyenne;
    }

    @Override
    public int getNbEtudiants() {
        return 1;
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
