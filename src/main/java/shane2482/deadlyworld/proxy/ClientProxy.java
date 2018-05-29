package shane2482.deadlyworld.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityFurnaceBasic;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityWorkstation;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityBasaltChest;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodChest;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodCrate;
import shane2482.deadlyworld.init.ModArmor;
import shane2482.deadlyworld.init.ModTools;
import shane2482.deadlyworld.library.GuiHandler;
import shane2482.deadlyworld.library.ModSoundHandler;
import shane2482.deadlyworld.render.AlloyFurnaceRenderer;
import shane2482.deadlyworld.render.BasaltChestRenderer;
import shane2482.deadlyworld.render.PlywoodChestRenderer;

public class ClientProxy implements iproxy {
	//Using a map for the resorce location
	private static final Map<ItemStack, ModelResourceLocation> MODEL_LOCATIONS = new HashMap<ItemStack, ModelResourceLocation>();
	

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// Custom Resource Location
		for (Map.Entry<ItemStack, ModelResourceLocation> entry : MODEL_LOCATIONS.entrySet()) {
			ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(),
					entry.getValue());
		}
	}

	@Override
	public void Init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}

	///Call register Render
	@Override
	public void addRenderRegister(ItemStack stack, ResourceLocation location, String variant) {
		MODEL_LOCATIONS.put(stack, new ModelResourceLocation(location, variant));

	

}
