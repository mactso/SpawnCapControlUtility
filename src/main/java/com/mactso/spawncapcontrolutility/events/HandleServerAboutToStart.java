package com.mactso.spawncapcontrolutility.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.config.MyConfig;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;




public class HandleServerAboutToStart {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void onAboutToStart(MinecraftServer server)
    {


//		from mappings.jar -> mappings.tiny
    	Field field = null;
        try {
            field = SpawnGroup.class.getDeclaredField("field_6297"); 
            field.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (field == null) {
            try {
                field = SpawnGroup.class.getDeclaredField("capacity");
                field.setAccessible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
//                return;
            }
        	
        }
        if (field == null) {
        	return;  // mod failed to reflect 
        }
        LOGGER.info("SpawncapControlUtiity Startup");
        
		LOGGER.info("Configured new Spawn Group Values");


		for (SpawnGroup mc : SpawnGroup.values()) {
			int i = 0;
			String mn = mc.getName().toUpperCase();
			if (mn.equals("MISC")) {
				LOGGER.info("SpawncapControlUtility: Category " + mc + " should not be changed.");
				continue;
			}
			int mcMax = mc.getCapacity();
			LOGGER.info("SpawncapControlUtility: Category " + mn + " has a default maximum of " + mcMax + ".  Checking configuration for override maximum values.");

			String scn = MyConfig.getSpawnCategoryName(mn);
			if (scn.equals("")) {
				LOGGER.info("SpawncapControlUtility: Category " + mn + " had no configured overrides.  Keeping maximum of " + mc.getCapacity());
				continue;
			} 

			
			int scm = MyConfig.getSpawnCategoryMaximum(mn);
			if ((scm < 1) || (scm > 350)) {
				LOGGER.info("SpawncapControlUtility: Category " + scn + " Maximum value less than 1 or greather than 350.  Ignored");
				continue;
			}

			if (scm == mc.getCapacity()) {
				LOGGER.info("SpawncapControlUtility: Category " + mn + " configured maximum same as default value.  No change made.");
				continue;
			}
			
			String changeDirection = "Raised";
			if (scm < mcMax) {
				changeDirection = "Lowered";
			}
			LOGGER.info("SpawncapControlUtility: " + changeDirection + " Category "  + scn + " Maximum Value from " + mcMax + " to " + scm);
			try {
				field.setInt(mc, scm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}

		int end = 0;
    }
}
