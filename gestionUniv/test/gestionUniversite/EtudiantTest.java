/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gaelvarlet
 */
public class EtudiantTest {
    Formation f;
    Universite univ;
    Module mod ;
    
    public EtudiantTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        ModeleApplication ma = new ModeleApplication();
        univ = new Universite("UHP", ma);
        Salle s = new Salle("A1", 30);
        
        ArrayList<Salle> als = new ArrayList<Salle>();
        als.add(s);
        univ.setLesSalles(als);
        
        univ.ajouterFormation("Licence", "A1", "A1");
        f = univ.getLesFormations().get(0);
        
        univ.ajouterProfesseur("Galmiche", "Didier", "aaa");
        univ.ajouterModule(f, "module", "galm0001");
        
        
        
        mod = f.getModules().get(0);
        ma.setCoefficient(mod, 1, 1);
        ma.setCoefficient(mod, 1, 2);
        ma.setCoefficient(mod, 1, 3);
        ma.setCoefficient(mod, 1, 4);
        
    }
    
    @After
    public void tearDown() {
    }

//    /**
//     * Test of getSuccessor method, of class Etudiant.
//     */
//    @Test
//    public void testGetSuccessor() {
//        System.out.println("getSuccessor");
//        ModeleApplication ma = new ModeleApplication();
//        Universite univ = new Universite("UHP", ma);
//        Salle s = new Salle("A1", 30);
//        Formation f = new Formation("Licence", "L", s, s);
//        Etudiant instance = new Etudiant("login", "mdp", "nom", "prenom", univ);
//        
//        f.addEtudiant(instance);
//        
//        ComposanteFac expResult = f;
//        
//        ComposanteFac result = instance.getSuccessor();
//        assertEquals(expResult, result);
//    }
//    
//    @Test
//    public void testGetSuccessorNull() {
//        System.out.println("getSuccessor");
//        ModeleApplication ma = new ModeleApplication();
//        Universite univ = new Universite("UHP", ma);
//        Salle s = new Salle("A1", 30);
//        Formation f = new Formation("Licence", "L", s, s);
//        Etudiant instance = new Etudiant("login", "mdp", "nom", "prenom", univ);
//        
//        ComposanteFac result = instance.getSuccessor();
//        assertNull(result);
//    }

    /**
     * Test of getMoyenne method, of class Etudiant.
     */
    @Test
    public void testGetMoyenne() {
        System.out.println("getMoyenne");    
        Etudiant instance = new Etudiant("login", "mdp", "nom", "prenom", univ);
        f.addEtudiant(instance);
        Resultat res = new Resultat(instance, mod);
        res.setNoteCM(15);
        res.setNoteTD(16);
        res.setNoteTP(14);
        ArrayList<Resultat> listeRes = new ArrayList<Resultat>();
        listeRes.add(res);
        univ.setLesResultats(listeRes);
        double expResult = 15.0;
        double result = instance.getMoyenne();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getNbEtudiants method, of class Etudiant.
     */
    @Test
    public void testGetNbEtudiants() {
        System.out.println("getNbEtudiants");
        Etudiant instance = new Etudiant("login", "mdp", "nom", "prenom", univ);
        int expResult = 1;
        int result = instance.getNbEtudiants();
        assertEquals(expResult, result);
    }
}
