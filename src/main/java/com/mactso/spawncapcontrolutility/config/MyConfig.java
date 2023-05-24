package com.mactso.spawncapcontrolutility.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;
import com.mojang.datafixers.util.Pair;

import net.minecraft.entity.SpawnGroup;


// Refactored "ModConfigs.java"

public class MyConfig {

	private static final Logger LOGGER = LogManager.getLogger();

	public static SimpleConfig CONFIG;
	private static ModConfigProvider configs;

	private static String spawnCategoriesConfig;

	private static String spawnCategoriesDefault = 
			SpawnGroup.MONSTER.getName().toUpperCase() + "," + (SpawnGroup.MONSTER.getCapacity() + 3) + ";" +
			SpawnGroup.CREATURE.getName().toUpperCase() + "," + SpawnGroup.CREATURE.getCapacity() + ";" +
			SpawnGroup.AMBIENT.getName().toUpperCase() + "," + (SpawnGroup.AMBIENT.getCapacity() - 1)  + ";" +
			SpawnGroup.AXOLOTLS.getName().toUpperCase() + "," + SpawnGroup.AXOLOTLS.getCapacity()+ ";" +
			SpawnGroup.UNDERGROUND_WATER_CREATURE.getName().toUpperCase() + ","
					+ SpawnGroup.UNDERGROUND_WATER_CREATURE.getCapacity()+ ";" +
			SpawnGroup.WATER_CREATURE.getName().toUpperCase() + "," + SpawnGroup.WATER_CREATURE.getCapacity()
			+ ";" +
			SpawnGroup.WATER_AMBIENT.getName().toUpperCase() + "," + SpawnGroup.WATER_AMBIENT.getCapacity();

	static String[] spawnCategories;
	
	public static int safeValue(int newValue) {
		if (newValue < 1)
			return 1;
		if (newValue > 350)
			return 350;
		return newValue;
	}


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
					LOGGER.warn("spawncapcontrolutility");
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
		
		spawnCategories = spawnCategoriesConfig.split(";");
		int i;
	}
	
	public static String getSpawnCategoryName(String str) {
		for (String s : spawnCategories) {
			if (s.toUpperCase().contains(str.toUpperCase())) {
				String[] parts = str.split(",", 2);
				return parts[0].trim();
			}
		}
		return "";
	}
	
	public static int getSpawnCategoryMaximum(String str) {
		for (String s : spawnCategories) {
			if (s.toUpperCase().contains(str.toUpperCase())) {
				return splitAndGetInt(s.toUpperCase());
			}
		}
		return -1;
	}
	
	public static int splitAndGetInt(String inputString) {
		try {
			String[] parts = inputString.split(",", 2);
			String secondHalf = parts[1].trim();
			return Integer.parseInt(secondHalf);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			LOGGER.error("Configuration Error for category: " + inputString);
			return -1;
		}
	}

	private static void createConfigs() {
		configs.addKeyValuePair(new Pair<>("key.SpawngroupCapacities", spawnCategoriesDefault), "string");
	}

	private static void assignConfigs() {
		
		spawnCategoriesConfig = CONFIG.getOrDefault("key.SpawngroupCapacities", spawnCategoriesDefault);

		LOGGER.info("All " + configs.getConfigsList().size() + " have been set properly");
	}
}
