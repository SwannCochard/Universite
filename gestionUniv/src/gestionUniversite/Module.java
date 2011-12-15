/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

/**
 *
 * @author Swann
 */
public class Module {
    
    private String nom,code;
    private int coefTD, coefTP, coefCM, coefModule;

    public Module(String nom) {
        this.nom = nom;
    }
    
    public void calculerMoyenne() {
        
    }

    public int getCoefCM() {
        return coefCM;
    }

    public void setCoefCM(int coefCM) {
        this.coefCM = coefCM;
    }

    public int getCoefModule() {
        return coefModule;
    }

    public void setCoefModule(int coefModule) {
        this.coefModule = coefModule;
    }

    public int getCoefTD() {
        return coefTD;
    }

    public void setCoefTD(int coefTD) {
        this.coefTD = coefTD;
    }

    public int getCoefTP() {
        return coefTP;
    }

    public void setCoefTP(int coefTP) {
        this.coefTP = coefTP;
    }
    
    public void save() {
        
    }
    
}
