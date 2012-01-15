package gestionUniversite;

import java.sql.Date;
import java.util.Enumeration;

/**
 *
 * @author gaelvarlet
 */
public class Seance implements Comparable{
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

    @Override
    public String toString() {
        return type + " de " + codeModule + " du " + date + " de " + heure + "h à " + (heure+duree) + "h";
    }

    @Override
    public int compareTo(Object t) {
        Seance d = (Seance) t;
        int i = 0;
        
        if(d.getDate().before(this.getDate())){
            i = 1;
        }
        if(d.getDate().after(this.getDate())){
            i = -1;
        }
        if(d.getDate().equals(this.getDate())){
            if(d.getHeure() < this.getHeure()){
                i = 1;
            }else{
                i = 0;
            }
        }
        return i;
    }
}
