package com.mactso.spawncapcontrolutility.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public class Utility {
	public final static int FOUR_SECONDS = 80;
	public final static int TWO_SECONDS = 40;
	public final static float Pct00 = 0.00f;
	public final static float Pct02 = 0.02f;
	public final static float Pct05 = 0.05f;
	public final static float Pct09 = 0.09f;
	public final static float Pct16 = 0.16f;
	public final static float Pct25 = 0.25f;
	public final static float Pct50 = 0.50f;
	public final static float Pct75 = 0.75f;
	public final static float Pct84 = 0.84f;
	public final static float Pct89 = 0.89f;
	public final static float Pct91 = 0.91f;
	public final static float Pct95 = 0.95f;
	public final static float Pct99 = 0.99f;
	public final static float Pct100 = 1.0f;

	private static final Logger LOGGER = LogManager.getLogger();

	public static void debugMsg(int level, String dMsg) {

//		if (MyConfig.getDebugLevel() > level - 1) {
			LOGGER.info("L" + level + ":" + dMsg);
//		}

	}

	public static void debugMsg(int level, BlockPos pos, String dMsg) {

//		if (MyConfig.getDebugLevel() > level - 1) {
			LOGGER.info("L" + level + " (" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + "): " + dMsg);
//		}

	}

	public static void debugMsg(int level, LivingEntity le, String dMsg) {

//		if (MyConfig.getDebugLevel() > level - 1) {
			LOGGER.info("L" + level + " (" 
					+ le.getBlockPos().getX() + "," 
					+ le.getBlockPos().getY() + ","
					+ le.getBlockPos().getZ() + "): " + dMsg);
//		}

	}

	public static void sendBoldChat(PlayerEntity p, String chatMessage, Formatting textColor) {

		MutableText component = Text.literal(chatMessage).setStyle(Style.EMPTY.withBold(true).withColor(TextColor.fromFormatting(textColor)));
		p.sendMessage(component ); // TODO UUID?

	}

	public static void sendChat(PlayerEntity p, String chatMessage, Formatting textColor) {
		MutableText component = Text.literal(chatMessage).setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(textColor)));
		// TODO how to make colored 
		p.sendMessage(component ); // TODO UUID?

	}

	public static void updateEffect(LivingEntity e, int amplifier, StatusEffect mobEffect, int duration) {
		
		StatusEffectInstance ei = e.getStatusEffect(mobEffect);
		if (amplifier == 10) {
			amplifier = 20; // player "plaid" speed.
		}
		if (ei != null) {
			if (amplifier > ei.getAmplifier()) {
				e.removeStatusEffect(mobEffect);
			}
			if (amplifier == ei.getAmplifier() && ei.getDuration() > 10) {
				return;
			}
			if (ei.getDuration() > 10) {
				return;
			}
			e.removeStatusEffect(mobEffect);
		}
		e.addStatusEffect(new StatusEffectInstance(mobEffect, duration, amplifier, true, true));
		return;
	}

//	public static boolean populateEntityType(EntityType<?> et, ServerWorld serverlevel, BlockPos savePos, int range,
//			int modifier) {
//	
//		boolean isBaby = false;
//		return populateEntityType(et, serverlevel, savePos, range, modifier, isBaby);
//	}

//	public static boolean populateEntityType(EntityType<?> et, ServerWorld level, BlockPos savePos, int range,
//			int modifier, boolean isBaby) {
//		boolean persistant = false;
//		return populateEntityType(et, level, savePos, range, modifier, persistant, isBaby);
//	}

//	public static boolean populateEntityType(EntityType<?> et, ServerWorld serverworld, BlockPos savePos, int range,
//			int modifier, boolean persistant, boolean isBaby) {
//		int numZP;
//		HostileEntity e;
//		numZP = serverworld.random.nextInt(range) - modifier;
//		if (numZP < 0)
//			return false;
//		for (int i = 0; i <= numZP; i++) {
//
//			e = (HostileEntity) et.spawn(serverworld, null, null, null, savePos.north().west(), SpawnReason.NATURAL, true, true);
//
//			if (persistant) 
//				e.setPersistent();
//			e.setBaby(isBaby);
//		}
//		return true;
//	}

//	public static boolean populateXEntityType(EntityType<?> et, ServerWorld level, BlockPos savePos, int X,  boolean isBaby) {
//		HostileEntity e;
//
//		for (int i = 0; i < X; i++) {
//			e = (HostileEntity) et.spawn(level, null, null, null, savePos.north().west(), SpawnReason.NATURAL, true, true);
//			e.setBaby(isBaby);
//		}
//		return true;
//	}

	public static void setName(ItemStack stack, String inString)
	{
		NbtCompound tag = stack.getOrCreateSubNbt("display");
		NbtList list = new NbtList();
		list.add(NbtString.of(inString));
		tag.put("Name", list);
	}

	public static void setLore(ItemStack stack, String inString)
	{
		NbtCompound tag = stack.getOrCreateSubNbt("display");
		NbtList list = new NbtList();
		list.add(NbtString.of(inString));
		tag.put("Lore", list);
	}
	
	public static boolean isNotNearWebs(BlockPos pos, ServerWorld serverworld) {

		if (serverworld.getBlockState(pos).getBlock() == Blocks.COBWEB)
			return true;
		if (serverworld.getBlockState(pos.up()).getBlock() == Blocks.COBWEB)
			return true;
		if (serverworld.getBlockState(pos.down()).getBlock() == Blocks.COBWEB)
			return true;
		if (serverworld.getBlockState(pos.north()).getBlock() == Blocks.COBWEB)
			return true;
		if (serverworld.getBlockState(pos.south()).getBlock() == Blocks.COBWEB)
			return true;
		if (serverworld.getBlockState(pos.east()).getBlock() == Blocks.COBWEB)
			return true;
		if (serverworld.getBlockState(pos.west()).getBlock() == Blocks.COBWEB)
			return true;

		return false;
	}

	public static boolean isOutside(BlockPos pos, ServerWorld serverworld) {

		return serverworld.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos) == pos;
	}

	public static void slowFlyingMotion(LivingEntity le) {

		if ((le instanceof PlayerEntity) && (le.isFallFlying())) {
			PlayerEntity cp = (PlayerEntity) le;
			Vec3d vec = cp.getVelocity();
			Vec3d slowedVec;
			if (vec.y > 0) {
				slowedVec = vec.multiply(0.17, -0.75, 0.17);
			} else {
				slowedVec = vec.multiply(0.17, 1.001, 0.17);
			}
			cp.setVelocity(slowedVec);
		}
	}

}
