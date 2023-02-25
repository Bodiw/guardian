package me.bodiw.guardian.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.bodiw.Utils;

public class Database {

    private static String db = "jdbc:sqlite:" + System.getenv("MINECRAFT_SQLITE_DB");

    public static void init() {
        String query = "CREATE TABLE IF NOT EXISTS users (discord_id TEXT PRIMARY KEY, minecraft_uuid TEXT UNIQUE, banned BOOLEAN);";

        try (Connection conn = DriverManager.getConnection(db);
                PreparedStatement stmt = conn.prepareStatement(query)) {
        } catch (Exception e) {
            System.out.println("ERROR : Couldn't create table \'users\'");
            e.printStackTrace();
        }
    }

    public static User getUser(Field field, String value) {

        String query = "SELECT * FROM users WHERE " + field.getName() + " = ?";
        // Connect to the sqlite database and get the user
        try (Connection conn = DriverManager.getConnection(db);
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("discord_id"),
                            rs.getString("minecraft_uuid"),
                            rs.getBoolean("banned"));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR : Couldn't get user from database");
            e.printStackTrace();
        }
        return User.EMPTY;
    }

    public static User addUser(User user) {

        User old = getUser(Field.DISCORD_ID, user.discordID());

        String query = "INSERT OR REPLACE INTO users (discord_id, minecraft_uuid, banned) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(db);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.discordID());
            stmt.setString(2, user.minecraftUUID());
            stmt.setBoolean(3, user.banned());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR : Couldn't add user to database");
            e.printStackTrace();
        }

        // Handle the update on the whitelist table

        query = "INSERT OR REPLACE INTO whitelist (uuid, name, whitelisted) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(db);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.minecraftUUID());
            stmt.setString(2, Utils.getMinecraftName(user.minecraftUUID()));
            stmt.setBoolean(3, !user.banned());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR : Couldn't add player to whitelist");
            e.printStackTrace();
        }

        return old;
    }

    public static void setWhitelisted(User user, boolean whitelisted) {
        String query = "UPDATE whitelist SET whitelisted = ? WHERE uuid = ?";

        try (Connection conn = DriverManager.getConnection(db);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, whitelisted);
            stmt.setString(2, user.minecraftUUID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR : Couldn't update whitelist");
            e.printStackTrace();
        }

    }

}
