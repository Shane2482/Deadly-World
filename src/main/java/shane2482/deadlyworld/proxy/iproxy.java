package shane2482.deadlyworld.proxy;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.storage.block_plywood_chest;
import shane2482.deadlyworld.library.GuiHandler;
import shane2482.deadlyworld.tiles.TileEntityBasaltChest;
import shane2482.deadlyworld.tiles.TileEntityPlywoodChest;

public interface iproxy {

	public void addRenderRegister(ItemStack stack, ResourceLocation location, String variant);

	public void preInit(FMLPreInitializationEvent event);

	public void Init(FMLInitializationEvent event);

	public void postInit(FMLPostInitializationEvent event);

}
