package powerlessri.anotsturdymod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import powerlessri.anotsturdymod.library.utils.Utils;

import javax.annotation.Nullable;
import java.io.IOException;

public class ByteIOHelper {

    private ByteIOHelper() {
    }



    // ======== ByteBuf IO ======== //

    @Nullable
    public static String readString(ByteBuf buf) {
        int strLen = buf.readInt();
        if (strLen == -1) {
            return null;
        }
        if (strLen == 0) {
            return "";
        }

        byte[] bytes = new byte[strLen];
        buf.readBytes(bytes);
        return new String(bytes);
    }

    public static void writeString(ByteBuf buf, String str) {
        if (str == null) {
            buf.writeInt(-1);
            return;
        }

        byte[] bytes = str.getBytes();
        buf.writeInt(bytes.length);
        if (bytes.length > 0) {
            buf.writeBytes(bytes);
        }
    }

    @Nullable
    public static NBTTagCompound readTag(ByteBuf buf) {
        try {
            return new PacketBuffer(buf).readCompoundTag();
        } catch (IOException e) {
            Utils.getLogger().error("Error reading compound from packet", e);
        }
        return null;
    }

    public static void writeTag(ByteBuf buf, NBTTagCompound tag) {
        new PacketBuffer(buf).writeCompoundTag(tag);
    }

}
