package com.msandro.skyfabricator;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

public class Skyfabricator implements ModInitializer {
	public static final String MOD_ID = "skyfabricator";

	@Override
	public void onInitialize() {
		ModRegistry.init();
	}

	public static Identifier id(String id) {
		return new Identifier(MOD_ID, id);
	}

	public static Identifier identifier(String path)
	{
		return new Identifier(MOD_ID, path);
	}
}
