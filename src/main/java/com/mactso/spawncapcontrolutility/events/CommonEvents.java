package com.mactso.spawncapcontrolutility.events;

import java.util.concurrent.Executor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;

public class CommonEvents {

	public static void onLoadWorld(Executor executor, LevelAccessor world) {
		System.out.println("onLoadWorld executed");
	}

	public static void onServerStarting(MinecraftServer server) {
		HandleServerAboutToStart.onAboutToStart(server);
	}

	
	public static void register()
	{
		ServerLifecycleEvents.SERVER_STARTING.register(CommonEvents::onServerStarting);
		ServerWorldEvents.LOAD.register(CommonEvents::onLoadWorld);
	}
}
