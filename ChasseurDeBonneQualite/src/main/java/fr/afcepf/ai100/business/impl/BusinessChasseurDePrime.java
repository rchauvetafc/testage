package fr.afcepf.ai100.business.impl;

import java.util.List;

import fr.afcepf.ai100.business.api.IBusinessChasseurDePrime;
import fr.afcepf.ai100.dao.api.IDaoChasseurDePrime;
import fr.afcepf.ai100.dao.impl.DaoChasseurDePrime;
import fr.afcepf.ai100.entity.ChasseurDePrime;
import fr.afcepf.ai100.exception.ChasseurDePrimeUniqueEnSonGenreException;

/**
 * Implémentation des règles de gestion des ChasseurDePrime.
 *
 * @author Stagiaire
 *
 */
public class BusinessChasseurDePrime implements IBusinessChasseurDePrime {

    /**
     * Couche d'acces aux données de ChasseurDePrime.
     */
    private IDaoChasseurDePrime daoChasseurDePrime = new DaoChasseurDePrime();

    @Override
    public ChasseurDePrime ajouterChasseurDePrime(
            ChasseurDePrime pChasseurDePrime)
            throws ChasseurDePrimeUniqueEnSonGenreException {
        boolean existe = daoChasseurDePrime
                .nomDejaExistant(pChasseurDePrime.getNom());
        if (existe) {
            throw new ChasseurDePrimeUniqueEnSonGenreException(
                    "un chasseur de prime est unique");
        }
        pChasseurDePrime = daoChasseurDePrime
                .ajouterChasseurDePrime(pChasseurDePrime);
        return pChasseurDePrime;
    }

    @Override
    public void supprimerChasseurDePrime(int pIdChasseurDePrime) {
        daoChasseurDePrime.supprimerChasseurDePrime(pIdChasseurDePrime);

    }

    @Override
    public List<ChasseurDePrime> rechercherChasseurDePrimeParNom(String pNom) {

        return daoChasseurDePrime.rechercherChasseurDePrimeParNom(pNom);
    }

    @Override
    public List<ChasseurDePrime> rechercherChasseurDePrimeParTarif(
            float pTarifBas, float pTarifHaut) {
        return daoChasseurDePrime.rechercherChasseurDePrimeParTarif(pTarifBas,
                pTarifHaut);
    }

}
