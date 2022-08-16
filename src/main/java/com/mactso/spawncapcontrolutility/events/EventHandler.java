package com.mactso.spawncapcontrolutility.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.config.ModConfigs;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;




public class EventHandler {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void onAboutToStart(MinecraftServer server)
    {
//		GameTestServer g;
		
//		need mixins on these two server classes.
		IntegratedServer i; //
//		forge code
//		   public boolean initServer() 
//			      LOGGER.info("Starting integrated minecraft server version {}", (Object)SharedConstants.getCurrentVersion().getName());
//			      this.setUsesAuthentication(true);
//			      this.setPvpAllowed(true);
//			      this.setFlightAllowed(true);
//			      this.initializeKeyPair();
//	HERE ->>>    if (!net.minecraftforge.server.ServerLifecycleHooks.handleServerAboutToStart(this)) return false;
//			      this.loadLevel();
//			      this.setMotd(this.getSingleplayerName() + " - " + this.getWorldData().getLevelName());
//			      return net.minecraftforge.server.ServerLifecycleHooks.handleServerStarting(this);
//			   }
// fabric code
//	    @Override
//	    public boolean setupServer() {
//	        LOGGER.info("Starting integrated minecraft server version {}", (Object)SharedConstants.getGameVersion().getName());
//	        this.setOnlineMode(true);
//	        this.setPvpEnabled(true);
//	        this.setFlightEnabled(true);
//	        this.generateKeyPair();
// so my mixin should go on this line I think.
//	        this.loadWorld();
//	        this.setMotd(this.getSinglePlayerName() + " - " + this.getSaveProperties().getLevelName());
//	        return true;
//	    }
		
		DedicatedServer d; //
// forge code.
//			initServer()
//		...
//	        SkullBlockEntity.setup(this.getProfileCache(), this.getSessionService(), this);
//	        GameProfileCache.setUsesAuthentication(this.usesAuthentication());
// HERE -> if (!net.minecraftforge.server.ServerLifecycleHooks.handleServerAboutToStart(this)) return false;
//	        LOGGER.info("Preparing level \"{}\"", (Object)this.getLevelIdName());
//	        this.loadLevel();
//	        long j = Util.getNanos() - i;
// fabric code
// package net.minecraft.server.dedicated;
// setupServer() {
// ...
//        long iOException = Util.getMeasuringTimeNano();
//        SkullBlockEntity.setServices(this.getUserCache(), this.getSessionService(), this);
//        UserCache.setUseRemote(this.isOnlineMode());
// my mixin goes here
//        LOGGER.info("Preparing level \"{}\"", (Object)this.getLevelName());
//        this.loadWorld();
//        long l = Util.getMeasuringTimeNano() - iOException;
//
		
		
		
    	Field field = null;
//        String fn = ASMAPI.mapField("f_21586_"); // internal SpawnGroup field : "max"
        try {
            field = SpawnGroup.class.getDeclaredField("field_6297");
            field.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
//            return;
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
