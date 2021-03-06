package thutconcrete.common.corehandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thutconcrete.common.blocks.*;
import thutconcrete.common.items.ItemLiftBlocks;
import thutconcrete.common.items.ItemWorldGenBlock;

public class BlockHandler {

	private ConfigHandler config;
	public static Block[] blocks;
	public static Map<Block, ItemBlock> itemBlocks = new HashMap<Block, ItemBlock>();
	private static List<Block> blockList = new ArrayList<Block>();

	public BlockHandler(ConfigHandler configHandler){
		config = configHandler;
		initBlocks();
	}

	public void initBlocks(){
		
		int id = config.IDBlock;
		int idWorld = config.IDWorldBlock;
		blockList.add(new BlockDust(id++));
		blockList.add(new BlockRebar(id++));
		
		blockList.add(new BlockREConcrete(id++));
		
		blockList.add(new BlockLiquidREConcrete(id++));
		

		blockList.add(new BlockConcrete(id++));
		blockList.add(new BlockLiquidConcrete(id++));
		for(int i = 0; i<3; i++){
			blockList.add(new BlockLava(id++,i));
			blockList.add(new BlockSolidLava(id++,i));
		}

		blockList.add(new BlockVolcano(id++));
		blockList.add(new BlockBoom(id++));
		blockList.add(new BlockLimekiln(id++));
		blockList.add(new BlockLimekilnDummy(id++));
		blockList.add(new BlockLiftRail(id++));
		
		blocks = blockList.toArray(new Block[0]);

		registerBlocks();
		
		BlockWorldGen worldGenBlock = new BlockWorldGen(idWorld);
		BlockLift lift = new BlockLift(id++);
		blockList.add(lift);
		
		GameRegistry.registerBlock(lift, ItemLiftBlocks.class, "liftBlocks");
		GameRegistry.registerBlock(worldGenBlock, ItemWorldGenBlock.class, "worldGenBlock");
		
		blockList.add(worldGenBlock);
		blocks = blockList.toArray(new Block[0]);
		
		changeFlamibility();
	}

	public void registerBlocks(){
		for(Block block : blocks){
			GameRegistry.registerBlock(block, block.getLocalizedName().substring(5));
		}
		
	}
	
	public void changeFlamibility()
	{
		for(Block b:Block.blocksList)
		{
			if(b!=null&&b.blockMaterial == Material.wood&&!b.getLocalizedName().toLowerCase().contains("chest"))
			{
				b.setBurnProperties(b.blockID, 5, 20);
			}
			if(b instanceof BlockDoor && b.blockMaterial != Material.iron)
			{
				b.setBurnProperties(b.blockID, 5, 20);
			}
		}
	}

}
