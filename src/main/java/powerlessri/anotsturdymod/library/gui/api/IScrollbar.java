package powerlessri.anotsturdymod.library.gui.api;

import net.minecraft.client.gui.GuiScreen;

/**
 * <p>
 * Whenever the scroll bar changed its step, which means content inside the scrolling panel should move,
 * it must manually call {@link IScrollingPanel#setCurrentStep(int)}.
 * </p>
 */
public interface IScrollbar extends IInteractionHandler {

    /**
     * Implementations should <b>NOT</b> override this method, but instead {@link #initialize(GuiScreen, IScrollingPanel)}.
     */
    @Override
    @Deprecated
    default void initialize(GuiScreen gui, IComponent parent) {
        throw new IllegalArgumentException("The parent of am IScrollbar must be an IScrollingPanel!");
    }

    /**
     * Specialized version so that the
     *
     * <p>Implementations only needs to overrides this method.</p>
     * <p>See {@link IComponent#initialize(GuiScreen, IComponent)} for more information. </p>
     */
    void initialize(GuiScreen gui, IScrollingPanel parent);
    
    
    // TODO add javadoc
    
    int getMaximumHeight();
    
    @Override
    int getHeight();
    
    int getBaseY();
    
    @Override
    int getActualY();

}
