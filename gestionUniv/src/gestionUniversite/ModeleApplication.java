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
        this.rapatrierDonnees();
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
                if (type.equals("Etudiant")) {
                    current = new Etudiant(login,mdp,nom,prenom, this.universite);
                }
                if (type.equals("Professeur")) {
                    current = new Professeur(login,mdp,nom,prenom, this.universite);
                }
                if (type.equals("Personnel")) {
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
        this.universite.afficherFormations();
    }

    boolean modifierFormation(String code, String nom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void afficherLesModules() {
        this.universite.afficherModules();
    }

    void afficherLesProfesseurs() {
        this.universite.afficherProfesseurs();
    }

    boolean modifierModule(String codeModule, String codeProfesseur, String nomModule) {
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

    private void rapatrierDonnees() {
//        this.lesFormations = new ArrayList<Formation>();
//        this.lesPersonnels = new ArrayList<Personnel>();
//        this.lesProfesseurs = new ArrayList<Professeur>();
//        this.lesSalles = new ArrayList<Salle>();
//        this.lesSeances = new ArrayList<Seance>();
        this.rapatrierSalles();
        this.rapatrierProfesseurs();
        this.rapatrierPersonnels();
        this.rapatrierEtudiants();
        this.rapatrierFormations();
        this.rapatrierSeances();
        
    }

    private void rapatrierFormations() {
        ArrayList<Formation> formations = new ArrayList<Formation>();
        String req = "";
        req = "select * from Formation";
        ResultSet res = null;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String code = res.getString("code");
                String nom = res.getString("nom");
                
                //-----------------------------------------/!\----------------------------
                //Aller chercher la salle dans l'AL de salles de l'université récedement recnstruite
                Formation formation = new Formation(nom, code, null, null);
                formations.add(formation);
                
            }

            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (Formation formation : formations) {
                formation.setModules(this.reconstruireModules(formation.getCode()));
            }
        this.universite.setLesFormations(formations);
    }

    private void rapatrierPersonnels() {
        ArrayList<Personnel> personnels = new ArrayList<Personnel>();
        String req = "";
        req = "select * from Utilisateur where type = 'personnel'";
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String login = res.getString("login");
                String mdp = res.getString("mdp");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Personnel personnel = new Personnel(login, mdp, nom, prenom,universite);
                personnels.add(personnel);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesPersonnels(personnels);
    }

    private void rapatrierProfesseurs() {
        ArrayList<Professeur> professeurs = new ArrayList<Professeur>();
        String req = "";
        req = "select * from Utilisateur where type = 'professeur'";
 
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String login = res.getString("login");
                String mdp = res.getString("mdp");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Professeur p = new Professeur(login, mdp, nom, prenom,universite);
                professeurs.add(p);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesProfesseurs(professeurs);
    }

    private void rapatrierSalles() {
        System.out.println("Salles non rapatriées");
    }

    private void rapatrierSeances() {
        System.out.println("Seances non rapatriées");
    }

    private void rapatrierEtudiants() {
        ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
        String req = "";
        req = "select * from Utilisateur where type = 'etudiant'";

        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String login = res.getString("login");
                String mdp = res.getString("mdp");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                Etudiant e = new Etudiant(login, mdp, nom, prenom,universite);
                etudiants.add(e);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesEtudiants(etudiants);

    }
    
    public ArrayList<Module> reconstruireModules(String codeFormation) {
        ArrayList<Module> modules = new ArrayList<Module>();
        String req = "select * from Module where codeFormation like '"+codeFormation+"'";
        ResultSet resbis = null;
        try { 
            resbis = gestionUniversite.Connexion.getInstance().getStatement2().executeQuery(req);
            while (resbis.next()) {
                String code = resbis.getString("code");
                String nom = resbis.getString("nom");
                int coeffTP = resbis.getInt("coeffTP");
                int coeffTD = resbis.getInt("coeffTD");
                int coeffCM = resbis.getInt("coeffCM");
                int coeffModule = resbis.getInt("coeffModule");
                String loginResponsable = resbis.getString("loginResponsable");
                Professeur p = this.universite.getProfesseur(loginResponsable);
                Module m = new Module(nom,code,coeffTD,coeffTP,coeffCM,coeffModule,p);
                System.out.println("Code Module : "+m.getCode());
                modules.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resbis != null) {
                try {
                    resbis.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return modules;
        
    }

    public void commit() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // FONCTIONS MAXIME
    public boolean setCoefficient(Module module, int coefficient, int type) {
        boolean result;
        switch (type) {
            case (1):
                module.setCoefModule(coefficient);
                result = true;
                break;
            case (2):
                module.setCoefCM(coefficient);
                result = true;
                break;
            case (3):
                module.setCoefTD(coefficient);
                result = true;
                break;
            case (4):                   
                module.setCoefTP(coefficient);
                result = true;
                break;
            default :
                result = false;
        }  
        return result;
    }
}
