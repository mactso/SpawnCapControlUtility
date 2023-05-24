package com.mactso.spawncapcontrolutility;

import com.mactso.spawncapcontrolutility.config.MyConfig;
import com.mactso.spawncapcontrolutility.events.CommonEvents;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

	public static final String MOD_ID = "spawncapcontrolutility";

	@Override
	public void onInitialize() {
		MyConfig.registerConfigs();
		registerCallbacks();
	}
	
	public void registerCallbacks() {
		CommonEvents.register();
	}

}
