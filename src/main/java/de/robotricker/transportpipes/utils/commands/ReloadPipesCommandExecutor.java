package de.robotricker.transportpipes.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.robotricker.transportpipes.TransportPipes;

public class ReloadPipesCommandExecutor implements PipesCommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, String[] args) {
		if (!cs.hasPermission("transportpipes.reload")) {
			return false;
		}

		for (Player on : Bukkit.getOnlinePlayers()) {
			TransportPipes.instance.armorStandProtocol.reloadRenderSystem(on);
		}

		cs.sendMessage("§cPipes reloaded");
		return true;
	}

}