package io.github.cbcraft; // directory of the class

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
	public static final String VERSION = "0.1.0";
	
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
