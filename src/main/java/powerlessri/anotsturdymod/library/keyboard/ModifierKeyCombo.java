package powerlessri.anotsturdymod.library.keyboard;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;

public class ModifierKeyCombo implements UtilKeyCombination {

    private ModifierKey baseKey;
    private Key secondaryKey;

    private ImmutableList<Key> keys;
    private ImmutableList<Key> rejectedKeys;

    public ModifierKeyCombo(@Nonnull ModifierKey baseKey, @Nonnull Key secondaryKey, Key... rejectedKeys) {
        this.baseKey = baseKey;
        this.secondaryKey = secondaryKey;
        this.keys = ImmutableList.of(baseKey, secondaryKey);
        this.rejectedKeys = ImmutableList.copyOf(rejectedKeys);
    }

    @Override
    public boolean isTriggered() {
        return this.baseKey.isKeyDown() && this.secondaryKey.isKeyDown();
    }

    @Nonnull
    @Override
    public ModifierKey getBaseKey() {
        return this.baseKey;
    }

    @Override
    public ImmutableList<Key> getKeys() {
        return this.keys;
    }

    @Override
    public ImmutableList<Key> getRejectedKeys() {
        return this.rejectedKeys;
    }

}
