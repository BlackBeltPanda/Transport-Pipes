package de.robotricker.transportpipes.ducts.manager;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Map;

import javax.inject.Inject;

import de.robotricker.transportpipes.TransportPipes;
import de.robotricker.transportpipes.ducts.Duct;
import de.robotricker.transportpipes.ducts.DuctRegister;
import de.robotricker.transportpipes.location.BlockLocation;
import de.robotricker.transportpipes.protocol.ProtocolService;

public abstract class DuctManager<T extends Duct> {

    protected TransportPipes transportPipes;
    protected DuctRegister ductRegister;
    protected GlobalDuctManager globalDuctManager;
    protected ProtocolService protocolService;

    @Inject
    public DuctManager(TransportPipes transportPipes, DuctRegister ductRegister, GlobalDuctManager globalDuctManager, ProtocolService protocolService) {
        this.transportPipes = transportPipes;
        this.ductRegister = ductRegister;
        this.globalDuctManager = globalDuctManager;
        this.protocolService = protocolService;
    }

    public abstract void registerDuctTypes();

    public abstract void tick();

    /**
     * called inside the bukkit thread whenever a duct comes into visible range
     */
    public void notifyDuctShown(Duct duct, Player p) {
        if (globalDuctManager.getPlayerDucts(p).add(duct))
            protocolService.sendASD(p, duct.getBlockLoc(), globalDuctManager.getPlayerRenderSystem(p, duct.getDuctType().getBaseDuctType()).getASDForDuct(duct));
    }

    /**
     * called inside the bukkit thread whenever a duct gets outside of the visible range
     */
    public void notifyDuctHidden(Duct duct, Player p) {
        if (globalDuctManager.getPlayerDucts(p).remove(duct))
            protocolService.removeASD(p, globalDuctManager.getPlayerRenderSystem(p, duct.getDuctType().getBaseDuctType()).getASDForDuct(duct));
    }

    public void updateNonDuctConnections(Duct duct) {

    }

}