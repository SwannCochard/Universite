/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Swann
 */
public class ModeleApplication {
    private Connexion c = gestionUniversite.Connexion.getInstance();
    private Statement stmt = c.getStatement();
    private Personne current;
    private boolean connected = false;
    private Universite universite;
    private boolean leftToCommit;
    public ModeleApplication() {
        current = null;
    }

    public void setUniversite(Universite universite) {
        this.universite = universite;
        this.leftToCommit = false;
    }
    public Statement getStmt() {
        return stmt;
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
    
    
    public void ajouterPersonne(Personne personne) {
        insertOrUpdateUtilisateur(personne);
    }


    public String calculerLogin(String nom) {
        int borneSup = 4;
        if (nom.length() < 4) {
            borneSup = nom.length();
        }
        String login = nom.substring(0,borneSup); 
        login = login.toLowerCase();
        String req = "select count(*) from Utilisateur where login like '"+login+"%'";
        ResultSet res;
        String nb = "";
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            res.next();
            int nbLogin = res.getInt(1)+1;
            nb = ""+nbLogin;
            int taille = nb.length();
            for (int i = taille; i<4;i++) {
               
                nb = "0"+nb;
            }    
            login += nb;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return login;
    }

    void ajouterFormation(Formation formation) {       
        insertOrUpdateFormation(formation);
    }
    
    public void ajouterFormation(String nom) {
        ((Personnel) current).ajouterFormation(nom);
    }
    
    public boolean modifierModule(String codeModule, String codeProfesseur, String nom) {
        if (codeModule.lastIndexOf("-") == -1) {
            System.out.println("Module inconnu.");
            return false;
        }
        String codeFormation = codeModule.substring(0,codeModule.lastIndexOf("-"));
        Formation formation = this.universite.getFormation(codeFormation);
        this.universite.ajouterFormation(formation);
        Module module = this.universite.getModule(codeModule);
        if (module == null) {
            System.out.println("Module inconnu");
            return false;
        }
        Professeur professeur = this.universite.getProfesseur(codeProfesseur);
        if (professeur == null) {
            System.out.println("Professeur inconnu.");
            return false;
        }
        return ((Personnel)current).modifierModule(module, professeur, nom);
        
    }
    
    public boolean modifierFormation(String codeFormation, String nom) {
        Formation formation = this.universite.getFormation(codeFormation);
        return ((Personnel)current).modifierFormation(formation, nom);
    }

    public Personne getCurrent() {
        return current;
    }

    public boolean inscrireEtudiant(String login, String codeFormation) {
        return ((Personnel) current).inscrireEtudiant(login, codeFormation);
    }
    public void inscrireEtudiant(Etudiant etudiant, Formation formation) {
        inscrireOrUpdateEtudiant(etudiant, formation);
    }

    void ajouterModule(Module module, Formation formation) {
        insertOrUpdateModule(module,formation);
    }
    
    public void commit() {
        this.universite.commitModifications();
    }

    public ArrayList<Professeur> getProfesseurs(String dejaPresents) {
        ArrayList<Professeur> professeurs = new ArrayList<Professeur>();
        String req = "";
        if(dejaPresents.isEmpty()) {
            req = "select * from Utilisateur where type = 'professeur'";
 
        }
        else {
            req = "select * from Utilisateur where type = 'professeur' and login not in ("+dejaPresents+")";
        }
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
        return professeurs;
    }

    public void insertOrUpdateModule(Module module, Formation formation) {
        String nom = module.getNom();
        String code = module.getCode();
        int coefTD = module.getCoefTD();
        int coefCM = module.getCoefCM();
        int coefTP = module.getCoefTP();
        int coefModule = module.getCoefModule();
        String req = "select count(*) from Module where code like '"+code+"'";
        String responsable = module.getResponsable().getLogin();
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            int nbRes = 0;
            if (res.next()) {    
                nbRes = res.getInt(1);
            }
            if (nbRes == 0) {
                req = "insert into Module (nom,code,loginResponsable,coeffTP,coeffTD,coeffCM,coeffModule) values ('"+nom+"','"+code+"','"+responsable+"',1,1,1,1)";
                gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            }
            else {
                req = "update Module set nom = '"+nom+"', coeffTP = "+coefTP+", coeffTD = "+coefTD+",coeffCM = "+coefCM+",coeffModule = "+coefModule+", loginResponsable = '"+module.getResponsable().getLogin()+"' where code like '"+code+"'";
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(req);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inscrireOrUpdateEtudiant(Etudiant etudiant, Formation formation) {
        if (formation != null) {
            String loginEtu = etudiant.getLogin();
            String req = "delete from Participe where login like '"+etudiant.getLogin()+"'";
            ResultSet res;
            try {
                gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Module module : formation.getLesModules()) {
                String codeModule = module.getCode();
                req = "insert into Participe (login,codeModule,noteCM,noteTP,noteTD) values ('"+loginEtu+"','"+codeModule+"',0,0,0)";
                try {
                    gestionUniversite.Connexion.getInstance().getStatement().execute(req);
                } catch (SQLException ex) {
                    Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

    public void insertOrUpdateFormation(Formation formation) {
        String nom = formation.getNom();
        String code = formation.getCode();
        String req = "select count(*) from Formation where code like '"+code+"'";
        
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            int nbRes = 0;
            if (res.next()) {    
                nbRes = res.getInt(1);
            }
            if (nbRes == 0) {
                req = "insert into Formation (nom,code,nomUniversite,nomSalleCM,nomSalleTD) values ('"+nom+"','"+code+"','UHP','uneSalleCM','uneSalleTD')";
                gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            }
            else {
                req = "update Formation set nom = '"+nom+"' where code like '"+code+"'";
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(req);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertOrUpdateUtilisateur(Personne personne) {
        String nom = personne.getNom();
        String prenom = personne.getPrenom();
        String login = personne.getLogin();
        String mdp = personne.getMdp();
        String type = personne.getClass().toString().toLowerCase();
        type = type.substring(type.lastIndexOf(".")+1);
        String req = "select count(*) from Utilisateur where login like '"+login+"'";
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            int nbRes = 0;
            if (res.next()) {    
                nbRes = res.getInt(1);
            }
            if (nbRes == 0) {
                req = "insert into Utilisateur (login,mdp,nom,prenom,type, nomUniversite) values ('"+login+"','"+mdp+"','"+nom+"','"+prenom+"', '"+type+"','"+this.universite.getNom()+"')";
                System.out.println("req : "+req);
                gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            }
            else {
                req = "update Utilisateur set nom = '"+nom+"',mdp = '"+mdp+"',prenom = '"+prenom+"', nomUniversite = '"+this.universite.getNom()+"' where login like '"+login+"'";
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(req);
            }
        } catch (SQLException ex) {
            System.out.println("req : "+req);
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Module> getModules(String dejaPresents) {
        ArrayList<Module> modules = new ArrayList<Module>();
        String req = "";
        if(dejaPresents.isEmpty()) {
            req = "select * from Module";
 
        }
        else {
            req = "select * from Module where code not in ("+dejaPresents+")";
        }
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement2().executeQuery(req);
            while (res.next()) {
                String code = res.getString("code");
                String nom = res.getString("nom");
                int coeffTP = res.getInt("coeffTP");
                int coeffTD = res.getInt("coeffTD");
                int coeffCM = res.getInt("coeffCM");
                int coeffModule = res.getInt("coeffModule");
                String loginResponsable = res.getString("loginResponsable");
                Professeur p = this.universite.getProfesseur(loginResponsable);
                Module m = new Module(nom,code,coeffTD,coeffTP,coeffCM,coeffModule,p);
                modules.add(m);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modules;
    }

    public ArrayList<Formation> getFormations(String dejaPresents) {
        ArrayList<Formation> formations = new ArrayList<Formation>();
        String req = "";
        if(dejaPresents.isEmpty()) {
            req = "select * from Formation";
 
        }
        else {
            req = "select * from Formation where code not in ("+dejaPresents+")";
        }
        ResultSet res = null;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String code = res.getString("code");
                String nom = res.getString("nom");
                Formation formation = new Formation(nom, code);
                //formation.setLesModules(this.reconstruireModules(formation.getCode()));
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
                formation.setLesModules(this.reconstruireModules(formation.getCode()));
            }
        return formations;    
    }

    ArrayList<Etudiant> getEtudiant(String dejaPresents) {
        ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
        String req = "";
        if(dejaPresents.isEmpty()) {
            req = "select * from Utilisateur where type = 'etudiant'";
 
        }
        else {
            req = "select * from Utilisateur where type = 'etudiant' and login not in ("+dejaPresents+")";
        }
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
        return etudiants;
    }

    public ArrayList<Module> reconstruireModules(String codeFormation) {
        ArrayList<Module> modules = new ArrayList<Module>();
        String req = "select * from Module where code like '"+codeFormation+"-%'";
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

    boolean leftToCommit() {
        return this.universite.leftToCommit();
    }
    
    public void afficherLesProfesseurs() {
        this.universite.afficherLesProfesseurs();
    }
    
    public void afficherLesFormations() {
        this.universite.afficherLesFormations();
    }
    
    public void afficherLesModules() {
        this.universite.afficherLesModules();
    }
    public void afficherLesEtudiants() {
        this.universite.afficherLesEtudiants();
    }
    
    public ArrayList<Sceance> consulterEDT(Date dateDebut, Date dateFin){
        /*
         * Pour créer une date de type java.sql.Date faire
         * java.sql.Date jsqlD = java.sql.Date.valueOf( "2010-01-31" );
         * A voir, ça peut être pratique et facilement réutilisable pour la BDD !
         * 
         * Pour afficher un string de la Date SQL
         * Date date = ...; // wherever you get this from  
         * DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
         * String text = df.format(date);  
         * System.out.println("The date is: " + text);  
         * 
         */
        
        /*
         * Faut-il faire -> 
         * 1. Aller chercher ensembles des séances dans la BDD
         * 2. Les ajouter dans l'université (vérifier au passage si elles sont déjà dedans)
         * 3. Demander à l'étudiant ou au prof de regarder son edt
         * 
         */
        
        /*
         * ArrayList<Sceance> modules = new ArrayList<Sceance>();
         * Récup BDD
         * Reconstruction objets BDD
         */
        
        return null;
    }
}
