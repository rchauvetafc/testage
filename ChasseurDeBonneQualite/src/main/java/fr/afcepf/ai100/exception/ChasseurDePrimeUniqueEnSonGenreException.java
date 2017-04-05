package fr.afcepf.ai100.exception;

/**
 * Exception levée si on essaie d'ajouter un ChasseurDePrime dont le nom est
 * déja dans la base.
 *
 * @author Stagiaire
 *
 */
public class ChasseurDePrimeUniqueEnSonGenreException extends Exception {

    /**
     * Constructeur.
     *
     * @param pMessage
     *            le message d'erreur.
     */
    public ChasseurDePrimeUniqueEnSonGenreException(String pMessage) {
        super(pMessage);
    }

}
