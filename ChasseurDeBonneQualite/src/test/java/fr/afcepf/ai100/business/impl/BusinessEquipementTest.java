package fr.afcepf.ai100.business.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import fr.afcepf.ai100.business.api.IBusinessEquipement;
import fr.afcepf.ai100.dao.api.IDaoEquipement;
import fr.afcepf.ai100.entity.ChasseurDePrime;
import fr.afcepf.ai100.entity.Equipement;
import fr.afcepf.ai100.exception.AttributionException;
import fr.afcepf.ai100.exception.EquipementMaxException;

/**
 * Classe de test de BusinessEquipement.
 *
 * @author Stagiaire
 *
 */
public class BusinessEquipementTest {

    /**
     * Id de l'Equipement pour les tests.
     */
    private static final int ID_EQUIPEMENT = 2;
    /**
     * Id du ChasseurDePrime de test.
     */
    private static final int ID_CHASSEUR_DE_PRIME = 42;
    /**
     * Classe que l'on teste.
     */
    private IBusinessEquipement businessEquipement = new BusinessEquipement();
    /**
     * Equipement utilisé pour les tests.
     */
    private Equipement equipement;
    /**
     * ChasseurDePrime utilisé pour les tests.
     */
    private ChasseurDePrime chasseurDePrime;

    /**
     * Méthode de réinitialisation de Equipement et Chasseur de prime pour les
     * tests.
     */
    @Before
    public void reinitialisation() {
        equipement = new Equipement(ID_EQUIPEMENT, "Titi");
        chasseurDePrime = new ChasseurDePrime();
        chasseurDePrime.setIdChasseurDePrime(ID_CHASSEUR_DE_PRIME);
    }

    /**
     * Test nominal de.
     * {@link BusinessEquipement#attribuerEquipement (Equipement,
     *  ChasseurDePrime)}
     *
     * @throws AttributionException
     *             ne doit pas être levée.
     * @throws EquipementMaxException
     *             ne doit pas être levée.
     */
    @Test
    public void attribuerEquipementNominal()
            throws EquipementMaxException, AttributionException {

        IDaoEquipement daoEquipement = new DaoEquipementNominal();
        ((BusinessEquipement) businessEquipement)
                .setDaoEquipement(daoEquipement);

        Equipement equipementRetour = businessEquipement
                .attribuerEquipement(equipement, chasseurDePrime);
        assertEquals(equipementRetour.getIdChasseurDePrime(),
                ID_CHASSEUR_DE_PRIME);
    }

    /**
     * Test en echec. le nombre d'Equipement porte par le ChasseurDePrime est au
     * maximum. L'Exception EquipementMaxException doit être levée.
     *
     * @throws AttributionException
     *             : ne doit pas etre jetée ici.
     * @throws EquipementMaxException
     *             : doit être jetée ici.
     */
    @Test(expected = EquipementMaxException.class)
    public void attribuerEquipementEchecGrandNb()
            throws EquipementMaxException, AttributionException {

        IDaoEquipement daoEquipementMock = Mockito.mock(IDaoEquipement.class);

        Mockito.when(
                daoEquipementMock.rechercherNbEquipementParIdChasseurDePrime(
                        ID_CHASSEUR_DE_PRIME))
                .thenReturn(BusinessEquipement.NOMBRE_EQUIPEMENT_MAX);
        Mockito.when(
                daoEquipementMock.rechercherNbEquipementParIdChasseurDePrime(2))
                .thenReturn(1);

        ((BusinessEquipement) businessEquipement)
                .setDaoEquipement(daoEquipementMock);
        businessEquipement.attribuerEquipement(equipement, chasseurDePrime);
    }

    /**
     * Dao pour le test nominal d'attribution.
     *
     * @author Stagiaire
     *
     */
    private class DaoEquipementNominal implements IDaoEquipement {

        @Override
        public Equipement ajouterEquipement(Equipement pEquipement) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void supprimerEquipement(int pIdEquipement) {
            // TODO Auto-generated method stub

        }

        @Override
        public List<Equipement> rechercherEquipement(String pNom) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean attribuerEquipement(int pIdEquipement,
                int pIdChasseurDePrime) {

            return true;
        }

        @Override
        public int rechercherNbEquipementParIdChasseurDePrime(
                int pIdChasseurDePrime) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public List<Equipement> rechercherEquipementParIdChasseurDePrime(
                int pIdChasseurDePrime) {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
