package powerlessri.anotsturdymod.library.gui.immutableimpl;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;

public class ComponentPanel extends AbstractComponent implements IContainer<IComponent> {
    
    protected ImmutableList<IComponent> components;
    
    public ComponentPanel(int x, int y, ImmutableList<IComponent> components) {
        super(x, y);
        this.components = components;
    }


    /**
     * <b>WARNING!</b> This implementation returns an instance of {@link ImmutableList}
     */
    @Override
    public ImmutableList<IComponent> getComponents() {
        return components;
    }

    @Override
    public boolean acceptsZIndex() {
        return false;
    }

    @Override
    public void initialize(GuiScreen gui, IComponent parent) {
        super.initialize(gui, parent);
        for (IComponent component : components) {
            component.initialize(gui, this);
        }
    }
    
    
    @Override
    public void draw() {
        for (IComponent component : components) {
            component.tryDraw();
        }
    }


    @Override
    public boolean isLeafComponent() {
        return true;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

}
