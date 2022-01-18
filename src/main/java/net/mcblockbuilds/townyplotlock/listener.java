package net.mcblockbuilds.townyplotlock;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.CustomDataField;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.palmergames.bukkit.util.BukkitTools.getServer;

public class listener implements Listener {

    private TownyPlotLock plugin;
    public listener(TownyPlotLock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlotEnter(PlayerChangePlotEvent event) {

        if (event.getPlayer().hasPermission("towny.bypasslockedplots")) {
            return;
        }

        TownBlock plot;

        try {
            plot = event.getTo().getTownBlock();
        } catch (NotRegisteredException e) {
            return;
        }

        //TownBlock plot = TownyAPI.getInstance().getTownBlock(event.getPlayer());

        if (plot == null) {
            return;
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

                // Check if plots locked
                if (bdf.getValue() == true) {

                    Town town;
                    try {
                        town = plot.getTown();
                    } catch (NotRegisteredException e) {
                        return;
                    }

                    if (town.hasOutlaw(event.getPlayer().getName())) {

                        event.getPlayer().sendMessage("§cOutlaws are not allowed to enter this plot!");
                        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(-1));

                        getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                if (TownyAPI.getInstance().getTownBlock(event.getPlayer()) == plot) { // If player still in plot
                                    event.getPlayer().sendMessage("§cOutlaws are not allowed to enter this plot!");
                                    event.getPlayer().teleport(event.getMoveEvent().getFrom());
                                }

                            }
                        }, 20);

                    }


                }
            }
        }

    }

}
