package com.mactso.spawncapcontrolutility;

import com.mactso.spawncapcontrolutility.config.ModConfigs;
import com.mactso.spawncapcontrolutility.events.CommonEvents;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

	public static final String MOD_ID = "spawncapcontrolutility";

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();
		registerCallbacks();
	}
	
	public void registerCallbacks() {
		CommonEvents.register();
	}

}
