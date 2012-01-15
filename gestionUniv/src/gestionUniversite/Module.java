/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.ArrayList;

/**
 *
 * @author gaelvarlet
 */
public class Module extends GroupeEtudiants {
    private String nom,code;
    private int coefTD, coefTP, coefCM, coefModule;
    private Professeur responsable;
    
    public Module(String nom, String code) {
        this.nom = nom;
        this.code = code;
        this.responsable = null;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Professeur getResponsable() {
        return responsable;
    }

    public void setResponsable(Professeur responsable) {
        this.responsable = responsable;
    }
    
    @Override
    public double getMoyenne() {
        ArrayList<Resultat> result = new ArrayList<Resultat>();
        Formation f = (Formation) this.getSuccessor();
        Universite u = (Universite) f.getSuccessor();

        for (Resultat r : u.getLesResultats()) {
            if (r.getModule().getCode().equals(this.code)){
                result.add(r);
            }
        }
        int coefficient = 0;
        double moyenne = 0;
        for (Resultat r : result) {
            double cm = r.getNoteCM();
            double td = r.getNoteTD();
            double tp = r.getNoteTP();
            
            int coeffCM = r.getModule().getCoefCM();
            int coeffTD = r.getModule().getCoefTD();
            int coeffTP = r.getModule().getCoefTP();
            double temp = (cm*coeffCM + td*coeffTD + tp*coeffTP) / (coeffCM+coeffTD+coeffTP);
            
            moyenne += (temp*r.getModule().getCoefModule());
            coefficient += r.getModule().getCoefModule();
        }
        moyenne /= coefficient;
        return moyenne;
    }

}
