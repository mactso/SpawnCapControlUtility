package com.mactso.spawncapcontrolutility.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.config.MyConfig;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.MobCategory;



public class HandleServerAboutToStart {
	private static final Logger LOGGER = LogManager.getLogger();
	public static void onAboutToStart(MinecraftServer server)
    {


		Field field = null;
		
		try {
			MappingResolver mapping = FabricLoader.getInstance().getMappingResolver();
//			from mappings.jar -> mappings.tiny
			String fieldName = mapping.mapFieldName("intermediary", "net.minecraft.class_1311", "field_6297",
					"I");
			field = MobCategory.class.getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
        LOGGER.info("SpawncapControlUtiity Startup");

		LOGGER.info("Configured new Spawn Category Values");

		for (MobCategory mc : MobCategory.values()) {

			String mn = mc.getName().toUpperCase();
			if (mn.equals("MISC")) {
				LOGGER.info("SpawncapControlUtility: Category " + mc + " should not be changed.");
				continue;
			}
			int mcMax = mc.getMaxInstancesPerChunk();
			LOGGER.info("SpawncapControlUtility: Category " + mn + " has a default maximum of " + mcMax + ".  Checking configuration for override maximum values.");

			if (MyConfig.isSpawnCategoryConfigured(mn)) {
				LOGGER.info("SpawncapControlUtility: Category " + mn + " had no configured overrides.  Keeping maximum of " + mc.getMaxInstancesPerChunk());
				continue;
			} 

			
			int scm = MyConfig.getSpawnCategoryMaximum(mn);
			if ((scm < 1) || (scm > 350)) {
				LOGGER.info("SpawncapControlUtility: Category " + mn + " Maximum value less than 1 or greather than 350.  Ignored");
				continue;
			}

			if (scm == mc.getMaxInstancesPerChunk()) {
				LOGGER.info("SpawncapControlUtility: Category " + mn + " configured maximum same as default value.  No change made.");
				continue;
			}
			
			String changeDirection = "Raised";
			if (scm < mcMax) {
				changeDirection = "Lowered";
			}
			LOGGER.info("SpawncapControlUtility: " + changeDirection + " Category "  + mn + " Maximum Value from " + mcMax + " to " + scm);
			try {
				field.setInt(mc, scm);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

    }
}
