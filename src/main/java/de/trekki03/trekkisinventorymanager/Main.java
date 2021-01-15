package de.trekki03.trekkisinventorymanager;

import de.trekki03.trekkisinventorymanager.commands.LoadInventoryCommand;
import de.trekki03.trekkisinventorymanager.commands.SaveInventoryCommand;
import de.trekki03.trekkisinventorymanager.commands.SeeEnderchestCommand;
import de.trekki03.trekkisinventorymanager.commands.SeeInventoryCommand;
import de.trekki03.trekkisinventorymanager.utility.Language;
import de.trekki03.trekkisinventorymanager.utility.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{

    //static objects for all classes
    public static Main plugin;
    public static Language lang;

    @Override
    public void onEnable() {

        //instanciate static objects
        plugin = this;
        lang = new Language();

        //start bStats
        Metrics metrics = new Metrics(plugin, 6317);
        
        //TODO Add lang for bStats messages
        if(metrics.isEnabled())
        {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "bStats started");
        }
        else
        {
            getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "bStats failed to start");
        }
        
        //classes with objects for event registration
        SeeInventoryCommand seeinv = new SeeInventoryCommand();
        SeeEnderchestCommand seeend = new SeeEnderchestCommand();

        //register events
        Bukkit.getPluginManager().registerEvents(seeend, this);
        Bukkit.getPluginManager().registerEvents(seeinv, this);

        //registering commands
        getCommand("saveinv").setExecutor(new SaveInventoryCommand());
        getCommand("loadinv").setExecutor(new LoadInventoryCommand());
        getCommand("seeinv").setExecutor(seeinv);
        getCommand("seeend").setExecutor(seeend);

        //send plugin loaded Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + lang.getText("consoleMessage.load"));

    }

    @Override
    public void onDisable()
    {
        //send plugin unloaded Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + lang.getText("consoleMessage.unload"));
    }
}
