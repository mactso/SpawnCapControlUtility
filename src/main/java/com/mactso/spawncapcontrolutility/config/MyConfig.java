package com.mactso.spawncapcontrolutility.config;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;

import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MyConfig {

	static {

		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	private static final Logger LOGGER = LogManager.getLogger();
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;

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

	public static String[] spawnCategories;

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == MyConfig.COMMON_SPEC) {
			bakeConfig();
		}
	}

	public static void bakeConfig() {

		spawnCategories = toArray(COMMON.spawnCategories.get());

	}

	private static String[] toArray(List<? extends String> value) {
		return isEmpty(value) ? new String[0] : value.toArray(new String[value.size()]);
	}

	private static boolean isEmpty(List<? extends String> value) {
		return value.isEmpty() || (value.size() == 1 && value.get(0).isEmpty());
	}

	public static class Common {


		public final ConfigValue<List<? extends String>> spawnCategories;

		public Common(ForgeConfigSpec.Builder builder) {

			List<String> spawnCategoriesDefault = Arrays.asList(
					MobCategory.MONSTER.getName().toUpperCase() + "," + "73",
					MobCategory.CREATURE.getName().toUpperCase() + "," + MobCategory.CREATURE.getMaxInstancesPerChunk(),
					MobCategory.AMBIENT.getName().toUpperCase() + "," + 14,
					MobCategory.AXOLOTLS.getName().toUpperCase() + "," + MobCategory.AXOLOTLS.getMaxInstancesPerChunk(),
					MobCategory.UNDERGROUND_WATER_CREATURE.getName().toUpperCase() + ","
							+ MobCategory.UNDERGROUND_WATER_CREATURE.getMaxInstancesPerChunk(),
					MobCategory.WATER_CREATURE.getName().toUpperCase() + "," + MobCategory.WATER_CREATURE.getMaxInstancesPerChunk()
							,
					MobCategory.WATER_AMBIENT.getName().toUpperCase() + "," + MobCategory.WATER_AMBIENT.getMaxInstancesPerChunk()
							);

			spawnCategories = builder
					.comment("spawn Categories(MONSTER, CREATURE, ... defaults..., custom pools from other mods")
					.translation(Main.MODID + ".config")
					.defineList("spawnCategories", spawnCategoriesDefault, Common::isString);

		}

		public static boolean isString(Object o) {
			return (o instanceof String);
		}
	}

}
