package net.mcblockbuilds.townyplotlock;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.palmergames.bukkit.util.BukkitTools.getServer;

public class listener implements Listener {

    private TownyPlotLock plugin;
    public listener(TownyPlotLock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlotEnter(PlayerChangePlotEvent event) throws NotRegisteredException {
        if (!(event.getTo().hasTownBlock())) return; // Make sure player is in a town

        TownBlock plot = event.getTo().getTownBlock();

        if (event.getPlayer().hasPermission("towny.bypasslockedplots")) return; // Let staff bypass
        if (!(plot.getTown().hasOutlaw(event.getPlayer().getName()))) return; // Check if player is even an outlaw

        if (!(plot.hasMeta("locked"))) return; // Check if plot has locked meta set
        if (!(((BooleanDataField) plot.getMetadata("locked")).getValue())) return; // Check if plots locked

        // Fling the player out of the plot
        event.getPlayer().sendMessage("§cOutlaws are not allowed to enter this plot!");
        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(-1));

        // If the player managed to stay in the plot 1 second later, teleport them out
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
