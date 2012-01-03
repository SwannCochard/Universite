package gestionUniversite;

import java.sql.Statement;

/**
 *
 * @author gaelvarlet
 */
class ModeleApplication {
    private Universite universite;
    private Connexion c = gestionUniversite.Connexion.getInstance();
    private Statement stmt = c.getStatement();
    private Personne current;
    
    public ModeleApplication() {
        current = null;
    }
    
    public void setUniversite(Universite universite) {
        this.universite = universite;
    }
}
