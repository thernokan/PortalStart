package world.bentobox.portalstart;


import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.addons.Pladdon;


public class PortalStartPladdon extends Pladdon {
    private Addon addon;

    @Override
    public Addon getAddon() {
        if (addon == null) {
            addon = new PortalStart();
        }
        return addon;
    }
}
