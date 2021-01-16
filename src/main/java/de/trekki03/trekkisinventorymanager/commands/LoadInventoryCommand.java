package de.trekki03.trekkisinventorymanager.commands;

import de.trekki03.trekkisinventorymanager.Main;
import de.trekki03.trekkisinventorymanager.utility.MojangApi;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * The LoadInventoryCommand loads a previously saved inventory
 * @author Trekki03
 * @since A-0.1
 */
public class LoadInventoryCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        //tests if sender is console -> has to be player
        if (CommandBooleans.isConsole(sender, true, Main.lang))
        {
            return true;
        }

        //test argument count
        switch (args.length)
        {
            case 0:
            {
                //zero arguments: target -> sender | interaction -> false
                loadInventory((Player) sender, (Player) sender, false);
                return true;
            }
            case 1:
            {
                //one arguments: target -> args[0] | interaction -> false
                withArgument( (Player) sender, args[0], false);
                return true;
            }
            case 2:
            {
                //two arguments: target -> args[0] | interaction -> args[2]

                //check if args[1] for true/false
                switch (args[1].toLowerCase())
                {
                    case "true":
                    {
                        //interaction true
                        withArgument( (Player) sender, args[0], true);
                        return true;
                    }
                    case "false":
                    {
                        //interaction false
                        withArgument( (Player) sender, args[0], false);
                        return true;
                    }
                    default:
                    {
                        //error resend command description
                        return false;
                    }
                }
            }
            default:
            {
                //error resend command description
                return false;
            }
        }
    }

    //function called if command used with arguments
    private void withArgument(Player sender, String arg, boolean replace)
    {
        //getting target uuid from MojangAPI 
        String uuid = MojangApi.getUUID(arg);

        //check if uuid is valid
        switch (uuid)
        {
            case "invalid name":
            {
                //name does not exist in database, send player not exists message
                sender.sendMessage(Main.lang.getText("chatMessage.loadInventory.noPlayer", sender));
                break;
            }
            case "error":
            {
                //error while reading form MojangAPI 
                sender.sendMessage(Main.lang.getText("chatMessage.error", sender));
                break;
            }
            default:
            {
                //default: everything worked

                //checks, if the inventory should be replaced with the loaded one.
                if(replace)
                {
                    loadInventory(sender, Bukkit.getOfflinePlayer(UUID.fromString(uuid)), true);
                }
                else
                {
                    loadInventory(sender, Bukkit.getOfflinePlayer(UUID.fromString(uuid)), false);
                }
                break;
            }
        }
    }

    //load the inventory from the database
    private void loadInventory(Player sender, OfflinePlayer target, boolean replaceInventory)
    {
        //specifies the name, of the file, where the data should be stored.
        File file = new File(Main.plugin.getDataFolder(), String.format("inventories/%s.yml", target.getUniqueId()));
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        //checks if the file exists, if not, sends an error message.
        if (!file.exists())
        {
            sender.sendMessage(Main.lang.getText("chatMessage.loadInventory.noPlayerData", sender));
            return;
        }

        //saves all items from the file into an for spigot usable formate 
        @SuppressWarnings("unchecked")
        ItemStack[] contents = ((List<ItemStack>) config.get("inventory.content")).toArray(new ItemStack[0]);
        
        //opens an inventory or replaces the senders one, depending on the second argument
        
        if (replaceInventory)
        {
            sender.getInventory().setContents(contents);
            sender.sendMessage(Main.lang.getText("chatMessage.loadInventory.inventoryReplaced", sender));
        } 
        else
        {
            Inventory inv = Bukkit.createInventory(null, 54);
            inv.setContents(contents);
            sender.openInventory(inv);
        }
    }
}
