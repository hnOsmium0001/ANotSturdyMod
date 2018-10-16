package powerlessri.anotsturdymod.blocksystems.remoteenergynetwork.tile;

import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import powerlessri.anotsturdymod.blocks.tile.base.TileEntityBase;
import powerlessri.anotsturdymod.library.Utils;
import powerlessri.anotsturdymod.world.AnsmSavedData;

/**
 * This block does not meant to be a direct part of forge energy system. Use BlockEnergyAccessPort
 * for any access to the power storage managed by this block.
 */
public class TileENController extends TileEntityBase {

    public static class FakeTE extends TileENController {

        public FakeTE() {
            this.isAlive = true;
        }

        @Override
        public int getOrAllocChannel() {
            return 0;
        }

        @Override
        public int getChannel() {
            return 0;
        }

        @Override
        public boolean isInitialized() {
            return true;
        }

        @Override
        public long getCapacity() {
            return 0;
        }

        @Override
        public long getCapacityLeft() {
            return 0;
        }

        @Override
        public void onLoadServer() {
        }

        @Override
        public void onChunkUnloadServer() {
        }

        @Override
        public void onRemoved() {
        }

        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            return super.writeToNBT(tag);
        }

    }


    // NBT tags
    public static final String CHANNEL = "storageChannel";
    public static final String STORAGE_UPGRADES = "storageUpgrades";
    public static final String STORAGE_ENERGY_REMAIN = "energyStored";

    public static final long DEFAULT_CAPACITY = 1000000000L;
    public static final long STORAGE_UPGRADE_INCREMENT = 1000000L;
    public static final int MAX_STORAGE_UPGRADES = 64;


    private AnsmSavedData data;

    /**
     * {@code true} when is loaded, {@code false} when is not loaded.
     */
    public boolean isAlive = false;


    /**
     * A unique channel id (in the save). An allocated (non-default) channel is at least {@code 1}.
     */
    public int channel;

    /**
     * Amount of storage upgrades installed. Used to calculate total capacity.
     */
    public long amountStorageUpgrades = 0;
    /**
     * Energy stored inside controller.
     */
    public long energyStored = 0;


    public int getOrAllocChannel() {
        if (!isInitialized()) {
            channel = data.controllerNextChannel;
            data.controllerNextChannel++;
            data.markDirty();

            // Increase the size, so ArrayList#get/set won't throw out IndexOutOfBoundsException
            data.controllerTiles.add(null);
            // Now it has a channel, put itself into the reference list.
            this.onLoadServer();
        }

        return this.getChannel();
    }

    public int getChannel() {
        return channel;
    }

    public boolean isInitialized() {
        return this.channel != 0;
    }


    /**
     * Install storage upgrades with capacity check.
     *
     * @return The amount of storage upgrades accepted.
     */
    public int installStorageUpgrade(int attempt) {
        int availableSlots = (int) (MAX_STORAGE_UPGRADES - amountStorageUpgrades);
        int actualInsert = Math.min(availableSlots, attempt);
        amountStorageUpgrades += actualInsert;
        return actualInsert;
    }


    public long getCapacity() {
        return DEFAULT_CAPACITY + (amountStorageUpgrades * STORAGE_UPGRADE_INCREMENT);
    }

    public long getCapacityLeft() {
        return this.getCapacity() - this.energyStored;
    }


    @Override
    public void onLoadServer() {
        Utils.getLogger().info("load");
        if (data == null) {
            data = AnsmSavedData.fromWorld(world);
        }

        // Wait until player activate this block (wait until it has a channel)
        if (isInitialized()) {
            if (channel < data.controllerTiles.size()) {
                updateDataReference();
            }
        }

        isAlive = true;
    }

    private void updateDataReference() {
        // This step might cause an IndexOutOfBoundsException, if channel is NOT set by #getOrAllocChannel()
        // but you're not suppose to do it other than #getOrAllocChannel(), so it's fine.
        if (data.controllerTiles.get(channel) == null) {
            data.controllerTiles.set(channel, this);

        } else {
            // When channel == DEFAULT_CHANNEL,
            // data.controllerTiles[channel] will always be a FakeEnergyNetworkController
            if (data.controllerTiles.get(channel) != data.FAKE_EN_CONTROLLER_TILE) {
                String description = "Unexpected repeating channel from BlockEnergyController";
                IllegalAccessException e = new IllegalAccessException(description);
                CrashReport crashReport = CrashReport.makeCrashReport(e, description);

                throw new ReportedException(crashReport);
            }
        }
    }

    @Override
    public void onChunkUnloadServer() {
        Utils.getLogger().info("unload");
        data.controllerTiles.set(channel, null);
        this.isAlive = false;
    }

    @Override
    public void onRemoved() {
        onChunkUnloadServer();
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        Utils.getLogger().info("readNBT");
        super.readFromNBT(tag);

        this.channel = tag.getInteger(CHANNEL);

        this.amountStorageUpgrades = tag.getLong(STORAGE_UPGRADES);
        this.energyStored = tag.getLong(STORAGE_ENERGY_REMAIN);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        Utils.getLogger().info("writeNBT");
        tag.setInteger(CHANNEL, this.channel);

        tag.setLong(STORAGE_UPGRADES, this.amountStorageUpgrades);
        tag.setLong(STORAGE_ENERGY_REMAIN, this.energyStored);

        return super.writeToNBT(tag);
    }

}