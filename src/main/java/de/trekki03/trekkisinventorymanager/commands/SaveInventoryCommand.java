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

/**
 * The SaveInventoryCommand saves the content of an (online) target player inventory in an readable yml file
 * @author Trekki03
 * @since A-0.1
 */
public class SaveInventoryCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        //tests if sender is a console - has to be player
        if (CommandBooleans.isConsole(sender, true, Main.lang))
        { 
            return true;
        }

        switch (args.length)
        {  
            //Argument count
            case 0:
            {
                // zero arguments -> target = sender
                saveInventory((Player) sender, (Player) sender);
                return true;
            }
            case 1:
            {
                // one argument -> target = argument
                oneArgument((Player) sender, args[0]);
                return true;
            }
            default:
            {
                //more than one argument -> send command instructions
                return false;
            }
        }
    }

    //This function is called, if one argument is specified
    private void oneArgument(Player sender, String arg)
    {
        //Get target from argument
        Player target = Bukkit.getPlayer(arg); 

        //Test if target is valid
        if (target == null)
        {
            sender.sendMessage(Main.lang.getText("chatMessage.saveInventory.noPlayer", sender)); //Test if a "online player" was found
            return;
        }

        //execute saveInventory function with extracted player as target
        saveInventory(sender, target);
    }

    //This function saves the inventory in the .yml file
    private void saveInventory(Player sender, Player target)
    {
        //specifies file name based on the players uuid 
        File file = new File(Main.plugin.getDataFolder(), String.format("inventories/%s.yml", target.getUniqueId()));
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        //if no file exists it creates a folder for it if not
        if (!file.exists())
        {  
            file.getParentFile().mkdirs();
        }

        //Save inventory content in the file
        cfg.set("inventory.content", target.getInventory().getContents()); 

        //saves the inventory
        try
        {
            cfg.save(file); 
            sender.sendMessage(Main.lang.getText("chatMessage.saveInventory.inventorySaved", sender));
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
