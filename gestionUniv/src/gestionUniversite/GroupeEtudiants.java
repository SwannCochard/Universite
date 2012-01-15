package gestionUniversite;

/**
 *
 * @author gaelvarlet
 */
public abstract class GroupeEtudiants implements ComposanteFac{
    private ComposanteFac successor;
    
    @Override
    public double getMoyenne() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNbEtudiants() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ComposanteFac getSuccessor() {
        return successor;
    }

    public void setSuccessor(ComposanteFac successor) {
        this.successor = successor;
    }
}
