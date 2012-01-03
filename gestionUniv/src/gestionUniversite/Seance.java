package gestionUniversite;

import java.util.Enumeration;

/**
 *
 * @author gaelvarlet
 */
public class Seance {
    private Enumeration type;
    private String codeModule;
    private String jour;
    private int heure;
    private int duree;
    private Salle salle;

    public String getCodeModule() {
        return codeModule;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Enumeration getType() {
        return type;
    }

    public void setType(Enumeration type) {
        this.type = type;
    }
}
