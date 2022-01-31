package net.prison.foggies.core.pickaxe.database;

import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.pickaxe.api.EnchantBase;
import net.prison.foggies.core.utils.Database;
import net.prison.foggies.core.utils.SerializeUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class PickaxeDatabase extends Database {

    private static final String TABLE_NAME = "Pickaxes";

    public PickaxeDatabase(String host, String port, String database, String userName, String password) {
        super(host, port, database, userName, password);
        this.createTable();
    }

    @Override
    public void createTable() {
        executeQuery("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                "UUID VARCHAR(37), " +
                "NAME VARCHAR(250), " +
                "ENCHANTMENTS TEXT, " +
                "RAW_BLOCKS BIGINT, " +
                "BLOCKS BIGINT, " +
                "TOKENS_SPENT BIGINT, " +
                "LEVEL BIGINT, " +
                "EXPERIENCE DOUBLE, " +
                "PRIMARY KEY (UUID)" +
                ")");
    }

    public Optional<PlayerPickaxe> getPickaxe(UUID uuid) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE UUID=?")) {

            ps.setString(1, uuid.toString());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next())
                return Optional.of(
                        new PlayerPickaxe(
                                uuid,
                                resultSet.getString("NAME"),
                                resultSet.getLong("RAW_BLOCKS"),
                                resultSet.getLong("BLOCKS"),
                                resultSet.getLong("TOKENS_SPENT"),
                                resultSet.getLong("LEVEL"),
                                resultSet.getDouble("EXPERIENCE"),
                                (HashMap<EnchantBase, Long>) SerializeUtils.fromString(resultSet.getString("ENCHANTMENTS"))
                        )
                );
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void savePickaxe(PlayerPickaxe pickaxe) throws IOException {
        executeQuery("UPDATE " + TABLE_NAME + " SET " +
                        "NAME=?",
                        "RAW_BLOCKS=?," +
                        "BLOCKS=?," +
                        "TOKENS_SPENT=?," +
                        "LEVEL=?," +
                        "EXPERIENCE=?," +
                        "ENCHANTMENTS=? WHERE UUID=?",
                pickaxe.getName(),
                pickaxe.getRawBlocksMined(),
                pickaxe.getBlocksMined(),
                pickaxe.getTokensSpent(),
                pickaxe.getLevel(),
                pickaxe.getExperience(),
                SerializeUtils.toString(pickaxe.getEnchantments()), pickaxe.getUuid().toString());
    }

    public void insertPickaxe(PlayerPickaxe pickaxe) throws IOException {
        executeQuery("INSERT IGNORE INTO " + TABLE_NAME + " VALUES(?,?,?,?,?,?,?,?)",
                pickaxe.getUuid().toString(),
                pickaxe.getName(),
                pickaxe.getRawBlocksMined(),
                pickaxe.getBlocksMined(),
                pickaxe.getTokensSpent(),
                pickaxe.getLevel(),
                pickaxe.getExperience(),
                SerializeUtils.toString(pickaxe.getEnchantments()));
    }



}
