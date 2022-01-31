package net.prison.foggies.core.mines;

import net.prison.foggies.core.utils.Database;
import net.prison.foggies.core.utils.SerializeUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MineDatabase extends Database {

    public MineDatabase(String host, String port, String database, String userName, String password) {
        super(host, port, database, userName, password);
        this.createTable();
    }

    @Override
    public void createTable() {

        executeQuery("CREATE TABLE IF NOT EXISTS Mines" +
                "(" +
                "UUID VARCHAR(37), " +
                "BLOCKS_MINED BIGINT, " +
                "LEVEL BIGINT, " +
                "EXPERIENCE DOUBLE, " +
                "COMPOSITION TEXT, " +
                "REGION TEXT, " +
                "IS_PUBLIC BOOLEAN, " +
                "PRIMARY KEY (UUID)" +
                ")");

        executeQuery("CREATE TABLE IF NOT EXISTS LastLocation" +
                "(" +
                "IDENTIFIER VARCHAR(15), " +
                "LAST_LOCATION INT, " +
                "PRIMARY KEY (IDENTIFIER)" +
                ")");
    }

    public synchronized Map<UUID, PersonalMine> loadAllPMines() {

        HashMap<UUID, PersonalMine> pMineMap = new HashMap<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Mines")) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                pMineMap.put(uuid, new PersonalMine(
                                uuid,
                                resultSet.getLong("BLOCKS_MINED"),
                        resultSet.getLong("LEVEL"),
                        resultSet.getDouble("EXPERIENCE"),
                        (MineBlock) SerializeUtils.fromString(resultSet.getString("COMPOSITION")),
                        (MineRegion) SerializeUtils.fromString(resultSet.getString("REGION")),
                        resultSet.getBoolean("IS_PUBLIC")
                        )
                );
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return pMineMap;
    }

    public void saveMine(PersonalMine pMine)  {
        try {
            executeQuery("UPDATE Mines SET " +
                            "BLOCKS_MINED=?,LEVEL=?," +
                            "EXPERIENCE=?,COMPOSITION=?,REGION=?,IS_PUBLIC=? WHERE UUID=?",
                    pMine.getBlocksMined(), pMine.getMineLevel(),
                    pMine.getMineExperience(), SerializeUtils.toString(pMine.getMineBlock()),
                    SerializeUtils.toString(pMine.getMineRegion()), pMine.isPublic(), pMine.getMineOwner().toString()
            );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void insertMine(PersonalMine pMine) {
        try {
            executeQuery("INSERT IGNORE INTO Mines VALUES(?,?,?,?,?,?,?)",
                    pMine.getMineOwner().toString(), pMine.getBlocksMined(), pMine.getMineLevel(),
                    pMine.getMineExperience(), SerializeUtils.toString(pMine.getMineBlock()),
                    SerializeUtils.toString(pMine.getMineRegion()), pMine.isPublic()
            );
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void insertLastLocation() {
        executeQuery("INSERT IGNORE INTO LastLocation VALUES(?,?)", "LOCATION", 0);
    }

    public int getLastLocation() {
        String query = "SELECT * FROM LastLocation WHERE IDENTIFIER=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM LastLocation WHERE IDENTIFIER=?")) {

            ps.setString(1, "LOCATION");
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next())
                return resultSet.getInt("LAST_LOCATION");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void saveLastLocation(int x) {
        executeQuery("UPDATE LastLocation SET LAST_LOCATION=? WHERE IDENTIFIER=?", x, "LOCATION");
    }

}
