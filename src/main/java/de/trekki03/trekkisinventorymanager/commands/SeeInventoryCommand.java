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

/**
 * The SeeInventoryCommands gives a authorized player the ability to see and change an inventory of another player.
 * @author Trekki03
 * @since A-0.3
 */
public class SeeInventoryCommand implements CommandExecutor, Listener
{
    //A Hashmap which contains all inventories, which are opened with /seeinv at the time
    private HashMap<Player, Player> seeInvPlayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        //Test if sender is Console - Only players permitted
        if (CommandBooleans.isConsole(sender, true, Main.lang))
        {
            return true;
        }
        
        //Test if command is send with one or two Arguments
        switch (args.length)
        {
            //One argument -> target name - interaction = false
            case 1:
                {
                    return withArgument((Player) sender, args[0], null);
                }

            //Two arguments -> target name - interaction = true/false
            case 2:
                {
                    return withArgument((Player) sender, args[0], args[1]);
                }
            
            //More then two arguments -> error
            default:
                {
                    return false;
                }
        }
    }

    /*
     * This function performs the action.
     * The second passed value can be null, if there is no second argument. 
     * In this case, the interaction with the inventory is set to false.
     */
    private boolean withArgument(Player sender, String arg0, String arg1)
    {
        // Get the target from the first argument and check if it is valid.
        Player target = Bukkit.getPlayer(arg0);
        if (target == null)
        {
            sender.sendMessage(Main.lang.getText("chatMessage.seeInventory.noPlayer", sender));
            return true;
        }

        //creates a inventory for the sender, which contains the targets items.
        Inventory targetContentInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        targetContentInventory.setContents(target.getInventory().getContents());

        //Checks if there is an second argument
        if (arg1 != null)
        {
            //second arg: yes
            switch (arg1.toLowerCase())
            {
                //if argument is true, add sender and target to seeInvPlayers Hashmap than open the created inventory for the sender
                case "true":
                    sender.openInventory(targetContentInventory);
                    seeInvPlayers.put(sender, target);
                    return true;

                //if argument is false, open the created inventory for the sender
                case "false":
                    sender.openInventory(targetContentInventory);
                    return true;

                //if argument isn't true/false send command instruction
                default:
                    return false;
            }
        } 
        else
        {
            //second arg: no
            //open the created inventory for the sender
            sender.openInventory(targetContentInventory);
            return true;
        }
    }

    /*
     * When an inventory gets closed, test if it is one of the seeInvPlayers Hashmap.
     * If this is the case, exchange the targets inventory with the seeInv Inventory and
     * delete them from the Hashmap
     */
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
