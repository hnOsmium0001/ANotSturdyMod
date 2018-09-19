package powerlessri.anotsturdymod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import powerlessri.anotsturdymod.blocks.base.TileBlockBase;
import powerlessri.anotsturdymod.tile.TileEnergyStorage;
import powerlessri.anotsturdymod.world.handler.LinkedEnergyStorage;

public class BlockRemoteEnergyCell extends TileBlockBase {
    
    private static final int DEFAULT_STORAGE_ID;
    
    private static final LinkedEnergyStorage multiStorage;
    
    static {
        multiStorage = new LinkedEnergyStorage();
        
        DEFAULT_STORAGE_ID = multiStorage.nextChannel();
        multiStorage.setStorageTraits(DEFAULT_STORAGE_ID, 0, 0, 0);
    }
    
    public static class TileRemoteEnergyCell extends TileEnergyStorage {
        
        private static final int DEFAULT_CAPACITY = 1000000;
        private static final int DEFAULT_MAX_RECEIVE = 5000;
        private static final int DEFAULT_MAX_EXTRACT = 5000;
        
        private int channel;
        
        public TileRemoteEnergyCell() {
            super(multiStorage.getStorage(DEFAULT_STORAGE_ID));
            
            this.channel = DEFAULT_STORAGE_ID;
        }
        
        public void setChannel(int channel) {
            this.updateStorage(-DEFAULT_CAPACITY);
            
            if(multiStorage.doesStorageExist(channel)) {
                this.channel = channel;
            }
            
            this.updateStorage(DEFAULT_CAPACITY);
        }
        
        public void createChannel() {
            this.setChannel(multiStorage.nextChannel());
        }
        
        public void updateStorage() {
            this.storage = multiStorage.getStorage(this.channel);
        }
        
        private void updateStorage(int capacityIncreament) {
            EnergyStorage last = multiStorage.getStorage(this.channel);
            // Take out DEFAULT_CAPACITY amount of capacity
            // This step will dispose the energy storage 'last'
            multiStorage.setStorageTraits(this.channel, last.getMaxEnergyStored() + capacityIncreament, DEFAULT_MAX_RECEIVE, DEFAULT_MAX_EXTRACT);
            // Put rest of energy into the new energy storage
            multiStorage.getStorage(this.channel).receiveEnergy(last.getEnergyStored(), false);
        }
        
    }
    
    

    public BlockRemoteEnergyCell(String name) {
        super(name, Material.IRON);

        this.setHardness(2.0f);
        this.setResistance(15.0f);
        this.setHarvestLevel(EHarvestTool.PICKAXE, EHarvestLevel.IRON);

        // TODO add own creative tab
        this.setCreativeTab(CreativeTabs.MISC);
    }



    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
        if(world.isRemote) {
            return true;
        }
        
        
        ItemStack heldItem = player.getHeldItem(hand);
        
        
        return false;
    }



    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileRemoteEnergyCell.class;
    }

}
