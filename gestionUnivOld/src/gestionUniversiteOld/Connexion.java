/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversiteOld;

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
    private Statement stmt, stmt2;
    private Connection con;
    String url = "jdbc:mysql://localhost:3306/gestionUniversite";
    
    private Connexion(){
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            stmt2 = con.createStatement();
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
    public Statement getStatement2() {
        return this.stmt2; 
    }
}
