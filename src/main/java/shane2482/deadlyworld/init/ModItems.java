package shane2482.deadlyworld.init;

import net.minecraft.item.Item;
import shane2482.deadlyworld.items.itembase;
import shane2482.deadlyworld.items.item_circuit;
import shane2482.deadlyworld.items.item_ingots;

public class ModItems {

	/////////////////////////////

	public static Item Circuits;
	public static Item Wood_Dust;
	public static Item Dirt_Clump;
	
	//Ingots
	public static Item Ingot_Copper;
	public static Item Ingot_Tin;
	public static Item Ingot_Aluminum; 
	public static Item Ingot_Nickel; 
	public static Item Ingot_Tungsten;
	
	//Alloys
	public static Item Ingot_Bronze;

	/////////////////////////////

	public static void init() {
		Circuits = new item_circuit("circuit", "item_circuit");
		Wood_Dust = new itembase("wood_dust", "item_wood_dust");
		Dirt_Clump = new itembase("dirt_clump", "item_dirt_clump");
		
		//Ingots
		Ingot_Copper = new itembase("ingot_copper", "item_ingot_copper");
		Ingot_Tin = new itembase("ingot_tin", "item_ingot_tin");
		Ingot_Aluminum = new itembase("ingot_aluminum", "item_ingot_aluminum");
		Ingot_Nickel = new itembase("ingot_nickel", "item_ingot_nickel");
		Ingot_Tungsten = new itembase("ingot_tungsten", "item_ingot_tungsten");
		
		//Alloys
		Ingot_Bronze = new itembase("ingot_bronze", "item_ingot_bronze");
	}

}
