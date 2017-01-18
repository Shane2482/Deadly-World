package shane2482.deadlyworld.library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import shane2482.deadlyworld.blocks.storage.container.ContainerPlywoodCrate;
import shane2482.deadlyworld.blocks.storage.container.ContainerWorkstation;
import shane2482.deadlyworld.gui.GuiPlywoodChest;
import shane2482.deadlyworld.gui.GuiPlywoodCrate;
import shane2482.deadlyworld.gui.GuiWorkstation;
import shane2482.deadlyworld.tiles.TileEntityPlywoodChest;
import shane2482.deadlyworld.tiles.TileEntityPlywoodCrate;
import shane2482.deadlyworld.tiles.TileEntityWorkstation;

public class GuiHandler implements IGuiHandler {

	public static final int Plywood_Chest = 0;
	public static final int Basalt_Chest = 1;	
	public static final int Basalt_Furnace = 2;
	public static final int Workstation = 3;
	public static final int Plywood_Crate = 4;
	
	

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		
		if (ID == Plywood_Chest) {
			return new ContainerChest(player.inventory,
					(TileEntityPlywoodChest) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		
		if (ID == Workstation) {
			return new ContainerWorkstation(player.inventory,
					(TileEntityWorkstation) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		
		if (ID == Plywood_Crate) {
			return new ContainerPlywoodCrate(player.inventory,
					(TileEntityPlywoodCrate) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;

	}

	
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		
		if (ID == Plywood_Chest) {
			return new GuiPlywoodChest(player.inventory,
					(TileEntityPlywoodChest) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		if (ID == Workstation) {
			return new GuiWorkstation(player.inventory,
					(TileEntityWorkstation) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		
		if (ID == Plywood_Crate) {
			return new GuiPlywoodCrate(player.inventory,
					(TileEntityPlywoodCrate) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		
		return null;

	}

}