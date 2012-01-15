package gestionUniversite;

/**
 *
 * @author gaelvarlet
 */
public class Resultat {
    private Etudiant etudiant;
    private Module module;
    private double noteCM;
    private double noteTD;
    private double noteTP;

    public Resultat(Etudiant etudiant, Module module) {
        this.etudiant = etudiant;
        this.module = module;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public double getNoteCM() {
        return noteCM;
    }

    public void setNoteCM(double noteCM) {
        this.noteCM = noteCM;
    }

    public double getNoteTD() {
        return noteTD;
    }

    public void setNoteTD(double noteTD) {
        this.noteTD = noteTD;
    }

    public double getNoteTP() {
        return noteTP;
    }

    public void setNoteTP(double noteTP) {
        this.noteTP = noteTP;
    }
}
