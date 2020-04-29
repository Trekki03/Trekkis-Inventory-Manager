package de.trekki03.inventorymanager.commands;


import de.trekki03.inventorymanager.utility.Language;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBooleans
{

    public static boolean isConsole(CommandSender sender, boolean hasToBePlayer, Language lang)
    {

        if (sender instanceof Player)
        {
            return false;
        }

        if (hasToBePlayer)
        {
            sender.sendMessage(lang.getText("consoleMessage.onlyPlayer"));
        }
        return true;
    }
}
