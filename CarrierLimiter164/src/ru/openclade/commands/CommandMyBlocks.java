package ru.openclade.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import ru.openclade.Main;
import ru.openclade.controller.BlockController;
import ru.openclade.controller.LimitController;
import ru.openclade.entity.BlockBased;
 

public class CommandMyBlocks implements CommandExecutor  {
	LimitController controller = new LimitController(Main.db);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    BlockController controller1 = new BlockController(Main.db);
	    if(cmd.getName().equalsIgnoreCase("myblocks")) {
			 Player player = (Player) sender;
			 player.sendMessage(ChatColor.GREEN + "Your limitation: " + controller.getLimitation(player.getUniqueId()));
			 List<BlockBased> blocks = controller1.getBlocks(player.getUniqueId());
			 for(BlockBased block: blocks) {
				 player.sendMessage("Your block: " + block.toString());
			 }
			 return true;
		}
		return false;
	}

	 

}
