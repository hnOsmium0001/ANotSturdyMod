package powerlessri.anotsturdymod.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powerlessri.anotsturdymod.items.base.ITagBasedItem;
import powerlessri.anotsturdymod.items.basic.ItemBasicItem;
import powerlessri.anotsturdymod.items.handler.WorldTransmutation;
import powerlessri.anotsturdymod.library.enums.EDataType;
import powerlessri.anotsturdymod.library.interfaces.IEnumNBTTags;
import powerlessri.anotsturdymod.library.utils.NBTUtils;

public class ItemTransmutationStone extends ItemBasicItem implements ITagBasedItem {
	
	public ItemTransmutationStone(String name) {
		super(name);
		
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
	}
	
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
	    String name = super.getItemStackDisplayName(stack);
	    
	    this.updateItemTag(stack);
	    int sideLength = this.getTagBValue(stack, EnumTags.CHARGE) * 2 + 1;
	    name = name + " (" + sideLength + "*" + sideLength + ")";
	    
	    return name;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack resultStack = player.getHeldItem(hand);
		
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
		
		this.updateItemTag(resultStack);
		this.cycleTagBValue(resultStack, EnumTags.CHARGE, (byte) 1);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, resultStack);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote)
			return EnumActionResult.SUCCESS;
		IBlockState pointerBlock = world.getBlockState(pos);
		IBlockState next = WorldTransmutation.getTransmutationNext(world, pos, pointerBlock);
		
		// Didn't found any matched transmutation
		if(next == null)
			return EnumActionResult.FAIL;
		
		int charge = (int) this.getTagBValue(player.getHeldItem(hand), EnumTags.CHARGE);
		for(BlockPos changingPos : getAffectedBlocks(world, pos, facing, charge, player.isSneaking())) {
			// If the affected pos is not the block at player's point
			// Which means it should not be affected
			if(pointerBlock != world.getBlockState(changingPos)) {
				continue;
			}
			
			world.setBlockState(changingPos, next);
		}
		
		return EnumActionResult.SUCCESS;
	}
	
	
	private Iterable<BlockPos> getAffectedBlocks(World world, BlockPos pos, EnumFacing faceHit, int charge, boolean sneaking) {
		
		if(sneaking) {
		    return BlockPos.getAllInBox(pos, pos);
		}
		
		switch(faceHit) {
		    case UP:
		    case DOWN:
		        return BlockPos.getAllInBox(pos.add(charge, 0, charge), pos.add(-charge, 0, -charge));
		    
		    // On a wall, it's either x changing or z
		    case NORTH:
		    case SOUTH:
		        return BlockPos.getAllInBox(pos.add(charge, charge, 0), pos.add(-charge, -charge, 0));
		    case EAST:
		    case WEST:
		    	return BlockPos.getAllInBox(pos.add(0, charge, charge), pos.add(0, -charge, -charge));
		}
		
		return BlockPos.getAllInBox(pos, pos);
	}
	
	private byte getTagBValue(ItemStack stack, EnumTags tag) {
        return stack.getTagCompound().getByte(tag.key);
    }
	
	private void cycleTagBValue(ItemStack stack, EnumTags targetNbt, byte increase) {
		NBTTagCompound tag = NBTUtils.getTagSafe(stack);
		byte originalVal = getTagBValue(stack, targetNbt);
		
        if(originalVal >= targetNbt.max) {
            tag.setByte(targetNbt.key, (byte) 0);
        } else {
        	NBTUtils.setTagEnum(tag, targetNbt, (byte) (originalVal + increase));
        }
    }
	
	
	
	private static enum EnumTags implements IEnumNBTTags<Object> {
        
        CHARGE("charge", 0, 5, EDataType.BYTE);
        
	    // You should never modify these values!
        EDataType type;
        String key;
        Object defaultValue;
        
        /** Numbers ONLY */
        int max;
        
        private EnumTags(String key, String defaultVal, EDataType type) {
            this.key = key;
            this.defaultValue = defaultVal;
            this.type = type;
        }
        private EnumTags(String key, int defaultVal, int max, EDataType type) {
            this.key = key;
            if(type == EDataType.BYTE) {
            	this.defaultValue = (byte) defaultVal;
            	this.max = (byte) max;
        	} else {
        		this.defaultValue = defaultVal;
        		this.max = max;
        	}
            this.type = type;
        }
        
        
        @Override
        public EDataType getType() {
            return this.type;
        }
        @Override
        public String getKey() {
            return this.key;
        }
        @Override
        public Object getDefaultValue() {
            return this.defaultValue;
        }
        
    }



	@Override
	public NBTTagCompound getDefaultTag() {
		NBTTagCompound tag = new NBTTagCompound();
		NBTUtils.buildTagWithDefault(tag, EnumTags.values());
		return tag;
	}


	@Override
	public void updateItemTag(ItemStack stack) {
		if(stack.getTagCompound() == null)  {
			this.buildDefaultTag(stack);
		}
	}
	
}
