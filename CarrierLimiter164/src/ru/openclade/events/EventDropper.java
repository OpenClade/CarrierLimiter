package ru.openclade.events;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.openclade.Main;
import ru.openclade.controller.BlockController;
import ru.openclade.controller.LimitController;
import ru.openclade.entity.BlockBased;

public class EventDropper implements Listener {
	LimitController controller = new LimitController(Main.db);
	BlockController blockcon = new BlockController(Main.db);
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p != null && !controller.playerExists(p.getUniqueId())) {
		   controller.addPlayer(p);
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Block blockm = e.getBlock();
		Player player = e.getPlayer();
		ConfigurationSection section = controller.plugin.getConfig().getConfigurationSection("config");
		if(blockm != null && blockm.getTypeId() == section.getInt("blockid")) {
			BlockBased block = new BlockBased(blockm.getX(), blockm.getY(), blockm.getZ(), player.getUniqueId().toString());
			if(controller.getLimitation(player.getUniqueId()) < section.getInt("limit") && controller.playerExists(player.getUniqueId())) {
				List<BlockBased> blocks = this.blockcon.getBlocks(player.getUniqueId());
				for(BlockBased blockk : blocks) {
					if(blockk.getX() == block.getX() && blockk.getY() == block.getY() && blockk.getZ() == block.getZ()) {
						controller.incrementOfPlayerLimitation(player);
						blockcon.deleteBlock(player, blockm);
						return;
					}
				} 
				player.sendMessage("Wrong!");
				e.setCancelled(true);
				
				
			}
		}
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		ConfigurationSection section = controller.plugin.getConfig().getConfigurationSection("config");
		if(e.getBlock() != null && e.getBlock().getTypeId() == section.getInt("blockid")) {
			if(controller.getLimitation(e.getPlayer().getUniqueId()) > 0 && controller.playerExists(e.getPlayer().getUniqueId())) {
				controller.decrementOfPlayerLimitation(e.getPlayer());
				blockcon.addBlock(e.getPlayer(), e.getBlock());
			} else {
				e.getPlayer().sendMessage("Your limitation is 0. Please, break block");
				e.setCancelled(true);
			}
		}
 	}
}
