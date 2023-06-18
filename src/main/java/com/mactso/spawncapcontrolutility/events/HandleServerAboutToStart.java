package com.mactso.spawncapcontrolutility.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;
import com.mactso.spawncapcontrolutility.config.MyConfig;

import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.coremod.api.ASMAPI;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID)
public class HandleServerAboutToStart {
	private static final Logger LOGGER = LogManager.getLogger();

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onAboutToStart(ServerAboutToStartEvent event) {

		Field field = null;
		String fn = ASMAPI.mapField("f_21586_"); // internal MobCategory field : "max"
		try {
			field = MobCategory.class.getDeclaredField(fn);
			field.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		LOGGER.info("SpawncapControlUtiity Startup");

		LOGGER.info("Configured new Spawn Category Values");

		for (MobCategory mc : MobCategory.values()) {
			int i = 0;
			String mn = mc.getName().toUpperCase();
			if (mn.equals("MISC")) {
				LOGGER.info("SpawncapControlUtility: Category " + mc + " should not be changed.");
				continue;
			}
			int mcMax = mc.getMaxInstancesPerChunk();
			LOGGER.info("SpawncapControlUtility: Category " + mn + " has a default maximum of " + mcMax + ".  Checking configuration for override maximum values.");

			String scn = MyConfig.getSpawnCategoryName(mn);
			if (scn.equals("")) {
				LOGGER.info("SpawncapControlUtility: Category " + mc + " had no configured overrides.  Keeping maximum of " + mc.getMaxInstancesPerChunk());
				continue;
			} 

			
			int scm = MyConfig.getSpawnCategoryMaximum(mn);
			if ((scm < 1) || (scm > 350)) {
				LOGGER.info("SpawncapControlUtility: Category " + scn + " Maximum value less than 1 or greather than 350.  Ignored");
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
