package powerlessri.anotsturdymod.blocks.gui.immutable.inventory;

import net.minecraft.util.ResourceLocation;
import powerlessri.anotsturdymod.blocks.gui.immutable.AbstractComponent;
import powerlessri.anotsturdymod.varia.Reference;

public class ComponentSlotGroup extends AbstractComponent {
    
    public static final ResourceLocation WIDGETS_1 = new ResourceLocation(Reference.MODID, "textures/gui/widgets_1.png");
    
    public static final int SLOT_POS_X = 0;
    public static final int SLOT_POS_Y = 0;
    public static final int SLOT_IMAGE_WIDTH = 18;
    public static final int SLOT_IMAGE_HEIGHT = 18;

    private int slotsHorizontal;
    private int slotsVertical;

    public ComponentSlotGroup(int relativeX, int relativeY, int slotsHorizontal, int slotsVertical) {
        super(relativeX, relativeY);
        
        this.slotsHorizontal = slotsHorizontal;
        this.slotsVertical = slotsVertical;
    }

    @Override
    public void draw() {
        gui.mc.renderEngine.bindTexture(WIDGETS_1);
        
        for (int i = 0; i < slotsVertical; i++) {
            int currentY = getAbsoluteY() + i * SLOT_IMAGE_HEIGHT;
            for (int j = 0; j < slotsHorizontal; j++) {
                int currentX = getAbsoluteX() + j * SLOT_IMAGE_WIDTH;
                
                gui.drawTexturedModalRect(currentX, currentY, SLOT_POS_X, SLOT_POS_Y, SLOT_IMAGE_WIDTH, SLOT_IMAGE_HEIGHT);
            }
        }
    }


    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }
    
}
