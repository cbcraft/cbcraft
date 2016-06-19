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

/*
 *   /$$$$$$  /$$$$$$$   /$$$$$$                      /$$$$$$   /$$
 *  /$$__  $$| $$__  $$ /$$__  $$                    /$$__  $$ | $$
 * | $$  \__/| $$  \ $$| $$  \__/  /$$$$$$  /$$$$$$ | $$  \__//$$$$$$
 * | $$      | $$$$$$$ | $$       /$$__  $$|____  $$| $$$$   |_  $$_/
 * | $$      | $$__  $$| $$      | $$  \__/ /$$$$$$$| $$_/     | $$
 * | $$    $$| $$  \ $$| $$    $$| $$      /$$__  $$| $$       | $$ /$$
 * |  $$$$$$/| $$$$$$$/|  $$$$$$/| $$     |  $$$$$$$| $$       |  $$$$/
 *  \______/ |_______/  \______/ |__/      \_______/|__/        \___/
 */

package io.github.cbcraft;

import io.github.cbcraft.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = CBCraft.MODID,
		name = CBCraft.MODNAME,
		version = CBCraft.VERSION,
		dependencies = "required-after:Forge@[11.15.1.1722,);",
		acceptedMinecraftVersions = "[1.8.9]"
)
public class CBCraft {
	public static final String MODID = "cbcraft";
	public static final String MODNAME = "Code Block and Craft";
	public static final String VERSION = "1.0.0";
	
	@Instance(CBCraft.MODID)
	public static CBCraft instance;
	
	@SidedProxy(clientSide = "io.github.cbcraft.client.ClientProxy", serverSide = "io.github.cbcraft.server.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		CBCraft.proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		CBCraft.proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		CBCraft.proxy.postInit(e);
	}
}
