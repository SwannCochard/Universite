/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swann
 */
public class Connexion {
    private static Connexion co = new Connexion();
    String user = "root";
    String password = null;   
    private Statement stmt;
    private Connection con;
    String url = "jdbc:mysql://localhost:3306/gestionUniversite";
    private Connexion(){
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("connexion réussie");
            stmt = con.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connexion getInstance() {
        return co; 
    }
    

    public Connection getConnexion() {
        return con;
    }
    
    public Statement getStatement() {
        return this.stmt; 
    }
}