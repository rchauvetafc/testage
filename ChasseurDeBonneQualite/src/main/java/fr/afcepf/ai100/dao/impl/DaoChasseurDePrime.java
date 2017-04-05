package fr.afcepf.ai100.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.afcepf.ai100.dao.api.IDaoChasseurDePrime;
import fr.afcepf.ai100.entity.ChasseurDePrime;
import fr.afcepf.ai100.jdbc.Ai100DataSource;

/**
 * Implémentation JDBC de l'IDaoChasseurDePrime.
 *
 * @author Stagiaire
 *
 */
public class DaoChasseurDePrime implements IDaoChasseurDePrime {
    /**
     * Position de l'attribut nom dans la requete d'ajout.
     */
    private static final int POSITION_NOM_AJOUT = 1;
    /**
     * Position de l'attribut vaisseau dans la requete d'ajout.
     */
    private static final int POSITION_VAISSEAU_AJOUT = 3;
    /**
     * Position de l'attribut tarif dans la requete d'ajout.
     */
    private static final int POSITION_TARIF_AJOUT = 2;
    /**
     * Position de l'attribut mort dans la requete d'ajout.
     */
    private static final int POSITION_MORT_AJOUT = 4;
    /**
     * Nom de la table chasseur_de_prime dans la bdd.
     */
    private static final String CHASSEUR_DE_PRIME_TABLE = "chasseur_de_prime";
    /**
     * Nom de la colonne nom dans la table.
     */
    private static final String COLONNE_NOM = "nom";
    /**
     * Nom de la colonne tarif dans la table.
     */
    private static final String COLONNE_TARIF = "tarif";
    /**
     * Nom de la colonne vaisseau dans la table.
     */
    private static final String COLONNE_VAISSEAU = "vaisseau";
    /**
     * Nom de la colonne nom dans la table.
     */
    private static final String COLONNE_MORT = "mort";
    /**
     * Nom de la colonne id_chasseur_de_prime dans la table.
     */
    private static final String COLONNE_ID_CHASSEUR_DE_PRIME =
            "id_chasseur_de_prime";

    /**
     * Nom de la colonne temporaire pour les requete de count.
     */
    private static final String COUNT = "colonneCount";

    /**
     * DataSource pour la connection à la bdd.
     */
    private DataSource dataSource = new Ai100DataSource();
    /**
     * Requete SQL d'ajout de ChasseurDePrime.
     */
    private String requeteAjout = "INSERT INTO " + CHASSEUR_DE_PRIME_TABLE
            + " ( " + COLONNE_NOM + ", " + COLONNE_TARIF + ", "
            + COLONNE_VAISSEAU + ", " + COLONNE_MORT + ") VALUES ( ?, ?, ?, ?)";
    /**
     * Requete de recherche de ChasseurDePrime par nom.
     */
    private String requeteRechercheParNom = "SELECT * FROM "
            + CHASSEUR_DE_PRIME_TABLE + " WHERE " + COLONNE_NOM + " like ?";
    /**
     * Requete de recherche de ChasseurDePrime par tarif.
     */
    private String requeteRechercheParTarif = "SELECT * FROM "
            + CHASSEUR_DE_PRIME_TABLE + " WHERE " + COLONNE_TARIF + " > ? AND "
            + COLONNE_TARIF + " < ?";
    /**
     * Requete de count des nom présent de chasseur de prime.
     */
    private String requeteCountNom = "SELECT COUNT(*) AS " + COUNT + " FROM "
            + CHASSEUR_DE_PRIME_TABLE + " WHERE " + COLONNE_NOM + " like ?";

    /**
     * Requete de suppression d'un ChasseurDePrime dans la bdd.
     */
    private static String requeteSuppression = "DELETE FROM "
            + CHASSEUR_DE_PRIME_TABLE + " WHERE " + COLONNE_ID_CHASSEUR_DE_PRIME
            + " = ?";

    /**
     * Getter de la DataSource.
     *
     * @return la DataSource.
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Setter de la DataSource.
     *
     * @param pDataSource
     *            la nouvelle DataSource.
     */
    public void setDataSource(DataSource pDataSource) {
        dataSource = pDataSource;
    }

    @Override
    public ChasseurDePrime ajouterChasseurDePrime(
            ChasseurDePrime pChasseurDePrime) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(requeteAjout,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(POSITION_NOM_AJOUT, pChasseurDePrime.getNom());
            pstmt.setString(POSITION_VAISSEAU_AJOUT,
                    pChasseurDePrime.getVaisseau());
            pstmt.setFloat(POSITION_TARIF_AJOUT, pChasseurDePrime.getTarif());
            pstmt.setBoolean(POSITION_MORT_AJOUT, pChasseurDePrime.isMort());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            pChasseurDePrime.setIdChasseurDePrime(rs.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pChasseurDePrime;
    }

    @Override
    public void supprimerChasseurDePrime(int pIdChasseurDePrime) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteSuppression);
            pstmt.setInt(1, pIdChasseurDePrime);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<ChasseurDePrime> rechercherChasseurDePrimeParNom(String pNom) {
        List<ChasseurDePrime> listeChasseurDePrimes = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteRechercheParNom);
            pstmt.setString(1, "%" + pNom + "%");

            ResultSet rs = pstmt.executeQuery();
            listeChasseurDePrimes = transformeResultSetEnListe(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listeChasseurDePrimes;
    }

    /**
     * Parcours le resultSet pour créer une liste de ChasseurDePrime.
     *
     * @param pResultSet
     *            le ResultSet.
     * @throws SQLException
     *             si jamais problème de lecture du ResultSet.
     * @return la liste des ChasseurDePrime remontés de la bdd
     */
    private List<ChasseurDePrime> transformeResultSetEnListe(
            ResultSet pResultSet) throws SQLException {
        List<ChasseurDePrime> liste = new ArrayList<>();
        while (pResultSet.next()) {
            ChasseurDePrime chasseurDePrime = new ChasseurDePrime();
            chasseurDePrime.setIdChasseurDePrime(
                    pResultSet.getInt(COLONNE_ID_CHASSEUR_DE_PRIME));
            chasseurDePrime.setMort(pResultSet.getBoolean(COLONNE_MORT));
            chasseurDePrime.setNom(pResultSet.getString(COLONNE_NOM));
            chasseurDePrime.setTarif(pResultSet.getFloat(COLONNE_TARIF));
            chasseurDePrime.setVaisseau(pResultSet.getString(COLONNE_VAISSEAU));
            liste.add(chasseurDePrime);
        }
        return liste;
    }

    @Override
    public List<ChasseurDePrime> rechercherChasseurDePrimeParTarif(
            float pTarifBas, float pTarifHaut) {
        List<ChasseurDePrime> listeChasseurDePrimes = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteRechercheParTarif);
            pstmt.setFloat(1, pTarifBas);
            pstmt.setFloat(2, pTarifHaut);

            ResultSet rs = pstmt.executeQuery();
            listeChasseurDePrimes = transformeResultSetEnListe(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listeChasseurDePrimes;
    }

    @Override
    public boolean nomDejaExistant(String pNom) {
        Connection connection = null;
        boolean existe = false;
        int count = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteCountNom);
            pstmt.setString(1, pNom);
            ResultSet res = pstmt.executeQuery();
            count = res.getInt(COUNT);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (count != 0) {
            existe = true;
        }
        return existe;
    }

}
