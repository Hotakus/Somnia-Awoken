package mods.su5ed.somnia.util;

import mods.su5ed.somnia.Somnia;
import mods.su5ed.somnia.api.capability.FatigueCapability;
import mods.su5ed.somnia.api.capability.IFatigue;
import mods.su5ed.somnia.server.ServerTickHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public enum SomniaState
{
	IDLE,
	ACTIVE,
	WAITING_PLAYERS,
	EXPIRED,
	NOT_NOW,
	COOLDOWN;
	
	public static SomniaState getState(ServerTickHandler handler) {
		long totalWorldTime = handler.worldServer.getGameTime();
		
		if (!Somnia.validSleepPeriod.isTimeWithin(totalWorldTime % 24000)) return NOT_NOW;

		List<ServerPlayerEntity> players = handler.worldServer.getPlayers();

		boolean anySleeping = false, allSleeping = true;
		int somniaSleep = 0, normalSleep = 0;

		for (ServerPlayerEntity player : players) {
			boolean sleeping = player.isSleeping() || ListUtils.containsRef(player, Somnia.instance.ignoreList);
			anySleeping |= sleeping;
			allSleeping &= sleeping;

			LazyOptional<IFatigue> props = player.getCapability(FatigueCapability.FATIGUE_CAPABILITY, null);
			if (props.isPresent() && props.resolve().get().shouldSleepNormally()) normalSleep++;
			else somniaSleep++;
		}

		if (allSleeping) {
			if (somniaSleep >= normalSleep) return ACTIVE;
		} else if (anySleeping) return WAITING_PLAYERS;

		return IDLE;
	}
}
