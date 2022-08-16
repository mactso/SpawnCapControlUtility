package com.mactso.spawncapcontrolutility.config;

import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;
import com.mojang.datafixers.util.Pair;

public class ModConfigs {

	private static final Logger LOGGER = LogManager.getLogger();

	public static SimpleConfig CONFIG;
	private static ModConfigProvider configs;

	public static int safeValue(int newValue) {
		if (newValue < 1)
			return 1;
		if (newValue > 210)
			return 210;
		return newValue;
	}

	public static int getMonsterCap() {
		return safeValue(monsterCap);
	}

	public static void setMonsterCap(int newValue) {
		ModConfigs.monsterCap = safeValue(newValue);
	}

	public static int getCreatureCap() {
		return safeValue(creatureCap);
	}

	public static void setCreatureCap(int newValue) {
		ModConfigs.creatureCap = safeValue(newValue);
	}

	public static int getAmbientCap() {
		return safeValue(ambientCap);
	}

	public static void setAmbientCap(int ambientCap) {
		ModConfigs.ambientCap = ambientCap;
	}

	public static int getWaterCreatureCap() {
		return safeValue(waterCreatureCap);
	}

	public static void setWaterCreatureCap(int newValue) {
		ModConfigs.waterCreatureCap = safeValue(newValue);
	}

	public static int getWaterAmbientCap() {
		return safeValue(waterAmbientCap);
	}

	public static void setWaterAmbientCap(int newValue) {
		ModConfigs.waterAmbientCap = safeValue(newValue);
	}

	public static int getUndergroundWaterCreatureCap() {
		return safeValue(undergroundWaterCreatureCap);
	}

	public static void setUndergroundWaterCreatureCap(int newValue) {
		ModConfigs.undergroundWaterCreatureCap = safeValue(newValue);
	}

	public static int monsterCap;
	public static int creatureCap;
	public static int ambientCap;
	public static int waterCreatureCap;
	public static int waterAmbientCap;
	public static int undergroundWaterCreatureCap;

	public static HashSet<String> getModStringSet(String[] values) {
		HashSet<String> returnset = new HashSet<>();
		// Collection<ModContainer> loadedMods= FabricLoader.getAllMods(); error static
		// calling non-static.
		HashSet<String> loadedset = new HashSet<>();
		loadedset.add("respawnvillager");
		loadedset.add("test");

		for (String s : loadedset) {
			String s2 = s.trim().toLowerCase();
			if (!s2.isEmpty()) {
				if (!returnset.contains(s2)) {
					returnset.add(s2);
				} else {
					LOGGER.warn("spawnbalanceutility includedReportModsSet entry : " + s2
							+ " is not a valid current loaded forge mod.");
				}
			}
		}
		return returnset;
	}

	public static void registerConfigs() {
		configs = new ModConfigProvider();
		createConfigs();

		CONFIG = SimpleConfig.of(Main.MOD_ID + "config").provider(configs).request();

		assignConfigs();
	}

	private static void createConfigs() {
		configs.addKeyValuePair(new Pair<>("key.monsterCap", 87), "int");
		configs.addKeyValuePair(new Pair<>("key.creatureCap", 11), "int");
		configs.addKeyValuePair(new Pair<>("key.ambientCap", 14), "int");
		configs.addKeyValuePair(new Pair<>("key.waterCreatureCap", 6), "int");
		configs.addKeyValuePair(new Pair<>("key.waterAmbientCap", 21), "int");
		configs.addKeyValuePair(new Pair<>("key.undergroundWaterCreatureCap", 4), "int");
	}

	private static void assignConfigs() {
		monsterCap = CONFIG.getOrDefault("key.monsterCap", 87);
		creatureCap = CONFIG.getOrDefault("key.respawnHealth", 11);
		ambientCap = CONFIG.getOrDefault("key.ambientCap", 14);
		waterCreatureCap = CONFIG.getOrDefault("key.waterCreature", 6);
		waterAmbientCap = CONFIG.getOrDefault("key.waterAmbient", 21);
		undergroundWaterCreatureCap = CONFIG.getOrDefault("key.undergroundWaterCreatureCap", 4);
		LOGGER.info("All " + configs.getConfigsList().size() + " have been set properly");
	}
}
