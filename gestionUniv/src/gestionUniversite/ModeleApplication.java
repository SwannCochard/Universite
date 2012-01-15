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
    private Formation currentFormation = null;
    
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
            String log = "";
            while(res.next()) {
                i++;
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                type = res.getString("type");
                log = res.getString("login");
            }
            if (i != 1) {
                System.out.println("Personne n'a été trouvé de ce nom");
                connected = false;
            }
            else {
                connected = true;
                if (type.equals("Etudiant")) {
                    current = universite.getEtudiant(log);
                }
                if (type.equals("Professeur")) {
                    current = universite.getProfesseur(log);
                }
                if (type.equals("Personnel")) {
                    current = universite.getPersonnel(log);
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

    public boolean ajouterFormation(String nom, String nomSalleCM, String nomSalleTD) {
        this.currentFormation = this.universite.ajouterFormation(nom, nomSalleCM, nomSalleTD);
        return (this.currentFormation != null);
    }

    void afficherLesFormations() {
        this.universite.afficherFormations();
    }

    boolean modifierFormation(String code, String nom, String nomSalleCM, String nomSalleTD) {
        this.currentFormation = this.universite.getFormation(code);
        return this.universite.modifierFormation(code, nom, nomSalleCM, nomSalleTD);
    }

    void afficherLesModules() {
        this.universite.afficherModules();
    }

    void afficherLesProfesseurs() {
        this.universite.afficherProfesseurs();
    }

    boolean modifierModule(String codeModule, String codeProfesseur, String nomModule) {
        return this.universite.modifierModule(codeModule, codeProfesseur, nomModule);
    }

    boolean inscrireEtudiant(String login, String code) {
        return this.universite.inscrireEtudiant(login, code);
    }

    private void rapatrierDonnees() {
        this.rapatrierSalles();
        this.rapatrierProfesseurs();
        this.rapatrierFormations();
        this.rapatrierPersonnels();
        this.rapatrierEtudiants();
        this.rapatrierSeances();
        this.rapatrierResultats();
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
                
                String nomSalleCM = res.getString("nomSalleCM");
                String nomSalleTD = res.getString("nomSalleTD");
                
                Salle salleCM = universite.getSalle(nomSalleCM);
                Salle salleTD = universite.getSalle(nomSalleTD);
                
                Formation formation = new Formation(nom, code, salleCM, salleTD);
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
        
        this.universite.setLesFormations(formations);
        for (Formation formation : formations) {
                formation.setModules(this.reconstruireModules(formation.getCode()));
                formation.setSuccessor(this.universite);
            }
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
        ArrayList<Salle> salles = new ArrayList<Salle>();
        String req = "";
        req = "select * from Salle";
 
        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String nom = res.getString("nom");
                int capacite = res.getInt("capacite");
                Salle p = new Salle(nom, capacite);
                salles.add(p);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesSalles(salles);
    }

    private void rapatrierSeances() {
        ArrayList<Seance> seances = new ArrayList<Seance>();
        String req = "";
        req = "select * from Seance ORDER BY DATE, HEURE ASC";

        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String type = res.getString("type");
                Date date = res.getDate("date");
                int heure = res.getInt("heure");
                int duree = res.getInt("duree");
                String nom = res.getString("nomSalle");
                String codeModule = res.getString("codeModule");
                
                Salle salle = universite.getSalle(nom);
                
                Seance e = new Seance(type, codeModule, date, heure, duree, salle);
                seances.add(e);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesSeances(seances);
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
                
                String codeFormation = res.getString("codeFormation");
                Formation f = universite.getFormation(codeFormation);
                f.addEtudiant(e);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesEtudiants(etudiants);

    }
    
    private void rapatrierResultats() {
        ArrayList<Resultat> resultats = new ArrayList<Resultat>();
        String req = "";
        req = "select * from Participe";

        ResultSet res;
        try {
            res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
            while (res.next()) {
                String login = res.getString("login");
                String codeModule = res.getString("codeModule");
                Double noteCM = res.getDouble("noteCM");
                Double noteTD = res.getDouble("noteTD");
                Double noteTP = res.getDouble("noteTP");
                
                Etudiant etudiant = this.universite.getEtudiant(login);
                Module module = this.universite.getModule(codeModule);
                
                
                Resultat resultat = new Resultat(etudiant, module);
                
                resultat.setNoteCM(noteCM);
                resultat.setNoteTD(noteTD);
                resultat.setNoteTP(noteTP);
                
                resultats.add(resultat);
            }
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.universite.setLesResultats(resultats);

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
                modules.add(m);
                
                Formation formation = universite.getFormation(codeFormation);
                formation.addModule(m);
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
    
    public void commit() {
        this.viderBase();
        this.exporterSalle();
        this.exporterPersonnel();
        this.exporterProfesseur();
        this.exporterFormation();
        this.exporterEtudiant();
        this.exporterSeance();
        this.exporterResultats();
    }
    
    public void viderBase() {
        String etudiant = "delete from Utilisateur where type = 'Etudiant';";
        String personnel = "delete from utilisateur where type = 'Personnel';";
        String formation = "delete from Formation;";        
        String salle = "delete from Salle;";
        String professeur = "delete from Utilisateur where type = 'Professeur';";
        String module = "delete from Module;";
        String seance = "delete from Seance;";
        String resultats = "delete from Participe;";
        
        try { 
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(resultats);
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(etudiant);
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(personnel);      
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(seance);
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(module);
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(formation);
            
            
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(salle);
            gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(professeur);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void exporterEtudiant() {
        ArrayList<Etudiant> etudiants = this.universite.getLesEtudiants();
        
        for (Etudiant etudiant : etudiants) {
            String login = etudiant.getLogin();
            String mdp = etudiant.getMdp();
            String nom = etudiant.getNom();
            String prenom = etudiant.getPrenom();
            String nomUniversite = this.universite.getNom();
            String formation = ((Formation)etudiant.getSuccessor()).getCode();
                        
            String requete = "insert into Utilisateur value ('"+login+"','"+mdp+"','"+nom+"','"+prenom+"','Etudiant','"+nomUniversite+"','"+formation+"');";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }
    
    public void exporterPersonnel() {
        ArrayList<Personnel> personnels = this.universite.getLesPersonnels();
        
        for (Personnel personnel : personnels) {
            String login = personnel.getLogin();
            String mdp = personnel.getMdp();
            String nom = personnel.getNom();
            String prenom = personnel.getPrenom();
            String nomUniversite = this.universite.getNom();
            
            
            String requete = "insert into Utilisateur value ('"+login+"','"+mdp+"','"+nom+"','"+prenom+"','Personnel','"+nomUniversite+"',NULL);";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }
    
    public void exporterFormation() {
        ArrayList<Formation> formations = this.universite.getLesFormations();
        
        for (Formation formation : formations) {
            String nom = formation.getNom();
            String code = formation.getCode();
            String nomUniversite = this.universite.getNom();
            String salleCM = formation.getSalleCM().getNom();

            String salleTD = formation.getSalleTD().getNom();
              
            String requete = "insert into Formation value ('"+nom+"','"+code+"','"+nomUniversite+"','"+salleCM+"','"+salleTD+"');";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
                for (Module module : formation.getModules()) {
                   String nom2 = module.getNom();
                   String code2 = module.getCode();
                   int coeffTD = module.getCoefTD();
                   int coeffTP = module.getCoefTP();
                   int coeffCM = module.getCoefCM();
                   int coeffModule = module.getCoefModule();
                   String responsable = module.getResponsable().getLogin();
                   
                   String requete2 = "insert into Module value ('"+code2+"','"+nom2+"','"+coeffTP+"','"+coeffTD+"','"+coeffCM+"','"+coeffModule+"','"+responsable+"','"+code+"');";
                   gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }
    
    public void exporterSalle() {
        ArrayList<Salle> salles = this.universite.getLesSalles();
        
        for (Salle salle : salles) {
            String nom = salle.getNom();
            int capacite = salle.getCapacite();
              
            String requete = "insert into Salle value ('"+nom+"','"+capacite+"');";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }
    
    public void exporterProfesseur() {
         ArrayList<Professeur> professeurs = this.universite.getLesProfesseurs();
        
        for (Professeur professeur : professeurs) {
            String login = professeur.getLogin();
            String mdp = professeur.getMdp();
            String nom = professeur.getNom();
            String prenom = professeur.getPrenom();
            String nomUniversite = this.universite.getNom();
            
            
            String requete = "insert into Utilisateur value ('"+login+"','"+mdp+"','"+nom+"','"+prenom+"','Professeur','"+nomUniversite+"',NULL);";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public void exporterSeance() {
        ArrayList<Seance> seances = this.universite.getLesSeances();
        
        for (Seance seance : seances) {
            String type = seance.getType();
            String codeModule = seance.getCodeModule();
            Date date = seance.getDate();
            int heure = seance.getHeure();
            int duree = seance.getDuree();
                        
            String salle = seance.getSalle().getNom();
            

            String requete = "insert into Seance value ('"+type+"','"+date+"','"+heure+"','"+salle+"','"+codeModule+"','"+duree+"');";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }
    
    public void exporterResultats() {
        ArrayList<Resultat> resultats = this.universite.getLesResultats();
        
        for (Resultat resultat : resultats) {
            String login = resultat.getEtudiant().getLogin();
            String module = resultat.getModule().getCode();
            double noteCM = resultat.getNoteCM();
            double noteTD = resultat.getNoteTD();
            double noteTP = resultat.getNoteTP();
              
            String requete = "insert into Participe value ('"+login+"','"+module+"','"+noteCM+"','"+noteTD+"','"+noteTP+"');";
            try { 
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(requete);
            } catch (SQLException ex) {
                Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }      
    }

    
    // FONCTIONS GAEL
    public ArrayList<Seance> consulterEDTProfesseur(Date dateDebut, Date dateFin, Professeur prof) {
        ArrayList<Seance> seances = this.universite.getSeances(dateDebut, dateFin);
        ArrayList<Module> modules = this.universite.getModulesParProfesseur(prof);
        
        ArrayList<Seance> seancesValides = new ArrayList<Seance>();
                
        for(Seance s : seances){
            for(Module m : modules){
                if(m.getCode().equals(s.getCodeModule())){
                    seancesValides.add(s);
                }
            }
        }        
        return seancesValides;
    }
    
    public ArrayList<Seance> consulterEDTEtudiant(Date dateDebut, Date dateFin, Etudiant etud) {
        ArrayList<Seance> seances = this.universite.getSeances(dateDebut, dateFin);
        Formation f = (Formation) etud.getSuccessor();
        f.getCode();
        ArrayList<Module> modules = f.getModules();
        
        ArrayList<Seance> seancesValides = new ArrayList<Seance>();
                
        for(Seance s : seances){
            for(Module m : modules){
                if(m.getCode().equals(s.getCodeModule())){
                    seancesValides.add(s);
                }
            }
        }
        
        return seancesValides;
    }
    
    public void ajouterSeance(String type, Date date, int heure, String nomSalle, String codeModule, int duree){
        Salle s = universite.getSalle(nomSalle);
        Seance seance = new Seance(type, codeModule, date, heure, duree, s);
        universite.ajouterSeance(seance);
    }

    public boolean reserverSalle(Seance seance, Formation formation) {
        ArrayList<Seance> seances = universite.getSeancesJourHeure(seance.getDate(), seance.getHeure(), seance.getDuree());
        ArrayList<Salle> salles = universite.getLesSalles();
        ArrayList<Salle> sallesUtilisees = new ArrayList<Salle>();
        
        for(Seance sc : seances){
            if(sc.getSalle() != null)
                sallesUtilisees.add(sc.getSalle());
        }
        
        int nbEtudiants = formation.getNbEtudiants();
        
        int i = 0;
        Salle salle = null;
        
        while(salle == null & i < salles.size()){
            Salle s = salles.get(i);
            if(!sallesUtilisees.contains(s) & s.getCapacite() >= nbEtudiants){
                salle = s;
            }
            i++;
        }
        
        if(salle != null){
            seance.setSalle(salle);
            return true;
        }else{
            return false;
        }
    }
    
    void ajouterEtudiant(String nom, String prenom, String mdp, Formation formation) {
        this.universite.ajouterEtudiant(nom, prenom, mdp, formation);
    }

    void ajouterProfesseur(String nom, String prenom, String mdp) {
        this.universite.ajouterProfesseur(nom, prenom, mdp);
    }

    void ajouterPersonnel(String nom, String prenom, String mdp) {
        this.universite.ajouterPersonnel(nom, prenom, mdp);
    }
    
    public boolean ajouterModule(String nomModule, String loginResponsable) {
       return this.universite.ajouterModule(this.currentFormation, nomModule, loginResponsable);
    }
}
