package shane2482.deadlyworld.render;

import java.awt.Container;
import java.util.Calendar;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.google.common.primitives.SignedBytes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import shane2482.deadlyworld.blocks.machines.model.ModelAlloyFurnace;
import shane2482.deadlyworld.blocks.machines.tile.TileEntityAlloyFurnace;
import shane2482.deadlyworld.blocks.storage.tiles.TileEntityPlywoodChest;
import shane2482.deadlyworld.library.Reference;

public class AlloyFurnaceRenderer extends TileEntitySpecialRenderer<TileEntityAlloyFurnace> {

	private static final ResourceLocation Basalt_Chest = new ResourceLocation(Reference.MOD_ID + ":" + "textures/entity/furnace/alloy_furnace.png");
	private static final ResourceLocation TEXTURE_CHRISTMAS = new ResourceLocation("textures/entity/chest/christmas.png");
	private ModelAlloyFurnace model;
	EntityItem entItem = null;
	
	
	private boolean isChristmas;

	public AlloyFurnaceRenderer() {
		Calendar calendar = Calendar.getInstance();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
			this.isChristmas = true;
		}

	}
	
	
	

	@Override
	public void renderTileEntityAt(TileEntityAlloyFurnace te, double x, double y, double z, float partialTicks,
			int destroyStage) {
		/*
		int slot = 0; //Example: int slot = 1;
		TileEntityAlloyFurnace tileEntity = (TileEntityAlloyFurnace)te;
		if((entItem == null) || entItem.getEntityItem().getItem() != tileEntity.getStackInSlot(slot).getItem())
		 entItem = new EntityItem(tileEntity.getWorld(), x, y, z, tileEntity.getStackInSlot(slot));
		GL11.glPushMatrix();
		this.entItem.hoverStart = 0.0F;
		render = true;
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.02F, (float)z + 0.3F);
		GL11.glRotatef(180, 0, 1, 1);
		RenderManager.instance.renderEntityWithPosYaw(this.entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		GL11.glPopMatrix();*/
		
		
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;

		i = te.getBlockMetadata();

		if (te instanceof TileEntityAlloyFurnace) {
			this.model = new ModelAlloyFurnace();

			if (destroyStage >= 0) {
				this.bindTexture(DESTROY_STAGES[destroyStage]);
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scale(4.0F, 4.0F, 1.0F);
				GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
				GlStateManager.matrixMode(5888);
			} else if (this.isChristmas) {
				this.bindTexture(TEXTURE_CHRISTMAS);
			}

			else {
				this.bindTexture(Basalt_Chest);
			}

		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();

		if (destroyStage < 0) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}

		GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		int j = 0;

		if (i == 2) {
			j = 180;
		}

		if (i == 3) {
			j = 0;
		}

		if (i == 4) {
			j = 90;
		}

		if (i == 5) {
			j = -90;
		}

		GlStateManager.rotate((float) j, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.0F, 0.0F);
		float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		model.Hinge.rotateAngleY = -(f * ((float) Math.PI / 2F));
		model.renderAll();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (destroyStage >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}

	}
}