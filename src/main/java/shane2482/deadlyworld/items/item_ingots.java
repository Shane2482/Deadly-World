package shane2482.deadlyworld.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.library.Reference;

public class item_ingots extends itembase

{
	public static final String Bug_Item = Reference.MOD_ID + ".this is a bug";

	public item_ingots(String Name, String RegName) {
		super(Name, RegName);

	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() >= IngotsTypes.values().length ? Bug_Item
				: this.getUnlocalizedName() + "_" + IngotsTypes.values()[stack.getItemDamage()].name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < IngotsTypes.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	protected void registerRendering() {
		for (int i = 0; i < IngotsTypes.values().length; i++) {
			String name = this.getRegistryName() + "_" + IngotsTypes.values()[i].name();
			DeadlyWorld.proxy.addRenderRegister(new ItemStack(this, 1, i), new ResourceLocation(name), "inventory");
		}

	}

	public enum IngotsTypes implements IStringSerializable {

		// Ore Ingots
		Tin("tin", 0), 
		Copper("copper", 1), 
		Aluminum("aluminum", 2), 
		Nickel("nickel", 3), 
		Tungsten("tungsten", 4),

		// Alloys
		Bronze("bronze", 5);

		/////////////////////////////

		private String name;
		private int ID;
		

		private IngotsTypes(String name, int ID) {
			this.name = name;
			this.ID = ID;
			
		}
		

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return ID;
		}

		@Override
		public String toString() {
			return getName();
		}
		
	}

}
