package de.trekki03.inventorymanager;

import de.trekki03.inventorymanager.commands.LoadInventoryCommand;
import de.trekki03.inventorymanager.commands.SaveInventoryCommand;
import de.trekki03.inventorymanager.commands.SeeEnderchestCommand;
import de.trekki03.inventorymanager.commands.SeeInventoryCommand;
import de.trekki03.inventorymanager.utility.Language;
import de.trekki03.inventorymanager.utility.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Language lang = new Language(this);

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 6317);

        SeeInventoryCommand seeinv = new SeeInventoryCommand(this, lang);
        SeeEnderchestCommand seeend = new SeeEnderchestCommand(this, lang);

        getCommand("saveinv").setExecutor(new SaveInventoryCommand(this, lang));
        getCommand("loadinv").setExecutor(new LoadInventoryCommand(this, lang));
        getCommand("seeinv").setExecutor(seeinv);
        getCommand("seeend").setExecutor(seeend);

        Bukkit.getPluginManager().registerEvents(seeend, this);
        Bukkit.getPluginManager().registerEvents(seeinv, this);


        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + lang.getText("consoleMessage.load"));

    }

    @Override
    public void onDisable()
    {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + lang.getText("consoleMessage.unload"));
    }
}
