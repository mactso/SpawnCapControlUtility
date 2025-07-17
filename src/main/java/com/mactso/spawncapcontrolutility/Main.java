package com.mactso.spawncapcontrolutility;

import com.mactso.spawncapcontrolutility.config.MyConfig;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("spawncapcontrolutility")
public class Main {

	    public static final String MODID = "spawncapcontrolutility"; 
	    
	    public Main(FMLJavaModLoadingContext context)
	    {
			context.registerConfig(ModConfig.Type.COMMON, MyConfig.COMMON_SPEC);
	    	System.out.println(MODID + ": Registering Mod.");
	    }
	    
//		@SubscribeEvent 
//		public void preInit (final FMLCommonSetupEvent event) {
//			MinecraftForge.EVENT_BUS.register(new HandleServerAboutToStart());
//		}  

}
