package com.mactso.spawncapcontrolutility.config;
//package com.mactso.spawncapcontrolutility.config;
//
//import java.util.List;
//
//import org.apache.commons.lang3.tuple.Pair;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.mactso.spawncapcontrolutility.Main;
//
//import net.minecraftforge.common.ForgeConfigSpec;
//import net.minecraftforge.common.ForgeConfigSpec.IntValue;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.event.config.ModConfigEvent;
//
//@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class MyConfig {
//
//	static {
//
//		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
//		COMMON_SPEC = specPair.getRight();
//		COMMON = specPair.getLeft();
//	}
//
//	private static final Logger LOGGER = LogManager.getLogger();
//	public static final Common COMMON;
//	public static final ForgeConfigSpec COMMON_SPEC;
//
//
//	public static int getMonsterCap() {
//		return monsterCap;
//	}
//
//
//
//	public static void setMonsterCap(int monsterCap) {
//		MyConfig.monsterCap = monsterCap;
//	}
//
//
//
//	public static int getCreatureCap() {
//		return creatureCap;
//	}
//
//
//
//	public static void setCreatureCap(int creatureCap) {
//		MyConfig.creatureCap = creatureCap;
//	}
//
//
//
//	public static int getAmbientCap() {
//		return ambientCap;
//	}
//
//
//
//	public static void setAmbientCap(int ambientCap) {
//		MyConfig.ambientCap = ambientCap;
//	}
//
//
//
//	public static int getWaterCreatureCap() {
//		return waterCreatureCap;
//	}
//
//
//
//	public static void setWaterCreatureCap(int waterCreatureCap) {
//		MyConfig.waterCreatureCap = waterCreatureCap;
//	}
//
//
//
//	public static int getWaterAmbientCap() {
//		return waterAmbientCap;
//	}
//
//
//
//	public static void setWaterAmbientCap(int waterAmbientCap) {
//		MyConfig.waterAmbientCap = waterAmbientCap;
//	}
//
//
//	public static int getUndergroundWaterCreatureCap() {
//		return undergroundWaterCreatureCap;
//	}
//
//
//
//	public static void setUndergroundWaterCreatureCap(int undergroundWaterCreatureCap) {
//		MyConfig.undergroundWaterCreatureCap = undergroundWaterCreatureCap;
//	}
//
//
//	public static int monsterCap;
//	public static int creatureCap;
//	public static int ambientCap;
//	public static int waterCreatureCap;
//	public static int waterAmbientCap;	
//	public static int undergroundWaterCreatureCap;		
//	
//	@SubscribeEvent
//	public static void onModConfigEvent(final ModConfigEvent configEvent) {
//		if (configEvent.getConfig().getSpec() == MyConfig.COMMON_SPEC) {
//			bakeConfig();
//		}
//	}
//
//	
//	
//	public static void bakeConfig() {
//		monsterCap  = COMMON.monsterCap.get();
//		creatureCap = COMMON.creatureCap.get();
//		ambientCap = COMMON.ambientCap.get();
//		waterCreatureCap = COMMON.waterCreatureCap.get();
//		waterAmbientCap = COMMON.waterAmbientCap.get();
//		undergroundWaterCreatureCap = COMMON.undergroundWaterCreatureCap.get();
//	}
//
//
//	
//	public static class Common {
//
//		public final IntValue monsterCap;
//		public final IntValue creatureCap;
//		public final IntValue ambientCap;
//		public final IntValue waterCreatureCap;
//		public final IntValue waterAmbientCap;
//		public final IntValue undergroundWaterCreatureCap;
//		
//		public Common	(ForgeConfigSpec.Builder builder) {
//
//
//			monsterCap = builder.comment("Max Monsters (zombie,skeleton, creeper, etc.)")
//					.translation(Main.MODID + ".config." + "monsterCap")
//					.defineInRange("monsterCap", () -> 75, 1, 210);
//			
//			creatureCap = builder.comment("MaxCreatures (sheep, cows)")
//					.translation(Main.MODID + ".config." + "creatureCap")
//					.defineInRange("creatureCap", () -> 11, 1, 210);
//
//
//			ambientCap= builder.comment("Max Ambient (bats)")
//					.translation(Main.MODID + ".config." + "ambientCap")
//					.defineInRange("ambientCap", () -> 10, 1, 210);
//
//
//			waterCreatureCap= builder.comment("Max water creature (squid, dolphins)")
//					.translation(Main.MODID + ".config." + "waterCreatureCap")
//					.defineInRange("waterCreatureCap", () -> 7, 1, 210);
//
//			waterAmbientCap= builder.comment("Max Water Ambient (Tropical Fish)")
//					.translation(Main.MODID + ".config." + "waterAmbientCap")
//					.defineInRange("waterAmbientCap", () -> 19, 1, 210);
//
//			undergroundWaterCreatureCap= builder.comment("Max Water Creature (Glow Squid)")
//					.translation(Main.MODID + ".config." + "undergroundWaterCreatureCap")
//					.defineInRange("undergroundWaterCreatureCap", () -> 3, 1, 210);
//			
//		}
//
//		private static String[] extract(List<? extends String> value)
//		{
//			return value.toArray(new String[value.size()]);
//		}
//		
//		public static boolean isString(Object o)
//		{
//			return (o instanceof String);
//		}
//	}
//
//}
