package powerlessri.anotsturdymod.blocks.cobblegen.tile;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import powerlessri.anotsturdymod.handlers.init.RegistryTileEntity;
import powerlessri.anotsturdymod.library.tile.base.TileEntityBase;

import javax.annotation.Nullable;

@RegistryTileEntity("cobble_generator")
public class TileCobbleGenerator extends TileEntityBase implements IItemHandler, ICapabilityProvider {

    private int cobbleRemainded;
    private final ItemStack representaionCobbleStack;

    public TileCobbleGenerator() {
        this.cobbleRemainded = 64;
        this.representaionCobbleStack = new ItemStack(Blocks.COBBLESTONE, this.cobbleRemainded);
    }

    @Nullable
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
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
        int extractionAmount = Math.min(this.cobbleRemainded, amount);
        return new ItemStack(Blocks.COBBLESTONE, extractionAmount);
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }


    @Override
    public void restoreFromNBT(NBTTagCompound tag) {
    }

    @Override
    public void writeRestorableNBT(NBTTagCompound tagCompound) {
    }

}
