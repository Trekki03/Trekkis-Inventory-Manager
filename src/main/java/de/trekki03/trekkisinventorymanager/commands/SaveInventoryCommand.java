package de.trekki03.trekkisinventorymanager.commands;

import de.trekki03.trekkisinventorymanager.Main;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (CommandBooleans.isConsole(sender, true, Main.lang))
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
            sender.sendMessage(Main.lang.getText("chatMessage.saveInventory.noPlayer", sender)); //Test if a "online player" was found
            return;
        }

        saveInventory(sender, target);
    }

    private void saveInventory(Player sender, Player target)
    {
        File file = new File(Main.plugin.getDataFolder(), String.format("inventories/%s.yml", target.getUniqueId()));
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (!file.exists())
        {  //Test if file exists and create folders
            file.getParentFile().mkdirs();
        }

        cfg.set("inventory.content", target.getInventory().getContents()); //Save inventory content

        try
        {
            cfg.save(file);         //save onfig
            sender.sendMessage(Main.lang.getText("chatMessage.saveInventory.inventorySaved", sender));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
