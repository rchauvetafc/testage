package fr.afcepf.ai100.entity;

/**
 * Classe entité correspondant à la table chasseur_de_prime.
 *
 * @author Stagiaire
 *
 */
public class ChasseurDePrime {

    /**
     * Id du chasseur de prime.
     */
    private int idChasseurDePrime;
    /**
     * nom du chasseur de prime.
     */
    private String nom;
    /**
     * tarif de recrutement du chasseur de prime.
     */
    private float tarif;
    /**
     * nom du vaisseau du chasseur de prime.
     */
    private String vaisseau;
    /**
     * Indique si le chasseur de prime est mort.
     */
    private boolean mort;

    /**
     * Getteur de l'id.
     *
     * @return l'id du chasseur de prime.
     */
    public int getIdChasseurDePrime() {
        return idChasseurDePrime;
    }

    /**
     * Setter.
     *
     * @param pIdChasseurDePrime
     *            : id du chasseur de prime.
     */
    public void setIdChasseurDePrime(int pIdChasseurDePrime) {
        idChasseurDePrime = pIdChasseurDePrime;
    }

    /**
     * Getteur du nom du chasseur de prime.
     *
     * @return le nom du chasseur de prime.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom du chasseur de prime.
     *
     * @param pNom
     *            nouveau nom du chasseur de prime.
     */
    public void setNom(String pNom) {
        nom = pNom;
    }

    /**
     * getter du tarif de recutement du chasseur de prime.
     *
     * @return le tarif du chasseur de prime.
     */
    public float getTarif() {
        return tarif;
    }

    /**
     * Setter du tarif du chasseur de prime.
     *
     * @param pTarif
     *            nouveau tarif du chasseur de prime.
     */
    public void setTarif(float pTarif) {
        tarif = pTarif;
    }

    /**
     * Getteur du nom du vaisseau.
     *
     * @return le nom du vaisseau.
     */
    public String getVaisseau() {
        return vaisseau;
    }

    /**
     * Setter du nom du vaisseau.
     *
     * @param pVaisseau
     *            nouveau nom du vaisseau.
     */
    public void setVaisseau(String pVaisseau) {
        vaisseau = pVaisseau;
    }

    /**
     * Indique si le chasseur de prime est mort.
     *
     * @return vrai si le chasseur de prime est mort, faux si le chasseur de
     *         prime est vivant.
     */
    public boolean isMort() {
        return mort;
    }

    /**
     * Setter du boolean mort du chasseur de prime.
     * @param pMort : vrai si le chasseur est vivant, faux s'il est mort.
     */
    public void setMort(boolean pMort) {
        mort = pMort;
    }

}
