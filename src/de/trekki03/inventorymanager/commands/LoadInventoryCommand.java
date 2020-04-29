package de.trekki03.inventorymanager.commands;

import de.trekki03.inventorymanager.Main;
import de.trekki03.inventorymanager.utility.Language;
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

public class LoadInventoryCommand implements CommandExecutor
{
    private MojangApi mojangApi = new MojangApi();
    private final Main plugin;
    private final Language lang;

    public LoadInventoryCommand(Main plugin, Language lang)
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
                loadInventory((Player) sender, (Player) sender, false);
                return true;
            }
            case 1:
            {
                withArgument( (Player) sender, args[0], false);
                return true;
            }
            case 2:
            {
                switch (args[1].toLowerCase())
                {
                    case "true":
                    {
                        withArgument( (Player) sender, args[0], true);
                        return true;
                    }
                    case "false":
                    {
                        withArgument( (Player) sender, args[0], false);
                        return true;
                    }
                    default:
                    {
                        return false;
                    }
                }
            }
            default:
            {
                return false;
            }
        }
    }

    private void withArgument(Player sender, String arg, boolean replace)
    {
       String uuid = mojangApi.getUUID(arg);

       switch (uuid)
       {
           case "invalid name":
           {
                sender.sendMessage(lang.getText("chatMessage.loadInventory.noPlayer", sender));
           }
           case "error":
           {
                sender.sendMessage(lang.getText("chatMessage.error", sender));
           }
           default:
           {
               if(replace)
               {
                   loadInventory(sender, Bukkit.getOfflinePlayer(UUID.fromString(uuid)), true);
               }
               else
               {
                   loadInventory(sender, Bukkit.getOfflinePlayer(UUID.fromString(uuid)), false);
               }
           }
       }
    }

    private void loadInventory(Player sender, OfflinePlayer target, boolean replaceInventory)
    {
        File file = new File(plugin.getDataFolder(), String.format("inventories/%s.yml", target.getUniqueId()));
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists())
        {
            sender.sendMessage(lang.getText("chatMessage.loadInventory.noPlayerData", sender));
            return;
        }
        ItemStack[] contents = ((List<ItemStack>) config.get("inventory.content")).toArray(new ItemStack[0]);
        if (replaceInventory)
        {
            sender.getInventory().setContents(contents);
            sender.sendMessage(lang.getText("chatMessage.loadInventory.inventoryReplaced", sender));
        } else
        {
            Inventory inv = Bukkit.createInventory(null, 54);
            inv.setContents(contents);
            sender.openInventory(inv);
        }


    }
}
