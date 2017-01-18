package shane2482.deadlyworld.items.tools;

import net.minecraft.item.ItemSpade;
import shane2482.deadlyworld.DeadlyWorld;
import shane2482.deadlyworld.init.ModTools;

public class shovel extends ItemSpade {
	public shovel(ToolMaterial material, String Name, String RegName) {
		super(material);
		setUnlocalizedName(Name);
		setRegistryName(RegName);
		setCreativeTab(DeadlyWorld.item);
		ModTools.TOOLS.put(RegName, this);
	}
}
