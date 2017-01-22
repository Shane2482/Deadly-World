package shane2482.deadlyworld.blocks;

import java.util.Random;

import io.netty.handler.codec.http.HttpHeaders.Names;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class light extends Block  {
    public static final PropertyBool UPDATE = PropertyBool.create("update");
    
    
    public light() {
    
    	super(Material.CLOTH);
    }

   

    @Override
    public IBlockState getStateFromMeta(int meta) {
          return this.getDefaultState().withProperty(UPDATE, (meta == 1));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(UPDATE)? 1 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UPDATE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return this.isCollidable();
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state,IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return 15;
    }

    /*@Override
    public TileEntityPhantomLight createNewTileEntity(World world, int metadata) {
        return new TileEntityPhantomLight();
    }

    @Override
    public void neighborChanged(IBlockState state,World worldIn, BlockPos pos, Block neighborBlock) {
        if (!worldIn.isRemote && state.getValue(UPDATE) && (neighborBlock != Blocks.AIR)) {
            ((TileEntityPhantomLight) worldIn.getTileEntity(pos)).updateAllSources(true);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockState) {
        ((TileEntityPhantomLight) world.getTileEntity(pos)).updateAllSources(true);
        super.breakBlock(world,pos,blockState);
    }*/

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }
}