package de.trekki03.trekkisinventorymanager.commands;

import de.trekki03.trekkisinventorymanager.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class SeeInventoryCommand implements CommandExecutor, Listener
{
    private HashMap<Player, Player> seeInvPlayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)

    {
        if (CommandBooleans.isConsole(sender, true, Main.lang))
        {
            //Sender has to be Player
            return true;
        }

        switch (args.length)
        {
            case 1:
                return withArgument((Player) sender, args[0], null);

            case 2:
                return withArgument((Player) sender, args[0], args[1]);

            default:
                return false;
        }

    }

    private boolean withArgument(Player sender, String arg0, String arg1)
    {
        Player target = Bukkit.getPlayer(arg0);
        if (target == null)
        {
            sender.sendMessage(Main.lang.getText("chatMessage.seeInventory.noPlayer", sender));
            return true;
        }
        Inventory targetContentInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        targetContentInventory.setContents(target.getInventory().getContents());

        if (arg1 != null)
        {
            switch (arg1.toLowerCase())
            {
                case "true":
                    sender.openInventory(targetContentInventory);
                    seeInvPlayers.put(sender, target);
                    return true;

                case "false":
                    sender.openInventory(targetContentInventory);
                    return true;

                default:
                    return false;
            }
        } else
        {
            sender.openInventory(targetContentInventory);
            return true;
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        Player p = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if (seeInvPlayers.containsKey(p))
        {
            seeInvPlayers.get(p).getInventory().setContents(inventory.getContents());
            seeInvPlayers.remove(p);
        }

    }

}
