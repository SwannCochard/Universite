package gestionUniversite;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gaelvarlet
 */
class ModeleApplication {
    private Universite universite;
    private Connexion c = gestionUniversite.Connexion.getInstance();
    private Statement stmt = c.getStatement();
    private Personne current;
    private boolean connected = false;
    
    public ModeleApplication() {
        current = null;
    }
    
    public void setUniversite(Universite universite) {
        this.universite = universite;
    }
    
    public boolean connecter(String login, String mdp) {
        String req = "select * from Utilisateur where login like '"+login+"' and mdp like '"+mdp+"'";
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            int i = 0;
            int id = 0;
            String nom = "";
            String prenom = "";
            String type = "";
            while(res.next()) {
                i++;
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                type = res.getString("type");
            }
            if (i != 1) {
                System.out.println("Personne n'a été trouvé de ce nom");
                connected = false;
            }
            else {
                connected = true;
                if (type.equals("etudiant")) {
                    current = new Etudiant(login,mdp,nom,prenom, this.universite);
                }
                if (type.equals("professeur")) {
                    current = new Professeur(login,mdp,nom,prenom, this.universite);
                }
                if (type.equals("personnel")) {
                    current = new Personnel(login,mdp,nom,prenom,this.universite);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    public void deconnecter() {
        current = null;
        this.setConnected(false);
    }
    
    public Personne getCurrent() {
        return current;
    }

    void ajouterFormation(String nom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void afficherLesFormations() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean modifierFormation(String code, String nom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void afficherLesModules() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void afficherLesProfesseurs() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean modifierModule(String codeModule, String codeProfesseur, String nomModule) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean leftToCommit() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void commit() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void afficherLesEtudiants() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    boolean inscrireEtudiant(String login, String code) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ArrayList<Seance> consulterEDT(Date dateDebut, Date dateFin) {
        System.out.println("Debut : " + dateDebut);
        System.out.println("Fin : " + dateFin);
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
