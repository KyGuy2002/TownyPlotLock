package net.mcblockbuilds.townyplotlock;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.CustomDataField;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class unlockCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TownBlock plot = TownyAPI.getInstance().getTownBlock((Player) sender);

        if (plot == null) {
            sender.sendMessage("§6[Towny] &cYou are not standing in a plot!");
            return false;
        }

        // cancel if you dont own the plot your in
        if (!(Objects.requireNonNull(plot.getTownBlockOwner()).getName().equalsIgnoreCase(sender.getName()))) {
            sender.sendMessage("§6[Towny] §cYou don't own this plot, " + plot.getTownBlockOwner().getName() + " does!");
            return false;
        }

        BooleanDataField meta = new BooleanDataField("locked", true);

        // Check that the town has the metadata key.
        if (plot.hasMeta(meta.getKey())) {
            // Get the metadata from the town using the key.
            CustomDataField cdf = plot.getMetadata(meta.getKey());
            // Check that it's an IntegerDataField
            if (cdf instanceof BooleanDataField) {
                // Cast to StringDataField
                BooleanDataField bdf = (BooleanDataField) cdf;

                // Update the value
                bdf.setValue(true);
                sender.sendMessage("§6[Towny] §aPlot has been unlocked, everyone is free to enter.");
                return true;
            }
        }

        // Add meta field
        sender.sendMessage("§6[Towny] §aPlot has been unlocked, everyone is free to enter.");
        BooleanDataField newData = new BooleanDataField("locked", false);
        plot.addMetaData(newData);
        return true;
    }

}
