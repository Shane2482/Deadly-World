package shane2482.deadlyworld.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.library.Reference;

public class ModArmor extends ItemArmor {
	public static Map<String, Item> ARMOR = new HashMap<String, Item>();

	public ModArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String Name,
			String RegName) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(Name);
		setRegistryName(RegName);
		setCreativeTab(DeadlyWorld.item);
		ARMOR.put(RegName, this);
	}

	/////////////////////////////

	public static ArmorMaterial Basalt = EnumHelper.addArmorMaterial("basalt", Reference.MOD_ID + ":basalt", 15,
			new int[] { 2, 5, 6, 2 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F);

	/////////////////////////////

	public static ItemArmor Basalt_Helmet;
	public static ItemArmor Basalt_Chestplate;
	public static ItemArmor Basalt_Leggins;
	public static ItemArmor Basalt_Boots;

	/////////////////////////////
	public static void init() {
		initialize();
		register();

	}

	private static void initialize() {
		Basalt_Helmet = new ModArmor(Basalt, 1, EntityEquipmentSlot.HEAD, "basalt_helmet", "item_basalt_helmet");
		Basalt_Chestplate = new ModArmor(Basalt, 1, EntityEquipmentSlot.CHEST, "basalt_chestplate",
				"item_basalt_chestplate");
		Basalt_Leggins = new ModArmor(Basalt, 2, EntityEquipmentSlot.LEGS, "basalt_leggings", "item_basalt_leggings");
		Basalt_Boots = new ModArmor(Basalt, 1, EntityEquipmentSlot.FEET, "basalt_boots", "item_basalt_boots");

	}

	private static void register() {
		for (Item item : ARMOR.values()) {
			GameRegistry.register(item);
		}

	}

	public static void registerRender() {
		for (Item item : ARMOR.values()) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
