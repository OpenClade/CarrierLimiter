package ru.openclade.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import ru.openclade.Main;
import ru.openclade.data.interfaces.IDB;
import ru.openclade.entity.BlockBased;
 

public class BlockController {
public Main plugin = Main.getPlugin(Main.class);
	
	private IDB db;
	public BlockController(IDB db) {
		this.db = db;
	}
	public List<BlockBased> getBlocks(UUID uuid) {
		try {
			PreparedStatement statement = db.getConnection()
					.prepareStatement("SELECT * FROM blocks WHERE UUID=?");
			statement.setString(1, uuid.toString());
			List<BlockBased> blocks = new LinkedList<BlockBased>();
			ResultSet results = statement.executeQuery();
			while(results.next()) { 
				blocks.add(new BlockBased(results.getInt("X"), results.getInt("Y"), results.getInt("Z"), 
						results.getString("UUID")));
			}
            return blocks;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void addBlock(Player player, Block block ) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO blocks(X, Y, Z, UUID) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, block.getX());
            st.setInt(2, block.getY());
            st.setInt(3, block.getZ());
            st.setString(4, player.getUniqueId().toString());
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
	public void deleteBlock(Player player, Block block) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "DELETE FROM blocks WHERE  X = ? AND Y = ? AND Z = ? AND UUID = ?";
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setInt(1, block.getX());
            st.setInt(2, block.getY());
            st.setInt(3, block.getZ());
            st.setString(4, player.getUniqueId().toString());
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
}
