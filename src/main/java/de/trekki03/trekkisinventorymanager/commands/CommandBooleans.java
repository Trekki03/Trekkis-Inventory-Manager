package de.trekki03.trekkisinventorymanager.commands;


import de.trekki03.trekkisinventorymanager.utility.Language;
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
