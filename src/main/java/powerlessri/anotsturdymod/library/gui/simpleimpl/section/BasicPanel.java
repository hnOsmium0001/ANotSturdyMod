package powerlessri.anotsturdymod.library.gui.simpleimpl.section;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiScreen;
import powerlessri.anotsturdymod.library.gui.api.IComponent;
import powerlessri.anotsturdymod.library.gui.api.IContainer;
import powerlessri.anotsturdymod.library.gui.simpleimpl.AbstractComponent;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;

import java.util.Comparator;

public class BasicPanel extends AbstractComponent implements IContainer<IComponent> {
    
    protected ImmutableList<IComponent> components;
    
    private int width;
    private int height;
    
    public BasicPanel(int x, int y, ImmutableList<IComponent> components) {
        super(x, y);
        this.components = components;
        this.width = components.stream()
                .max(Comparator.comparingInt(IComponent::getActualXRight))
                .orElse(components.get(0))
                .getActualXRight();
        this.height = components.stream()
                .max(Comparator.comparingInt(IComponent::getActualYBottom))
                .orElse(components.get(0))
                .getActualYBottom();
                
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
    public void draw(ContextGuiDrawing event) {
        for (IComponent component : components) {
            component.draw(event);
        }
    }


    @Override
    public boolean isLeafComponent() {
        return false;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
