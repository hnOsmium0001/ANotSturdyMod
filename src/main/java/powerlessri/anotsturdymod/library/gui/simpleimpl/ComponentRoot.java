package powerlessri.anotsturdymod.library.gui.simpleimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.*;
import powerlessri.anotsturdymod.library.gui.integration.GuiDrawBackgroundEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentRoot implements IContainer {

    private ImmutableList<IContainer<IComponent>> windows;
    private ImmutableList<IComponent> flattened;
    private ImmutableList<IComponent> leaves;
    private GuiScreen gui;

    private EventManager eventManager;

    public ComponentRoot(GuiScreen gui, ImmutableList<IContainer<IComponent>> windows) {
        this.gui = gui;
        this.windows = windows;
        this.flattened = ComponentStructureProjector.flatten(windows);
        this.leaves = ComponentStructureProjector.leaves(flattened);
        
        this.eventManager = EventManager.forLeaves(leaves);

        this.initializeAllComponents();
    }
    
    private void initializeAllComponents() {
        for (IComponent component : windows) {
            component.initialize(gui, this);
        }
    }
    

    @Override
    public void draw(GuiDrawBackgroundEvent event) {
        for (IContainer<IComponent> window : windows) {
            window.tryDraw(event);
        }
    }

    
    @Override
    public EDisplayMode getDisplay() {
        return EDisplayMode.ALL;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
    
    @Override
    public List<IContainer<IComponent>> getComponents() {
        return windows;
    }


    public void markVisible(IContainer<? extends IComponent> container) {
        for (IComponent component : container.getComponents()) {
            if (component instanceof IInteractionHandler) {
                markVisible((IInteractionHandler) component);
            }
        }
    }

    public void markVisible(IInteractionHandler component) {
        if (component.isLeafComponent()) {
            eventManager.markVisible(component);
        } else {
            markVisible(component);
        }
    }
    
    public void markInvisible(IContainer<? extends IComponent> container) {
        for (IComponent component : container.getComponents()) {
            if (component instanceof IInteractionHandler) {
                markInvisible((IInteractionHandler) component);
            }
        }
    }
    
    public void markInvisible(IInteractionHandler component) {
        if (component.isLeafComponent()) {
            eventManager.markInvisible(component);
        }
    }
    
    
    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        this.gui = gui;
    }

    @Override
    public GuiScreen getGui() {
        return gui;
    }

    @Nullable
    @Override
    public IComponent getParentComponent() {
        return this;
    }

    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public boolean isRootComponent() {
        return true;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int setId(int id) {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return gui.width;
    }

    @Override
    public int getHeight() {
        return gui.height;
    }

    @Override
    public int getActualX() {
        return 0;
    }

    @Override
    public int getActualY() {
        return 0;
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return true;
    }

    @Override
    public void forceActualPosition(int x, int y) {
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(int zIndex) {
    }

}
