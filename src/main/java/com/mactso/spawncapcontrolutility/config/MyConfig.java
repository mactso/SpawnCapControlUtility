package com.mactso.spawncapcontrolutility.config;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
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


	public static String[] willMonsterMountBoat;
	public static String[] willMonsterNotHitBoat;
	public static String[] willMonsterNotLeaveBoat;

	
	public static boolean isWillMonsterMountBoat(String classname)
	{	
		for (String mod : willMonsterMountBoat) {
			if (classname.contains(mod)) {
				return true;
			}
		}
		return false;
	}

	
	
	public static boolean isWillMonsterNotHitBoat(String classname)
	{	
		for (String mod : willMonsterNotHitBoat) {
			if (classname.contains(mod)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isWillMonsterNotLeaveBoat(String classname)
	{	
		for (String mod : willMonsterNotLeaveBoat) {
			if (classname.contains(mod)) {
				return true;
			}
		}
		return false;
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == MyConfig.COMMON_SPEC) {
			bakeConfig();
		}
	}

	
	
	public static void bakeConfig() {
		willMonsterMountBoat  = extract(COMMON.willMonsterMountBoat.get());
		willMonsterNotHitBoat = extract(COMMON.willMonsterNotHitBoat.get());
		willMonsterNotLeaveBoat = extract(COMMON.willMonsterNotLeaveBoat.get());
	}

	private static String[] extract(List<? extends String> value)
	{
		return value.toArray(new String[value.size()]);
	}

	
	public static class Common {
		List<String> willMonsterMountBoatList = Arrays.asList("minecraft:zombie_villager","minecraft:vex");
		List<String> willMonsterNotHitBoatList = Arrays.asList("minecraft:zombie_villager","minecraft:zombie","minecraft:creeper","minecraft:skeleton","nasty:skeleton", "minecraft:vex");
		List<String> willMonsterNotLeaveBoatList = Arrays.asList("minecraft:zombie_villager");

		public final ConfigValue<List<? extends String>> willMonsterMountBoat;
		public final ConfigValue<List<? extends String>> willMonsterNotHitBoat;
		public final ConfigValue<List<? extends String>> willMonsterNotLeaveBoat;

		
		
		public Common(ForgeConfigSpec.Builder builder) {
			String baseTrans = Main.MODID + ".config.";

			willMonsterMountBoat = builder
					.comment("Checked Mods Name List")
					.translation(Main.MODID + ".config." + "willMonsterMountBoat")
					.defineList("willMonsterMountBoat", willMonsterMountBoatList, Common::isString);

			willMonsterNotLeaveBoat = builder
					.comment("Checked Mods Name List")
					.translation(Main.MODID + ".config." + "willMonsterNotLeaveBoat")
					.defineList("willMonsterNotLeaveBoat", willMonsterNotLeaveBoatList, Common::isString);
			
			willMonsterNotHitBoat = builder
					.comment("Checked Mods Name List")
					.translation(Main.MODID + ".config." + "willMonsterNotHitBoat")
					.defineList("willMonsterNotHitBoat", willMonsterNotHitBoatList, Common::isString);

		}
		
		public static boolean isString(Object o)
		{
			return (o instanceof String);
		}
	}

}
