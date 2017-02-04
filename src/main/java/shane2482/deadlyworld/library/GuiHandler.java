package shane2482.deadlyworld.library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import shane2482.deadlyworld.blocks.machines.container.ContainerAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.container.ContainerFurnaceBasic;
import shane2482.deadlyworld.blocks.machines.container.ContainerToolBuilder;
import shane2482.deadlyworld.blocks.machines.container.ContainerWorkstation;
import shane2482.deadlyworld.blocks.machines.gui.GuiAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.gui.GuiFurnaceBasic;
import shane2482.deadlyworld.blocks.machines.gui.GuiToolBuilder;
import shane2482.deadlyworld.blocks.machines.gui.GuiWorkstation;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityFurnaceBasic;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityWorkstation;
import shane2482.deadlyworld.blocks.storage.container.ContainerPlywoodCrate;
import shane2482.deadlyworld.blocks.storage.gui.GuiBasaltChest;
import shane2482.deadlyworld.blocks.storage.gui.GuiPlywoodChest;
import shane2482.deadlyworld.blocks.storage.gui.GuiPlywoodCrate;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityBasaltChest;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodChest;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodCrate;

public class GuiHandler implements IGuiHandler {

	public static final int Plywood_Chest = 0;
	public static final int Basalt_Chest = 1;
	public static final int Furnace_Basic = 2;
	public static final int Workstation = 3;
	public static final int Plywood_Crate = 4;
	public static final int Tool_Builder = 5;
	public static final int Alloy_Furnace = 6;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == Plywood_Chest) {
			return new ContainerChest(player.inventory,
					(TileEntityPlywoodChest) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		
		if (ID == Basalt_Chest) {
			return new ContainerChest(player.inventory,
					(TileEntityBasaltChest) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		
		if (ID == Furnace_Basic) {
			return new ContainerFurnaceBasic(player.inventory,
					(TileEntityFurnaceBasic) world.getTileEntity(new BlockPos(x, y, z)), player);
		}

		if (ID == Workstation) {
			return new ContainerWorkstation(player.inventory,
					(TileEntityWorkstation) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Plywood_Crate) {
			return new ContainerPlywoodCrate(player.inventory,
					(TileEntityPlywoodCrate) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Tool_Builder) {
			return new ContainerToolBuilder(player.inventory, world, (new BlockPos(x, y, z)));
		}
		if (ID == Alloy_Furnace) {
			return new ContainerAlloyFurnace(player.inventory,
					(TileEntityAlloyFurnace) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		
		return null;

	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == Plywood_Chest) {
			return new GuiPlywoodChest(player.inventory,
					(TileEntityPlywoodChest) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		if (ID == Basalt_Chest) {
			return new GuiBasaltChest(player.inventory,
					(TileEntityBasaltChest) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		if (ID == Furnace_Basic) {
			return new GuiFurnaceBasic(player.inventory,
					(TileEntityFurnaceBasic) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Workstation) {
			return new GuiWorkstation(player.inventory,
					(TileEntityWorkstation) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Plywood_Crate) {
			return new GuiPlywoodCrate(player.inventory,
					(TileEntityPlywoodCrate) world.getTileEntity(new BlockPos(x, y, z)));
		}

		if (ID == Tool_Builder) {
			return new GuiToolBuilder(player.inventory, world);
		}
		
		if (ID == Alloy_Furnace) {
			return new GuiAlloyFurnace(player.inventory,
					(TileEntityAlloyFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		}
		
		return null;

	}

}
