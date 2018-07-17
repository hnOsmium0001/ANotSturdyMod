package powerlessri.anotsturdymod.items.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import powerlessri.anotsturdymod.ANotSturdyMod;
import powerlessri.anotsturdymod.utils.Reference;
import powerlessri.anotsturdymod.utils.Utils;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasModel;
import powerlessri.anotsturdymod.utils.handlers.interfaces.IHasVariants;

public class ItemVariantedItem extends ItemBase implements IHasVariants{
	
	//unlocalizedName for each item.
	public final List<String> VARIANT_NAMES = new ArrayList<String>();
	
	public ItemVariantedItem(String registry_name, String unlocalized_name, CreativeTabs tab) {
		super(registry_name, unlocalized_name);
		
		this.setCreativeTab(tab);
	}
	
	@Override
	public void registerModel() {
		ANotSturdyMod.proxy.registerItemRenderer(this, "inventory");
	}
	
	
	
	@Override
	public void addVariant(String unlocalized_name) {
		VARIANT_NAMES.add(unlocalized_name);
	}
	@Override
	public void removeVariant(String unlocalized_name) {
		VARIANT_NAMES.remove(unlocalized_name);
	}
	@Override
	public void removeVariant(int index) {
		VARIANT_NAMES.remove(index);
	}
	
	
	@Override
	public int getVariantAmount() {
		return VARIANT_NAMES.size();
	}
	@Override 
	public String getVariant(int index) {
		return VARIANT_NAMES.get(index);
	}
	@Override
	public boolean contains(String unlocalized_name) {
		return VARIANT_NAMES.contains(unlocalized_name);
	}
	
	
	
	@Override
	public void registerModel(int meta) throws MethodNotSupportedException {
	}
	
}