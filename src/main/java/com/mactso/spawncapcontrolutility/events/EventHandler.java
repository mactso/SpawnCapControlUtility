package com.mactso.spawncapcontrolutility.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.config.ModConfigs;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.MinecraftServer;




public class EventHandler {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void onAboutToStart(MinecraftServer server)
    {
//		GlowParticle
		MappingResolver mapping = FabricLoader.getInstance().getMappingResolver();
        String fieldname = mapping.mapFieldName("intermediary", "net.minecraft.class_1311", "field_6297", "I");		
    	Field field = null;
        try {
            field = SpawnGroup.class.getDeclaredField(fieldname);
            field.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (field == null) {
        	return;  // mod failed to reflect
        }

        LOGGER.info("SpawncapControlUtiity Startup");
        LOGGER.info("Mob Category (Spawn Group)  List");

		for (SpawnGroup m : SpawnGroup.values()) {
			String n = m.getName().toUpperCase();
			int c = m.getCapacity();
        	LOGGER.info("SpawnGroup: " + n + " capacity:"+c+".");
        }
        try {
            field.setInt(SpawnGroup.MONSTER, ModConfigs.getMonsterCap());
            field.setInt(SpawnGroup.CREATURE, ModConfigs.getCreatureCap());
            field.setInt(SpawnGroup.AMBIENT, ModConfigs.getAmbientCap());
            field.setInt(SpawnGroup.WATER_CREATURE, ModConfigs.getWaterCreatureCap());
            field.setInt(SpawnGroup.WATER_AMBIENT, ModConfigs.getWaterAmbientCap());
            field.setInt(SpawnGroup.UNDERGROUND_WATER_CREATURE, ModConfigs.getUndergroundWaterCreatureCap());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		for (SpawnGroup m : SpawnGroup.values()) {
			String n = m.getName().toUpperCase();
			int c = m.getCapacity();
        	LOGGER.info("SpawnGroup: " + n + " new capacity:"+c+".");
        }        
    }
}
