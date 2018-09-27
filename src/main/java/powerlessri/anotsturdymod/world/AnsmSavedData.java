package powerlessri.anotsturdymod.world;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import powerlessri.anotsturdymod.library.utils.Reference;
import powerlessri.anotsturdymod.tile.TileEnergyNetworkController;

public class AnsmSavedData extends WorldSavedData {

    public static AnsmSavedData fromWorld(World world) {
        MapStorage storage = world.getMapStorage();
        AnsmSavedData result = (AnsmSavedData) storage.getOrLoadData(AnsmSavedData.class, DATA_NAME);

        if(result == null) {
            result = new AnsmSavedData();
            storage.setData(DATA_NAME, result);
        }

        return result;
    }



    public static final String DATA_NAME = Reference.MODID + "_GeneralData";

    // NBT tag keys
    public static final String CONTROLLER_CHANNEL_USAGE = "cntrllrNextChnnl";

    // TODO redesign so it's not coupled with BlockEnergyController.nextChannel
    public int controllerNextChannel = 1;
    
    
    // ======== Runtime Data ======== //
    
    public ArrayList<TileEnergyNetworkController> controllerTiles = new ArrayList<>();


    public AnsmSavedData() {
        super(DATA_NAME);
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.controllerNextChannel = tag.getInteger(CONTROLLER_CHANNEL_USAGE);
        
        // Leave one slot for channel 0
        for(int i = -1; i < controllerNextChannel; i++) {
            this.controllerTiles.add(null);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger(CONTROLLER_CHANNEL_USAGE, controllerNextChannel);

        return tag;
    }

}
