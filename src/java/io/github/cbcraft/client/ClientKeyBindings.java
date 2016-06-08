package io.github.cbcraft.client;

import org.lwjgl.input.Keyboard;

import io.github.cbcraft.CBCraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientKeyBindings {
	public static KeyBinding add;
	public static KeyBinding subtract;
	public static KeyBinding secondParamter;
	
	public static void init() {
		add = new KeyBinding(CBCraft.MODID + ".key.add", Keyboard.KEY_EQUALS, CBCraft.MODID + ".key.categories.cbcraft");
		register(add);
		
		subtract = new KeyBinding(CBCraft.MODID + ".key.subtract", Keyboard.KEY_MINUS, CBCraft.MODID + ".key.categories.cbcraft");
		register(subtract);
		
		secondParamter = new KeyBinding(CBCraft.MODID + ".key.secondParamter", Keyboard.KEY_M, CBCraft.MODID + ".key.categories.cbcraft");
		register(secondParamter);
	}
	
	public static void register(KeyBinding key){
		ClientRegistry.registerKeyBinding(key);
	}
}
