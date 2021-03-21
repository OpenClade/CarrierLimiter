package ru.openclade.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.entity.Player;

import ru.openclade.Main;
import ru.openclade.data.interfaces.IDB;

public class LimitController {
	 public Main plugin = Main.getPlugin(Main.class);
	
	private IDB db;
	public LimitController(IDB db) {
		this.db = db;
	}
	
	public void addPlayer(Player player) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO players(UUID, name, limitation) VALUES (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, player.getUniqueId().toString());
            st.setString(2, player.getName());
            st.setInt(3, plugin.getConfig().getInt("config.limit"));
            st.execute();
          
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
	public void decrementOfPlayerLimitation(Player player) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "UPDATE players SET limitation = limitation - 1 WHERE UUID = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, player.getUniqueId().toString());
            st.execute();
          
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
	public void incrementOfPlayerLimitation(Player player) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "UPDATE players SET limitation = limitation + 1 WHERE UUID = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, player.getUniqueId().toString());
            st.execute();
          
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
	public boolean playerExists(UUID uuid) {
		try {
			PreparedStatement statement = db.getConnection()
					.prepareStatement("SELECT * FROM players WHERE UUID=?");
			statement.setString(1, uuid.toString());

			ResultSet results = statement.executeQuery();
			if (results.next()) { 
				return true;
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	public int getLimitation(UUID uuid) {
		try {
			PreparedStatement statement = db.getConnection()
					.prepareStatement("SELECT limitation FROM players WHERE UUID=?");
			statement.setString(1, uuid.toString());

			ResultSet results = statement.executeQuery();
			if (results.next()) {
				return results.getInt("limitation");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
