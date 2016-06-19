/*
 * This file is part of Code, Block and Craft.
 * Copyright (c) 2016, GoldSpy98 and bernas-antunes97, All rights reserved.
 *
 * Code, Block and Craft 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Code, Block and Craft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Code, Block and Craft.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

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
