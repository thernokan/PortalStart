package world.bentobox.portalstart;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.user.User;


/**
 * Addon to BentoBox that teleports player to their island. Makes an island if required.
 * @author tastybento
 *
 */
public class PortalStart extends Addon implements Listener {

    private final Set<UUID> inTeleport = new HashSet<>();

    @Override
    public void onEnable() {
        this.registerListener(this);
    }


    @Override
    public void onDisable(){
        // Do nothing
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPortal(PlayerPortalEvent e) {
        // Check if the destination is a game world and the source is out of the world
        if (e.getTo() != null) {
            if (getPlugin().getIWM().inWorld(e.getTo()) && !getPlugin().getIWM().inWorld(e.getFrom())) {
                String perm = getPlugin().getIWM().getPermissionPrefix(e.getTo().getWorld()) + "island.portalstart";
                User user = User.getInstance(e.getPlayer());
                if (!user.hasPermission(perm)) {
                    return;
                }
                // Check if the player has an island in this world
                if (getIslands().hasIsland(e.getTo().getWorld(), e.getPlayer().getUniqueId())) {
                    // Teleport to their island
                    e.setTo(getIslands().getHomeLocation(e.getTo().getWorld(), user));
                    return;
                }

                // Get the Game Mode
                getPlugin().getIWM().getAddon(e.getTo().getWorld()).ifPresent(gm -> {
                    e.setCancelled(true);
                    inTeleport.add(e.getPlayer().getUniqueId());
                    gm.getPlayerCommand().ifPresent(plCmd -> {
                        plCmd.call(user, "", List.of());
                    });
                });

            }
        }
    }

}
