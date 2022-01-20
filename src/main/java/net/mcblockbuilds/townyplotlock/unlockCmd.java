package net.mcblockbuilds.townyplotlock;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class unlockCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TownBlock plot = TownyAPI.getInstance().getTownBlock((Player) sender);

        // Cancel if not standing in plot
        if (plot == null) {
            sender.sendMessage("§6[Towny] &cYou are not standing in a plot!  Use §6F3 + G §cto show chunk borders.");
            return false;
        }

        // Cancel if not owner of current plot
        if (!(Objects.requireNonNull(plot.getTownBlockOwner()).getName().equals(sender.getName()))) {
            sender.sendMessage("§6[Towny] §cYou don't own this plot, §6" + plot.getTownBlockOwner().getName() + "§c does!");
            return false;
        }

        BooleanDataField bdf = new BooleanDataField("locked", false);

        // Update existing metadata
        if (plot.hasMeta("locked")) bdf.setValue(false);

        // Add new metadata
        if (!(plot.hasMeta("locked"))) plot.addMetaData(bdf);

        // Notify player of success
        sender.sendMessage("§6[Towny] §aPlot has been unlocked, everyone is free to enter.");
        return true;

    }

}
