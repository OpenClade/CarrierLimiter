package ru.openclade;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import ru.openclade.commands.CommandMyBlocks;
import ru.openclade.data.DBConnector;
import ru.openclade.data.interfaces.IDB;
import ru.openclade.events.EventDropper; 

public class Main extends JavaPlugin {
	public static IDB db;
	
	
	@Override
	public void onEnable() {
		  this.loadConfiguration();
		  ConfigurationSection section = this.getConfig().getConfigurationSection("connect");
		  db = new DBConnector(section.getString("host"), section.getString("port"),
				  section.getString("database"), 
				  section.getString("username"), section.getString("password"));
		  System.out.println("Successfully!");
		  this.getServer().getPluginManager().registerEvents(new EventDropper(), this); 
		  this.getCommand("myblocks").setExecutor(new CommandMyBlocks());
	}
	public void onDisable() {
		  this.saveDefaultConfig();
	} 
	
	
	public void loadConfiguration(){ 
	     this.getConfig().options().copyDefaults(true); // NOTE: You do not have to use "plugin." if the class extends the java plugin
	     this.saveConfig();
	}
	
	
}
