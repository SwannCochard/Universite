/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionUniversite;

/**
 *
 * @author gaelvarlet
 */
public class Personnel extends Personne{
    public Personnel(String motDePasse, String nom, String prenom, Universite universite) {
        super(motDePasse, nom, prenom, universite);
    }

    public Personnel(String login, String motDePasse, String nom, String prenom, Universite universite) {
        super(login, motDePasse, nom, prenom, universite);
    }
}
