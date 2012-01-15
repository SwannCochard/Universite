package gestionUniversite;

import java.sql.Date;
import java.util.Enumeration;

/**
 *
 * @author gaelvarlet
 */
public class Seance {
    private String type;
    private String codeModule;
    private Date date;
    private int heure;
    private int duree;
    private Salle salle;

    public Seance(String type, String codeModule, Date date, int heure, int duree, Salle salle) {
        this.type = type;
        this.codeModule = codeModule;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.salle = salle;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
