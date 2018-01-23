package icbm.classic.content.items;

import com.builtbroken.mc.api.items.tools.IWorldPosItem;
import com.builtbroken.mc.api.tile.multiblock.IMultiTile;
import com.builtbroken.mc.api.tile.multiblock.IMultiTileHost;
import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.core.network.IPacketReceiver;
import com.builtbroken.mc.core.network.packet.PacketPlayerItem;
import com.builtbroken.mc.core.network.packet.PacketType;
import com.builtbroken.mc.imp.transform.vector.Location;
import com.builtbroken.mc.lib.helper.LanguageUtility;
import com.builtbroken.mc.prefab.items.ItemWorldPos;
import icbm.classic.ICBMClassic;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import resonant.api.explosion.ILauncherController;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 6/13/2016.
 */
public class ItemRadarGun extends ItemWorldPos implements IWorldPosItem, IPacketReceiver
{
    public ItemRadarGun()
    {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setCreativeTab(ICBMClassic.CREATIVE_TAB);
        this.setUnlocalizedName(ICBMClassic.PREFIX + "radarGun");
        this.setRegistryName(ICBMClassic.DOMAIN, "radarGun");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lines, ITooltipFlag flagIn)
    {
        String localization = LanguageUtility.getLocal(getUnlocalizedName() + ".info");
        if (localization != null && !localization.isEmpty())
        {
            String[] split = localization.split(",");
            for (String line : split)
            {
                lines.add(line.trim());
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn)
    {
        if (world.isRemote)
        {
            RayTraceResult objectMouseOver = player.rayTrace(200, 1);
            TileEntity tileEntity = world.getTileEntity(objectMouseOver.getBlockPos());
            if (!(tileEntity instanceof ILauncherController))
            {
                Engine.packetHandler.sendToServer(new PacketPlayerItem(player, objectMouseOver.getBlockPos()));
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }

        Location location = new Location(world, pos);
        TileEntity tile = location.getTileEntity();
        if (tile instanceof IMultiTile)
        {
            IMultiTileHost host = ((IMultiTile) tile).getHost();
            if (host instanceof TileEntity)
            {
                tile = (TileEntity) host;
            }
        }

        if (player.isSneaking())
        {
            stack.setTagCompound(null);
            stack.setItemDamage(0);
            LanguageUtility.addChatToPlayer(player, "gps.cleared.name");
            player.inventoryContainer.detectAndSendChanges();
            return EnumActionResult.SUCCESS;
        }
        else
        {
            Location storedLocation = getLocation(stack);
            if (storedLocation == null || !storedLocation.isAboveBedrock())
            {
                LanguageUtility.addChatToPlayer(player, "gps.error.pos.invalid.name");
                return EnumActionResult.SUCCESS;
            }
            else if (tile instanceof ILauncherController)
            {
                ((ILauncherController) tile).setTarget(storedLocation.toPos());
                LanguageUtility.addChatToPlayer(player, "gps.data.transferred.name");
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void read(ByteBuf buf, EntityPlayer player, PacketType packet)
    {
        ItemStack stack = player.inventory.getCurrentItem();
        if (stack != null && stack.getItem() == this)
        {
            setLocation(stack, new Location(player.world, buf.readInt(), buf.readInt(), buf.readInt()));
            LanguageUtility.addChatToPlayer(player, "gps.pos.set.name");
        }
    }
}
