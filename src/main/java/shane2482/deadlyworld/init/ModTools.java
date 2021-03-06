package shane2482.deadlyworld.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.items.tools.axe;
import shane2482.deadlyworld.library.Reference;

public class ModTools {
	public static Map<String, Item> TOOLS = new HashMap<String, Item>();
	public static final ToolMaterial Basalt = EnumHelper.addToolMaterial(Reference.MOD_ID + ":basalt", 1, 200, 5.0F,
			1.0F, 10);

	/////////////////////////////
	public static ItemPickaxe Basalt_Pickaxe;
	public static axe Basalt_Axe;
	public static ItemHoe Basalt_Hoe;
	public static ItemSpade Basalt_Shovel;
	public static ItemSword Basalt_Sword;

	/////////////////////////////

	public static void init() {
		initialize();
		register();

	}

	private static void initialize() {
		Basalt_Pickaxe = new shane2482.deadlyworld.items.tools.pickaxe(Basalt, "basalt_pickaxe", "item_basalt_pickaxe");
		Basalt_Axe = new shane2482.deadlyworld.items.tools.axe(Basalt, "basalt_axe", "item_basalt_axe");
		Basalt_Hoe = new shane2482.deadlyworld.items.tools.hoe(Basalt, "basalt_hoe", "item_basalt_hoe");
		Basalt_Shovel = new shane2482.deadlyworld.items.tools.shovel(Basalt, "basalt_shovel", "item_basalt_shovel");
		Basalt_Sword = new shane2482.deadlyworld.items.tools.sword(Basalt, "basalt_sword", "item_basalt_sword");
	}

	private static void register() {
		for (Item item : TOOLS.values()) {
			GameRegistry.register(item);
		}

	}

	public static void registerRender() {
		for (Item item : TOOLS.values()) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
