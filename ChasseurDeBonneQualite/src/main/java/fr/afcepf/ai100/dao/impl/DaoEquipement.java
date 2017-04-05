package fr.afcepf.ai100.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import fr.afcepf.ai100.dao.api.IDaoEquipement;
import fr.afcepf.ai100.entity.Equipement;
import fr.afcepf.ai100.jdbc.Ai100DataSource;

/**
 * Implémentation de l'interface IDaoEquipement.
 *
 * @author Stagiaire
 *
 */
public class DaoEquipement implements IDaoEquipement {

    /**
     * DataSource pour se connecter à la base.
     */
    private DataSource dataSource = new Ai100DataSource();

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
     *            : la nouvelle DataSource.
     */
    public void setDataSource(DataSource pDataSource) {
        dataSource = pDataSource;
    }

    /**
     * Nom de la table dans la bdd.
     */
    private static final String EQUIPEMENT_TABLE = "equipement";
    /**
     * Nom de la colonne nom.
     */
    private static final String COLONNE_NOM = "nom";
    /**
     * Nom de la colonne id_Equipement.
     */
    private static final String COLONNE_ID_EQUIPEMENT = "id_equipement";
    /**
     * nom de la colonne de count.
     */
    private static final String COUNT = "countColonne";

    /**
     * Nom de la colonne id_chasseur_de_prime.
     */
    private static final String COLONNE_ID_CHASSEUR_DE_PRIME =
            "id_chasseur_de_prime";

    /**
     * Requete de recherche des Equipement d'un ChasseurDePrime par
     * idChasseurDePrime.
     */
    private static String requeteRechercheParIdChasseurDePrime = "SELECT "
            + COLONNE_ID_EQUIPEMENT + " ," + COLONNE_NOM + ", "
            + COLONNE_ID_CHASSEUR_DE_PRIME + " FROM equipement WHERE "
            + COLONNE_ID_CHASSEUR_DE_PRIME + " = ?";

    /**
     * Requete pour compter le nombre d'équipements d'un chasseur de prime.
     */
    private static String requeteCount = "SELECT COUNT(*) as " + COUNT
            + " FROM " + EQUIPEMENT_TABLE + " WHERE "
            + COLONNE_ID_CHASSEUR_DE_PRIME + " = ?";

    /**
     * Requete d'ajout d'un équipement.
     */
    private static String requeteAjout = "INSERT INTO " + EQUIPEMENT_TABLE
            + " (" + COLONNE_NOM + ") VALUES (?)";

    /**
     * Requete de suppression d'un équipement dans la bdd.
     */
    private static String requeteSuppression = "DELETE FROM " + EQUIPEMENT_TABLE
            + " WHERE " + COLONNE_ID_EQUIPEMENT + " = ?";
    /**
     * Requete de recherche d'équipement par nom.
     */
    private static String requeteRecherche = "SELECT " + COLONNE_ID_EQUIPEMENT
            + " ," + COLONNE_NOM + ", " + COLONNE_ID_CHASSEUR_DE_PRIME
            + " FROM equipement WHERE " + COLONNE_NOM + "like ?";

    /**
     * Requete pour l'attribution d'un équipement à un chasseur de prime.
     */
    private static String requeteUpdate = "UPDATE " + EQUIPEMENT_TABLE + "set "
            + COLONNE_ID_CHASSEUR_DE_PRIME + " = ? " + "WHERE "
            + COLONNE_ID_EQUIPEMENT + " = ? ";

    @Override
    public Equipement ajouterEquipement(Equipement pEquipement) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(requeteAjout,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pEquipement.getNom());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            pEquipement.setIdEquipement(rs.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pEquipement;
    }

    @Override
    public void supprimerEquipement(int pIdEquipement) {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteSuppression);
            pstmt.setInt(1, pIdEquipement);
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
    public List<Equipement> rechercherEquipement(String pNom) {
        List<Equipement> listeEquipements = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteRecherche);
            pstmt.setString(1, "%" + pNom + "%");

            ResultSet rs = pstmt.executeQuery();
            listeEquipements = transformeResultSetEnListeEquipements(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listeEquipements;
    }

    @Override
    public boolean attribuerEquipement(int pIdEquipement,
            int pIdChasseurDePrime) {
        Connection connection = null;
        boolean reussi = false;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteUpdate);
            pstmt.setInt(1, pIdChasseurDePrime);
            pstmt.setInt(2, pIdEquipement);
            int nb = pstmt.executeUpdate();

            if (nb == 1) {
                reussi = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reussi;
    }

    @Override
    public int rechercherNbEquipementParIdChasseurDePrime(
            int pIdChasseurDePrime) {
        Connection connection = null;
        int count = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(requeteCount);
            pstmt.setInt(1, pIdChasseurDePrime);
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

        return count;
    }

    @Override
    public List<Equipement> rechercherEquipementParIdChasseurDePrime(
            int pIdChasseurDePrime) {
        List<Equipement> listeEquipements = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstmt = connection
                    .prepareStatement(requeteRechercheParIdChasseurDePrime);
            pstmt.setInt(1, pIdChasseurDePrime);
            ResultSet rs = pstmt.executeQuery();
            listeEquipements = transformeResultSetEnListeEquipements(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listeEquipements;
    }

    /**
     * Transforme un ResultSet en une liste d'Equipement.
     *
     * @param pResultSet
     *            le resultSet source.
     * @return la liste d'Equipement.
     * @throws SQLException
     *             si jamais la lecture du ResultSet est en erreur.
     */
    private List<Equipement> transformeResultSetEnListeEquipements(
            ResultSet pResultSet) throws SQLException {
        List<Equipement> listeEquipements = new ArrayList<>();
        while (pResultSet.next()) {
            int idEquipement = pResultSet.getInt(COLONNE_ID_EQUIPEMENT);
            int idChasseurDePrime = pResultSet
                    .getInt(COLONNE_ID_CHASSEUR_DE_PRIME);
            String nom = pResultSet.getString(COLONNE_NOM);
            listeEquipements
                    .add(new Equipement(idEquipement, nom, idChasseurDePrime));
        }
        return listeEquipements;
    }

}
