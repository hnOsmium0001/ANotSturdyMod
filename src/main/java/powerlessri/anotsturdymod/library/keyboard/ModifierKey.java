package powerlessri.anotsturdymod.library.keyboard;

public interface ModifierKey extends Key {

    /**
     * See parent Javadoc for more information.
     * <p>
     * For collective keys, the method may return {@code 0}. If not returning {@code 0}, if must return the bitwise or result of all sub key
     * codes.
     * </p>
     */
    @Override
    int getKeyCode();

    /**
     * If the parameter can be considered as self.
     * <p>
     * Example: {@code CTRL.isCollectorOf(LCTRL)} returns true, but {@code ALT.isCollectorOf(LSHIFT)} returns false.
     * </p>
     *
     * @param other The potential sub-modifier key
     */
    boolean isCollectorOf(ModifierKey other);

    /**
     * If the this can be considered as the parameter.
     * <p>
     * Example: {@code LCTRL.isPartOf(CTRL)} returns true, but {@code CTRL.isPartOf(LCTRL)} returns false.
     * </p>
     *
     * @param parent The potential superior modifier key
     */
    boolean isPartOf(ModifierKey parent);

    /**
     * Either {@link #isCollectorOf(ModifierKey)} or {@link #isPartOf(ModifierKey)} the parameter.
     */
    boolean isRelatedTo(ModifierKey other);

    /**
     * If {@link #isCollectorOf(ModifierKey)} would ever return {@code true}.
     */
    boolean hasSubKeys();

}
