package shane2482.deadlyworld.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import shane2482.deadlyworld.blocks.block_lamp;
import shane2482.deadlyworld.blocks.base.blockbase;
import shane2482.deadlyworld.blocks.machines.block_tool_builder;
import shane2482.deadlyworld.blocks.machines.block_workstation;
import shane2482.deadlyworld.blocks.ores.block_metals;
import shane2482.deadlyworld.blocks.ores.block_ore;
import shane2482.deadlyworld.blocks.storage.block_basalt_chest;
import shane2482.deadlyworld.blocks.storage.block_plywood_chest;
import shane2482.deadlyworld.blocks.storage.block_plywood_crate;

public class ModBlocks {
	
	
	// Blocks
	public static Block Plywood;
	public static Block Basalt_Rough;
	public static Block Basalt_Smooth;
	public static Block Basalt_Brick;
	public static Block Basalt_Carved;
	public static Block Cracked_Dirt;
	public static Block Lamp;
	
	

	
	//Ores
	public static Block Ores;
	public static Block Metal_Blocks;

	// Machines
	public static Block Basalt_Furnaceoff;
	public static Block Basalt_Furnaceon;
	public static Block Workstation;
	public static Block Tool_Builder;

	// Storage
	public static Block Plywood_Chest;
	public static Block Basalt_Chest;
	public static Block Plywood_Crate;

	/////////////////////////////
	public static void init() {

		// Blocks
		Plywood = new blockbase(Material.WOOD, "plywood", "block_plywood", 3.0f, 10.0f, "axe", 0);
		Cracked_Dirt = new blockbase(Material.GROUND, "cracked_dirt", "block_cracked_dirt", 1.5f, 5.0f, "shovel", 0);
		Basalt_Rough = new blockbase(Material.ROCK, "basalt_rough", "block_basalt_rough", 5.0f, 20.0f, "pickaxe", 1);
		Basalt_Smooth = new blockbase(Material.ROCK, "basalt_smooth", "block_basalt_smooth", 5.0f, 20.0f, "pickaxe", 1);
		Basalt_Brick = new blockbase(Material.ROCK, "basalt_brick", "block_basalt_brick", 5.0f, 20.0f, "pickaxe", 1);
		Basalt_Carved = new blockbase(Material.ROCK, "basalt_carved", "block_basalt_carved", 5.0f, 20.0f, "pickaxe", 1);
		Lamp = new block_lamp(Material.GLASS, "lamp", "block_lamp", 1.0f, 3.0f, "", 0);
		
		//Ores
		Ores = new block_ore("ore", "block_ore");
		Ores = new block_metals("metal", "block_metal");
		
		// Machines
		//Basalt_Furnaceoff = new block_basalt_furnace(false, "basalt_furnaceoff", "block_basalt_furnaceoff").setCreativeTab(DeadlyWorld.block);
		//Basalt_Furnaceon = new block_basalt_furnace(false, "basalt_furnaceon", "block_basalt_furnaceon");
		Workstation = new block_workstation(Material.WOOD, "workstation", "block_workstation", 3.0f, 10.0f, "axe", 0);
		Tool_Builder = new block_tool_builder(Material.WOOD, "tool_builder", "block_tool_builder", 3.0f, 10.0f, "axe", 0);


		// Storage
		Plywood_Chest = new block_plywood_chest(Material.WOOD, "plywood_chest", "block_plywood_chest", 3.0f, 10.0f, "axe", 0);
		Plywood_Crate = new block_plywood_crate(Material.WOOD, "plywood_crate", "block_plywood_crate", 3.0f, 10.0f, "axe", 0);
		Basalt_Chest = new block_basalt_chest(Material.ROCK, "basalt_chest", "block_basalt_chest", 3.0f, 10.0f, "pickaxe", 0);
		
	}
	
	
}
