package world.bentobox.portalstart;


import org.bukkit.event.Listener;

import world.bentobox.bentobox.api.addons.Addon;


/**
 * Addon to BentoBox that teleports player to their island. Makes an island if required.
 * @author tastybento
 *
 */
public class PortalStart extends Addon implements Listener {

    @Override
    public void onEnable() {
        this.registerListener(this);
    }


    @Override
    public void onDisable(){
        // Do nothing
    }



}
