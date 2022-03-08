package com.msandro.skyfabricator;

import com.msandro.skyfabricator.block.kiln.KilnScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;


public class ClientMod implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ModRegistry.KILN_SCREEN_HANDLER, KilnScreen::new);
	}
}
