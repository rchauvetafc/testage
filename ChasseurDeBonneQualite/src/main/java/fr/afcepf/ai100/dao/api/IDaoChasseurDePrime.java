package fr.afcepf.ai100.dao.api;

import java.util.List;

import fr.afcepf.ai100.entity.ChasseurDePrime;

/**
 * Interface pour la d�finition des m�thodes de CRUD de la table Chasseur de
 * prime.
 *
 * @author Stagiaire
 *
 */
public interface IDaoChasseurDePrime {

    /**
     * Ajoute un chasseur de prime dans la bdd.
     *
     * @param pChasseurDePrime
     *            le chasseur de prime � ajouter.
     * @return le chasseur de prime avec son id g�n�r�.
     */
    ChasseurDePrime ajouterChasseurDePrime(ChasseurDePrime pChasseurDePrime);

    /**
     * Supprime le chasseur de prime d�sign� par son id de la bdd.
     *
     * @param pIdChasseurDePrime
     *            l'id du chasseur de prime � supprimer.
     */
    void supprimerChasseurDePrime(int pIdChasseurDePrime);

    /**
     * Recherche des chasseurs de prime par nom semblable � celui fourni.
     *
     * @param pNom
     *            : le nom de r�f�rence.
     * @return la liste des chasseurs de prime dont le nom match pNom.
     */
    List<ChasseurDePrime> rechercherChasseurDePrimeParNom(String pNom);

    /**
     * Recherche des chasseurs de prime dont le tarif est compris entre le tarif
     * bas et le tarif haut.
     *
     * @param pTarifBas
     *            le tarif minimum de la recherche.
     * @param pTarifHaut
     *            le tarif maximum de la recherche.
     * @return la liste des chasseurs de prime dont le tarif est compris entre
     *         pTarifHaut et pTarifBas.
     */
    List<ChasseurDePrime> rechercherChasseurDePrimeParTarif(float pTarifBas,
            float pTarifHaut);

    /**
     * Regarde si un ChasseurDePrime avec le nom demand� existe d�ja dans la
     * bdd.
     *
     * @param pNom
     *            le nom � rechercher.
     * @return vrai si le nom est d�ja pr�sent, faux autrement.
     */
    boolean nomDejaExistant(String pNom);
}
