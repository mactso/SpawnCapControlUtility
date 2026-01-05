package com.mactso.spawncapcontrolutility.commands;

import com.mojang.brigadier.CommandDispatcher;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;

public class MyCommands {

	private final static int MAGIC_NUMBER = 289; // See NaturalSpawner.MAGIC_NUMBER
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("spawncapinfo").executes(ctx -> info(ctx.getSource())));
	}

	private static int info(CommandSourceStack source) {
	    ServerPlayer player = source.getPlayer();
	    if (player == null) {
	        source.sendFailure(Component.literal("This command must be run by a player in a world, not from the console."));
	        return 0;
	    }
	    // ---- SPAWN STATE CAPS (cached, actual values used) ----
	    ServerLevel level = player.level();
	    NaturalSpawner.SpawnState spawnState = level.getChunkSource().getLastSpawnState();

	    if (spawnState == null) {
	        source.sendFailure(Component.literal("SpawnState not available yet (no spawn cycle has run)."));
	        return 0;
	    }

	    Object2IntMap<MobCategory> counts = spawnState.getMobCategoryCounts();
	    int eligibleChunks = spawnState.getSpawnableChunkCount();

	    // Loop over all MobCategory values
        source.sendSuccess(() -> Component.literal(
	            "[ SpawncapControlInfo ]"
	        ), false);
	    for (MobCategory category : MobCategory.values()) {
	        if (category == MobCategory.MISC) continue; // skip MISC if desired

	        int enumMax = category.getMaxInstancesPerChunk();
	        int count = counts.getInt(category);
	        int computedCap = enumMax * eligibleChunks / MAGIC_NUMBER;

	        source.sendSuccess(() -> Component.literal(
	            " [" + category + "] Configured=" + enumMax +
	            "  Actual Count=" + count +
	            "  ComputedCap=" + computedCap
	        ), false);
	    }

	    return 1;
	}
}
