package net.prison.foggies.core.pickaxe;

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
                "ENCHANTMENTS TEXT, " +
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
                                (HashMap<EnchantBase, Long>) SerializeUtils.fromString(resultSet.getString("ENCHANTMENTS"))
                        )
                );
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void savePickaxe(PlayerPickaxe pickaxe) throws IOException {
        executeQuery("UPDATE " + TABLE_NAME + " SET ENCHANTMENTS=? WHERE UUID=?",
                SerializeUtils.toString(pickaxe.getEnchantments()), pickaxe.getUuid().toString());
    }

    public void insertPickaxe(PlayerPickaxe pickaxe) throws IOException {
        executeQuery("INSERT IGNORE INTO " + TABLE_NAME + " VALUES(?,?)",
                pickaxe.getUuid().toString(), SerializeUtils.toString(pickaxe.getEnchantments()));
    }



}
