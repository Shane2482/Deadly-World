package shane2482.deadlyworld;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import shane2482.deadlyworld.creativetabs.tabblocks;
import shane2482.deadlyworld.creativetabs.tabitems;
import shane2482.deadlyworld.init.ModArmor;
import shane2482.deadlyworld.init.ModBlocks;
import shane2482.deadlyworld.init.ModItems;
import shane2482.deadlyworld.init.ModTools;
import shane2482.deadlyworld.library.RecipeHandler;
import shane2482.deadlyworld.library.Reference;
import shane2482.deadlyworld.proxy.iproxy;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSION)
public class DeadlyWorld {
	@Mod.Instance(Reference.MOD_ID)
	public static DeadlyWorld instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static iproxy proxy;

	public static final CreativeTabs block = new tabblocks(12, "dpblocks");
	public static final CreativeTabs item = new tabitems(13, "dpitems");

	/////////////////////////////
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModItems.init();
		ModTools.init();
		ModArmor.init();
		ModBlocks.init();
		proxy.preInit(event);
		
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		RecipeHandler.registerSmeltingRecipes();
		RecipeHandler.registerCraftingRecipes();
		proxy.Init(event);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}