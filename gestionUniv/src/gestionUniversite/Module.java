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
    private Professeur responsable;
    public Module(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Module(String nom, String code, int coefTD, int coefTP, int coefCM, int coefModule, Professeur responsable) {
        this.nom = nom;
        this.code = code;
        this.coefTD = coefTD;
        this.coefTP = coefTP;
        this.coefCM = coefCM;
        this.coefModule = coefModule;
        this.responsable = responsable;
    }

    public void setResponsable(Professeur responsable) {
        this.responsable = responsable;
    }

    public Professeur getResponsable() {
        return responsable;
    }
    
    public void calculerMoyenne() {
        
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
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

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}
