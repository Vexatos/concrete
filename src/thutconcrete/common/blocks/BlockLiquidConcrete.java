package thutconcrete.common.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import thutconcrete.common.ConcreteCore;
import thutconcrete.common.corehandlers.TSaveHandler;
import thutconcrete.common.utils.ISaveable;
import thutconcrete.common.utils.ThreadSafeWorldOperations;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.item.*;
import net.minecraftforge.liquids.IBlockLiquid;
import net.minecraftforge.liquids.ILiquid;

public class BlockLiquidConcrete extends Block16Fluid implements ILiquid
{

	public static Block instance;
	static Material wetConcrete = (new WetConcrete(MapColor.stoneColor));
	Integer[][] data;
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
	public BlockLiquidConcrete(int par1) {
		super(par1, wetConcrete);
		setUnlocalizedName("concreteLiquid");
		this.setResistance((float) 0.0);
		this.setTickRandomly(true);
		this.instance = this;
	}
	
	/////////////////////////////////////////Block Bounds Stuff//////////////////////////////////////////////////////////
	
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	this.setBoundsByMeta(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
		return AxisAlignedBB.getAABBPool().getAABB(0, 0.0, 0, 0, 0.0, 0);
    }
	
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int tickRate(World par1World)
    {
        return 10;
    }

    //*/
    
	@Override
    public void onBlockPlacedBy(World worldObj,int x,int y,int z,EntityLiving entity, ItemStack item){
		worldObj.setBlockMetadataWithNotify(x, y, z, 15, 3);
		//TODO add set colour based on item
		if(data==null){
			setData();
			}
    	this.setTickRandomly(true);
    	super.onBlockPlacedBy(worldObj, x, y, z, entity, item);
    }
	
	@Override
    public void onBlockAdded(World worldObj, int x, int y, int z) {
		if(data==null){
			setData();
			}
    	this.setTickRandomly(true);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity) {
		entity.motionX*=0.5;
		entity.motionZ*=0.5;
		if(par1World.getBlockMetadata(par2, par3, par4)>6)
		entity.motionY*=0.5;
	}
	
	@Override
	public void updateTick(World worldObj, int x, int y, int z, Random par5Random){
		if(data==null){
			setData();
			}
		 super.updateTick(worldObj, x, y, z, par5Random);
		 
	}
	
	private void setData(){

		List<Integer> combinationList = new ArrayList<Integer>();
		List<Integer> desiccantList = new ArrayList<Integer>();

		
		//Rebar to make this colour.
		combinationList.add(BlockRebar.instance.blockID+4096*BlockLiquidREConcrete.instance.blockID);
		//Air to make this colour.
		combinationList.add(4096*BlockLiquidConcrete.instance.blockID);

		//Normal Concrete to make this colour
		
		combinationList.add(BlockLiquidConcrete.instance.blockID + 4096*BlockLiquidConcrete.instance.blockID);
		
		
		combinationList.add(BlockConcrete.instance.blockID+4096*BlockLiquidConcrete.instance.blockID);
		//RE Concrete to make this colour
		combinationList.add(BlockLiquidREConcrete.instance.blockID+4096*BlockLiquidREConcrete.instance.blockID);
		combinationList.add(BlockREConcrete.instance.blockID+4096*BlockLiquidREConcrete.instance.blockID);

		for(int i = 0;i<3;i++){
	//		combinationList.add(BlockLiquidConcrete.instance.blockID+4096*BlockLava.getInstance(i).blockID);
		}


		desiccantList.add(0+4096);

		desiccantList.add(BlockREConcrete.instance.blockID+4096*4);
		
		desiccantList.add(BlockConcrete.instance.blockID+4096*4);

		data = new Integer[][]{
				{
					0,//ID that this returns when meta hits -1, 
					0,//the viscosity factor,
					BlockConcrete.instance.blockID,//a secondary ID that this can turn into used for hardening,
					1,//The hardening differential that prevents things staying liquid forever.,
					0,//a randomness coefficient, this is multiplied by a random 0-10 then added to the hardening differential and viscosity.,
					1,//The will fall of edges factor, this is 0 or 1,
					1,//0 = not colourable, 1 = colourable.
				},
				desiccantList.toArray(new Integer[0]),
				combinationList.toArray(new Integer[0]),
			};
			fluid16Blocks.put(BlockLiquidConcrete.instance.blockID,data);

	}
	
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 0;
    }
    
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[16];
        this.blockIcon = par1IconRegister.registerIcon("thutconcrete:" + "wetConcrete_"+8);
        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = par1IconRegister.registerIcon("thutconcrete:" + "wetConcrete_"+i);
        }
    }
	
	
	
	 @SideOnly(Side.CLIENT)

	    /**
	     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	     */
	    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int par5)
	    {
		 TileEntityBlock16Fluid te = (TileEntityBlock16Fluid) par1IBlockAccess.getBlockTileEntity(x, y, z);
		 return this.iconArray[te.metaArray[par5]];
		 	
	    }
	 

		@Override
		public int stillLiquidId() {
			return BlockLiquidConcrete.instance.blockID;
		}
		@Override
		public boolean isMetaSensitive() {
			return false;
		}
		@Override
		public int stillLiquidMeta() {
			return 15;
		}
	 
}
