package slimeknights.tconstruct.library.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

import slimeknights.tconstruct.common.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.utils.TagUtil;

public class Pattern extends Item {
  public static final String TAG_PARTTYPE = "PartType";

  public Pattern() {
    this.setCreativeTab(TinkerRegistry.tabTools);
    this.setHasSubtypes(true);
  }

  @Override
  public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
    subItems.add(new ItemStack(this));

    for(IToolPart toolpart : TinkerRegistry.getToolParts()) {
      if(!(toolpart instanceof Item)) {
        continue;
      }

      ItemStack stack = new ItemStack(this);
      setTagForPart(stack, toolpart);

      subItems.add(stack);
    }
  }

  public static void setTagForPart(ItemStack stack, IToolPart toolPart) {
    NBTTagCompound tag = TagUtil.getTagSafe(stack);
    String id = toolPart.getIdentifier();

    tag.setString(TAG_PARTTYPE, id);

    stack.setTagCompound(tag);
  }

  public static IToolPart getPartFromTag(ItemStack stack) {
    NBTTagCompound tag = TagUtil.getTagSafe(stack);
    String part = tag.getString(TAG_PARTTYPE);

    for(IToolPart toolpart : TinkerRegistry.getToolParts()) {
      if(part.equals(toolpart.getIdentifier()))
        return toolpart;
    }

    return null;
  }

  public boolean isBlankPattern(ItemStack stack) {
    if(stack == null || !(stack.getItem() instanceof Pattern))
      return false;

    return Config.reuseStencil || (!stack.hasTagCompound());
  }
}
