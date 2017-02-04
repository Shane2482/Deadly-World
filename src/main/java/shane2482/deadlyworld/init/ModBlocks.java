package shane2482.deadlyworld.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.blocks.block_lamps;
import shane2482.deadlyworld.blocks.block_lamps_extras;
import shane2482.deadlyworld.blocks.base.blockbase;
import shane2482.deadlyworld.blocks.machines.block_alloy_furnace;
import shane2482.deadlyworld.blocks.machines.block_furnace_basic;
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
	public static Block Lamps;
	public static Block LampsOn;
	public static Block LampsExtras;
	public static Block LampsOnExtras;
	
	
	

	
	//Ores
	public static Block Ores;
	public static Block Metal_Blocks;

	// Machines
	public static Block Furnace_Basic;
	public static Block Furnace_BasicOn;
	public static Block AlloyFurnace;
	public static Block AlloyFurnaceOn;
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
		Lamps = new block_lamps(Material.GLASS, "lamps", "block_lamps", 1.0f, 3.0f, "", 0, false);
		LampsOn = new block_lamps(Material.GLASS, "lampson", "block_lampson", 1.0f, 3.0f, "", 0, true).setCreativeTab(DeadlyWorld.block);
		LampsExtras = new block_lamps_extras(Material.GLASS, "lamps_extras", "block_lamps_extras", 1.0f, 3.0f, "", 0, false);
		LampsOnExtras = new block_lamps_extras(Material.GLASS, "lampson_extras", "block_lampson_extras", 1.0f, 3.0f, "", 0, true).setCreativeTab(DeadlyWorld.block);
		
		
		//Ores
		Ores = new block_ore("ore", "block_ore");
		Ores = new block_metals("metal", "block_metal");
		
		// Machines
		Furnace_Basic = new block_furnace_basic(Material.WOOD, "furnace_basic", "block_furnace_basic", 3.0f, 10.0f, "axe", 0, false).setCreativeTab(DeadlyWorld.block);
		Furnace_BasicOn = new block_furnace_basic(Material.WOOD, "furnace_basicon", "block_furnace_basicon", 3.0f, 10.0f, "axe", 0, true);
		
		AlloyFurnace = new block_alloy_furnace(Material.WOOD, "alloy_furnace", "block_alloy_furnace", 3.0f, 10.0f, "axe", 0).setCreativeTab(DeadlyWorld.block);
		//AlloyFurnaceOn = new block_alloy_furnace(Material.WOOD, "alloy_furnaceon", "block_alloy_furnaceon", 3.0f, 10.0f, "axe", 0, true);
		
		Workstation = new block_workstation(Material.WOOD, "workstation", "block_workstation", 3.0f, 10.0f, "axe", 0);
		Tool_Builder = new block_tool_builder(Material.WOOD, "tool_builder", "block_tool_builder", 3.0f, 10.0f, "axe", 0);


		// Storage
		Plywood_Chest = new block_plywood_chest(Material.WOOD, "plywood_chest", "block_plywood_chest", 3.0f, 10.0f, "axe", 0);
		Plywood_Crate = new block_plywood_crate(Material.WOOD, "plywood_crate", "block_plywood_crate", 3.0f, 10.0f, "axe", 0);
		Basalt_Chest = new block_basalt_chest(Material.ROCK, "basalt_chest", "block_basalt_chest", 3.0f, 10.0f, "pickaxe", 0);
		
	}
	
	
}
