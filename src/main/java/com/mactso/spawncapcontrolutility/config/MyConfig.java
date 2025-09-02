package com.mactso.spawncapcontrolutility.config;

import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;
import com.mactso.spawncapcontrolutility.util.Utility;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.MobCategory;


// Refactored "ModConfigs.java"

public class MyConfig {

	private static final Logger LOGGER = LogManager.getLogger();

	public static SimpleConfig CONFIG;
	private static ModConfigProvider configs;

	private static String spawnCategoriesConfig;

	private static String spawnCategoriesDefault = 
			MobCategory.MONSTER.getName().toUpperCase() + "," + (MobCategory.MONSTER.getMaxInstancesPerChunk() + 3) + ";" +
			MobCategory.CREATURE.getName().toUpperCase() + "," + MobCategory.CREATURE.getMaxInstancesPerChunk() + ";" +
			MobCategory.AMBIENT.getName().toUpperCase() + "," + (MobCategory.AMBIENT.getMaxInstancesPerChunk() - 1)  + ";" +
			MobCategory.AXOLOTLS.getName().toUpperCase() + "," + MobCategory.AXOLOTLS.getMaxInstancesPerChunk() + ";" +
			MobCategory.UNDERGROUND_WATER_CREATURE.getName().toUpperCase() + ","
					+ MobCategory.UNDERGROUND_WATER_CREATURE.getMaxInstancesPerChunk()+ ";" +
			MobCategory.WATER_CREATURE.getName().toUpperCase() + "," + MobCategory.WATER_CREATURE.getMaxInstancesPerChunk()
			+ ";" +
			MobCategory.WATER_AMBIENT.getName().toUpperCase() + "," + MobCategory.WATER_AMBIENT.getMaxInstancesPerChunk();

	static String[] spawnCategories;
	
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
		

    public static boolean isSpawnCategoryConfigured(String str) {
        String category = "";
        int idx;

        Utility.debugMsg(1, "Info: Bad Spawn Categories Config Entry: " + str);
        
        for (String s : spawnCategories) {
            idx = s.indexOf(',');
            if (idx == -1) {
                category = "";
                Utility.debugMsg(0, "Error: Bad Spawn Categories Config Entry: " + s);
            } else {
                category = s.substring(0, idx).trim();
            }

            if (category.equals(str)) {
                return true;
            }
        }

        return false;
    }
	
	public static int getSpawnCategoryMaximum(String str) {
		for (String s : spawnCategories) {
			if (s.toUpperCase().contains(str.toUpperCase())) {
				return splitAndGetInt(s.toUpperCase());
			}
		}
		return -1;
	}
	


	private static void createConfigs() {
		configs.addKeyValuePair(new Pair<>("key.SpawngroupCapacities", spawnCategoriesDefault), "string");
	}

	private static void assignConfigs() {
		
		spawnCategoriesConfig = CONFIG.getOrDefault("key.SpawngroupCapacities", spawnCategoriesDefault);

		LOGGER.info("All " + configs.getConfigsList().size() + " have been set properly");
	}
}
