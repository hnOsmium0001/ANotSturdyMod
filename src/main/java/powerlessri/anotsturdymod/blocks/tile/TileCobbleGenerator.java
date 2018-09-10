package powerlessri.anotsturdymod.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileCobbleGenerator extends TileEntity implements IItemHandler, ICapabilityProvider {

    private int cobbleRemainded;
    private final ItemStack representaionCobbleStack;

    public TileCobbleGenerator() {
        this.cobbleRemainded = 64;
        this.representaionCobbleStack = new ItemStack(Blocks.COBBLESTONE, this.cobbleRemainded);
    }

    @Nullable
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this);
        }
        
        return super.getCapability(capability, facing);

    }



    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.representaionCobbleStack;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        amount = Math.min(this.cobbleRemainded, amount);
        return new ItemStack(Blocks.COBBLESTONE, amount);
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

}
