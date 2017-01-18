package shane2482.deadlyworld.items.tools;

import net.minecraft.item.ItemHoe;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.init.ModTools;

public class hoe extends ItemHoe {
	public hoe(ToolMaterial material, String Name, String RegName) {
		super(material);
		setUnlocalizedName(Name);
		setRegistryName(RegName);
		setCreativeTab(DeadlyWorld.item);
		ModTools.TOOLS.put(RegName, this);
	}
}
