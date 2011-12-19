/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

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
                    current = new Etudiant(id,login,mdp,nom,prenom, this.universite);
                }
                if (type.equals("professeur")) {
                    current = new Professeur(login,mdp,nom,prenom, this.universite);
                }
                if (type.equals("personnel")) {
                    current = new Personnel(id,login,mdp,nom,prenom,this.universite);
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
    
    
    public String ajouterPersonne(Personne personne) {
        //String login = calculerLogin(nom, prenom);
        
        String nom = personne.getNom();
        String prenom = personne.getPrenom();
        String login = personne.getLogin();
        String mdp = personne.getMdp();
        String type = personne.getClass().toString().toLowerCase();
        type = type.substring(type.lastIndexOf(".")+1);
        //this.universite.ajouterPersonne(nom,prenom,login,mdp,type);
        String req = "insert into Utilisateur (login,mdp,nom,prenom,type) values ('"+login+"','"+mdp+"','"+nom+"','"+prenom+"', '"+type+"')";
        
        ResultSet res;
        try {
            gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return login;
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
                req = "insert into Formation (nom,code) values ('"+nom+"','"+code+"')";
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
    
//    public void ajouterEtudiant(String nom, String prenom, String mdp) {
//        String login = this.calculerLogin(nom);
//        ((Personnel) current).ajouterEtudiant(login, nom, prenom, mdp);
//    }
    
    public void ajouterFormation(String nom) {
        ((Personnel) current).ajouterFormation(nom);
    }
    
    public void mettreEnPlaceModule() {
        
    }
    
    public void mettreEnPLaceFormation() {
        
    }

    public Personne getCurrent() {
        return current;
    }

    public void inscrireEtudiant(String login, String codeFormation) {
        ((Personnel) current).inscrireEtudiant(login, codeFormation);
    }
    public void inscrireEtudiant(Etudiant etudiant, Formation formation) {
        String loginEtu = etudiant.getLogin();
        String codeFormation = formation.getCode();
        String req = "insert into Participe (login,codeFomration) values ('"+loginEtu+"','"+codeFormation+"')";
        
        ResultSet res;
        try {
            gestionUniversite.Connexion.getInstance().getStatement().execute(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ajouterModule(Module module, Formation formation) {
        System.out.println("Ajouter module "+module.getNom());
        String nom = module.getNom();
        String code = module.getCode();
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
                req = "insert into Module (nom,code,responsable) values ('"+nom+"','"+code+"','"+responsable+"')";
                gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            }
            else {
                req = "update Module set nom = '"+nom+"' where code like '"+code+"'";
                gestionUniversite.Connexion.getInstance().getStatement().executeUpdate(req);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}
