package shane2482.deadlyworld.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import shane2482.deadlyworld.DeadlyWorld;

public class itembase extends Item {
	public itembase(String Name, String RegName) {
		this.setUnlocalizedName(Name);
		this.setRegistryName(RegName);
		this.setCreativeTab(DeadlyWorld.item);
		GameRegistry.register(this);
		registerRendering();

	}

	protected void registerRendering() {
		DeadlyWorld.proxy.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
	}

}
