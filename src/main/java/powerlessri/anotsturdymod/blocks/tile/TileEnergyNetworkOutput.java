package powerlessri.anotsturdymod.blocks.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import powerlessri.anotsturdymod.handlers.init.RegistryHandler;

public class TileEnergyNetworkOutput extends TileEnergyNetworkAccessPort implements ITickable {

    public static final String TILE_REGISTRY_NAME = RegistryHandler.makeTileEntityID("energy_network_output");



    public TileEnergyNetworkOutput() {
    }

    public TileEnergyNetworkOutput(int channel, int ioLimit) {
        super(channel, ioLimit);
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos neighborPos = pos.offset(facing);
                TileEntity tile = world.getTileEntity(neighborPos);
                EnumFacing opposite = facing.getOpposite();

                // Don't insert to another access port, it might cause problems (e.g. loop)
                if (tile != null && !(tile instanceof TileEnergyNetworkAccessPort)) {
                    if (tile.hasCapability(CapabilityEnergy.ENERGY, opposite)) {
                        IEnergyStorage storage = tile.getCapability(CapabilityEnergy.ENERGY, opposite);

                        if (storage.canReceive()) {
                            sendEnergy(storage);
                        }
                    }
                }
            }
        }
    }

    public void sendEnergy(IEnergyStorage targetStorage) {
        TileEnergyNetworkController controller = getController();
        if(controller != null) {
            int extractLimit = extractEnergy(ioLimit, true);
            int accepted = targetStorage.receiveEnergy(extractLimit, false);
            controller.energyStored -= accepted;
        }
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

}
