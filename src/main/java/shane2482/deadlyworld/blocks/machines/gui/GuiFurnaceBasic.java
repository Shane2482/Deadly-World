package shane2482.deadlyworld.blocks.machines.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.blocks.machines.container.ContainerAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.container.ContainerFurnaceBasic;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityFurnaceBasic;
import shane2482.deadlyworld.library.Reference;

@SideOnly(Side.CLIENT)
public class GuiFurnaceBasic extends GuiContainer {

	private static final ResourceLocation GUI = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/gui/container/furnace_basic_gui.png");

	private TileEntityFurnaceBasic Te;

	public GuiFurnaceBasic(InventoryPlayer playerInv, TileEntityFurnaceBasic Te) {
		super(new ContainerFurnaceBasic(playerInv, Te, Minecraft.getMinecraft().thePlayer));
		this.Te = Te;
		xSize = 176;
		ySize = 166;

	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("Furnace Basic", new Object[0]), 97, 5, 14277081);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 108, ySize - 96 + 2, 14277081);

		List<String> hoveringText = new ArrayList<String>();

		if (isInRect(guiLeft + 36, guiTop + 28, 12, 53, mouseX, mouseY)) {
			hoveringText.add("Fuel:");
			int Fuel = (int) (getPowerRemainingScaled(101));
			hoveringText.add(Fuel + "%");
		}

		if (isInRect(guiLeft + 96, guiTop + 31, 30, 17, mouseX, mouseY)) {
			hoveringText.add("Progress:");
			int Progress = (int) (getProgressScaled(101));
			hoveringText.add(Progress + "%");
		}

		// If hoveringText is not empty draw the hovering text
		if (!hoveringText.isEmpty()) {
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUI);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if (Te.hasPower()) {
			int i1 = getPowerRemainingScaled(57);
			drawTexturedModalRect(guiLeft + 36, guiTop + 69 - i1, 176, 75 - i1, 17, i1);
		}

		int j1 = getProgressScaled(31);
		drawTexturedModalRect(guiLeft + 96, guiTop + 31, 176, 0, j1 + 1, 17);

	}

	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}
	
	public int getProgressScaled(int i) {
		return (Te.cookTime * i) / Te.SPEED;
	}

	public int getPowerRemainingScaled(int i) {
		return (Te.power * i) / Te.MAXPOWER;
	}

}
