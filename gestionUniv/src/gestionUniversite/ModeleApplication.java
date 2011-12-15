/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public ModeleApplication() {
        current = null;
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
                    current = new Etudiant(id,login,mdp,nom,prenom);
                }
                // Ajouter les deux autres types
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
    
    
    void ajouterPersonne(String nom, String prenom, String mdp, String type) {
        //String login = calculerLogin(nom, prenom);
        
        String login = this.calculerLogin(nom);
        String req = "insert into Utilisateur (login,mdp,nom,prenom,type) values ('"+login+"','"+mdp+"','"+nom+"','"+prenom+"', '"+type+"')";
        
        ResultSet res;
        try {
            gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String calculerLogin(String nom, String prenom) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String calculerLogin(String nom) {
        int borneSup = 4;
        if (nom.length() < 4) {
            borneSup = nom.length();
        }
        String login = nom.substring(0,borneSup); 
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

    void ajouterFormation(String nom, String code) {
        String login = this.calculerLogin(nom);
        String req = "insert into Formation (nom,code) values ('"+nom+"','"+code+"')";
        
        ResultSet res;
        try {
            gestionUniversite.Connexion.getInstance().getStatement().execute(req);
            //res = gestionUniversite.Connexion.getInstance().getStatement().executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ModeleApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    
    
}
