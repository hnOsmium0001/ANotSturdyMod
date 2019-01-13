package powerlessri.anotsturdymod.library.gui.simpleimpl.button;

import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import powerlessri.anotsturdymod.config.client.GuiStyleConfig;
import powerlessri.anotsturdymod.library.gui.Color;
import powerlessri.anotsturdymod.library.gui.integration.ContextGuiDrawing;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.render.TESRStateManager;
import powerlessri.anotsturdymod.varia.render.VertexSequencer;
import powerlessri.anotsturdymod.varia.render.style.VanillaPresets;
import powerlessri.anotsturdymod.varia.render.utils.BoxUtils;

import javax.rmi.CORBA.Util;

public class ButtonGradient extends Button {

    public static int calculateTextXOffset(String text, int componentWidth) {
        return (componentWidth / 2) - (Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2);
    }

    public static int calculateTextYOffset(int componentHeight) {
        return calculateTextYOffset(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, componentHeight);
    }

    public static int calculateTextYOffset(int textHeight, int componentHeight) {
        return (componentHeight / 2) - (textHeight / 2);
    }


    public static Color colorSNormal;
    public static Color colorENormal;

    public static Color colorSHovering;
    public static Color colorEHovering;

    public static Color colorSPressed;
    public static Color colorEPressed;

    public static Color colorNormalText;
    public static Color colorDisabledText;

    private static void reloadColors() {
        colorSNormal = Color.hex(GuiStyleConfig.gradientBtnSNormal);
        colorENormal = Color.hex(GuiStyleConfig.gradientBtnENormal);

        colorSHovering = Color.hex(GuiStyleConfig.gradientBtnSHover);
        colorEHovering = Color.hex(GuiStyleConfig.gradientBtnEHover);

        colorSPressed = Color.hex(GuiStyleConfig.gradientBtnSPressed);
        colorEPressed = Color.hex(GuiStyleConfig.gradientBtnEPressed);

        colorNormalText = Color.hex(GuiStyleConfig.gradientBtnTextNormal);
        colorDisabledText = Color.hex(GuiStyleConfig.gradientBtnTextDisabled);
    }


    private String text;
    private int textXOffset;
    private int textYOffset;

    public ButtonGradient(int relativeX, int relativeY, int width, int height, String text) {
        super(relativeX, relativeY, width, height);
        this.setText(text);

        //TODO update on config changes
        reloadColors();
    }


    private int getTextX() {
        return getActualX() + textXOffset;
    }

    private int getTextY() {
        return getActualY() + textYOffset;
    }


    public void setText(String text) {
        this.text = text;
        this.textXOffset = calculateTextXOffset(text, getWidth());
        this.textYOffset = calculateTextYOffset(getHeight());
    }


    @Override
    public void drawNormal(ContextGuiDrawing event) {
        this.drawGradientRectangleBox(1, colorSNormal, colorENormal, false);
        this.drawText(colorNormalText.getHex());
    }

    @Override
    public void drawHovering(ContextGuiDrawing event) {
        this.drawGradientRectangleBox(1, colorSHovering, colorEHovering, false);
        this.drawText(colorNormalText.getHex());
    }

    @Override
    public void drawPressed(ContextGuiDrawing event) {
        this.drawGradientRectangleBox(1, colorSPressed, colorEPressed, true);
        this.drawText(colorNormalText.getHex());
    }

    @Override
    public void drawDisabled(ContextGuiDrawing event) {
        this.drawNormal(event);
        this.drawText(colorDisabledText.getHex());
    }


    private void drawText(int color) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, getTextX(), getTextY(), color);
    }

    private void drawGradientRectangleBox(int shrink, Color top, Color bottom, boolean invert) {
        BufferBuilder buffer = TESRStateManager.getGradientVBuffer();
        {
            VertexSequencer.verticalGradientBox(buffer, getActualX() + shrink, getActualY() + shrink, getActualXRight() - shrink, getActualYBottom() - shrink, 0, top, bottom);
            int x1 = getActualX();
            int y1 = getActualY();
            int x2 = getActualXRight();
            int y2 = getActualYBottom();
            if (invert) {
                BoxUtils.putBorderVertexes(buffer, x1, y1, x2, y2, 1, VanillaPresets.BORDER_COLOR_DARK, VanillaPresets.BORDER_COLOR_LIGHT);
            } else {
                BoxUtils.putBorderVertexes(buffer, x1, y1, x2, y2, 1, VanillaPresets.BORDER_COLOR_LIGHT, VanillaPresets.BORDER_COLOR_DARK);
            }
        }
        TESRStateManager.finish();
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("textXOffset", textXOffset)
                .add("textYOffset", textYOffset)
                .toString();
    }

}
