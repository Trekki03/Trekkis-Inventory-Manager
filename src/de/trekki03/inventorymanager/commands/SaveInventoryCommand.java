package de.trekki03.inventorymanager.commands;

import de.trekki03.inventorymanager.Main;
import de.trekki03.inventorymanager.utility.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SaveInventoryCommand implements CommandExecutor
{

    private final Main plugin;
    private final Language lang;

    public SaveInventoryCommand(Main plugin, Language lang)
    {
        this.plugin = plugin;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (CommandBooleans.isConsole(sender, true, lang))
        { //Sender has to be player
            return true;
        }

        switch (args.length)
        {  //Argument count
            case 0:
            {
                saveInventory((Player) sender, (Player) sender);
                return true;
            }
            case 1:
            {
                oneArgument((Player) sender, args[0]);
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

    private void oneArgument(Player sender, String arg)
    { //One argument

        Player target = Bukkit.getPlayer(arg); //Get player from argument

        if (target == null)
        {
            sender.sendMessage(lang.getText("chatMessage.saveInventory.noPlayer", sender)); //Test if a "online player" was found
            return;
        }

        saveInventory(sender, target);
    }

    private void saveInventory(Player sender, Player target)
    {
        File file = new File(plugin.getDataFolder(), String.format("inventories/%s.yml", target.getUniqueId()));
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (!file.exists())
        {  //Test if file exists and create folders
            file.getParentFile().mkdirs();
        }

        cfg.set("inventory.content", target.getInventory().getContents()); //Save inventory content

        try
        {
            cfg.save(file);         //save onfig
            sender.sendMessage(lang.getText("chatMessage.saveInventory.inventorySaved", sender));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
