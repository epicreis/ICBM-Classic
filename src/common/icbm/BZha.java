package icbm;

import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;

public class BZha extends ICBMBlock
{
	protected BZha(int par1, int par2)
	{
		super(par1, par2, Material.cactus);
		this.setHardness(1.0F);
		this.setBlockName("Zha");
		this.setCreativeTab(ZhuYao.TAB);
	}

	/**
	 * Returns the bounding box of the wired
	 * rectangular prism to render.
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.5F, z + 1);
	}

	/**
	 * Returns a bounding box from the pool of
	 * bounding boxes (this means this box can
	 * change after the pool has been cleared to
	 * be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * Returns the block texture based on the side
	 * being looked at. Args: side
	 */
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int metadata)
	{
		return this.blockIndexInTexture + metadata;
	}

	/**
	 * If this block doesn't render as an ordinary
	 * block it will return False (examples:
	 * signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m
	 * cube? This determines whether or not to
	 * render the shared face of two adjacent
	 * blocks and also whether the player can
	 * attach torches, redstone wire, etc to this
	 * block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called
	 * for this block
	 */
	@Override
	public int getRenderType()
	{
		return 1;
	}

	/**
	 * Returns the mobility information of the
	 * block, 0 = free, 1 = can't push but can
	 * move over, 2 = total immobility and stop
	 * pistons
	 */
	@Override
	public int getMobilityFlag()
	{
		return 0;
	}

	/**
	 * Checks to see if its valid to put this
	 * block at the specified coordinates. Args:
	 * world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return canBlockStay(par1World, par2, par3, par4);
	}

	/**
	 * Lets the block know when one of its
	 * neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}
	}

	/**
	 * Can this block stay at this position.
	 * Similar to canPlaceBlockAt except gets
	 * checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockSolidOnSide(par2 - 1, par3, par4, ForgeDirection.getOrientation(5)) || par1World.isBlockSolidOnSide(par2 + 1, par3, par4, ForgeDirection.getOrientation(4)) || par1World.isBlockSolidOnSide(par2, par3, par4 - 1, ForgeDirection.getOrientation(3)) || par1World.isBlockSolidOnSide(par2, par3, par4 + 1, ForgeDirection.getOrientation(2)) || par1World.isBlockSolidOnSide(par2, par3 + 1, par4, ForgeDirection.getOrientation(1)) || par1World.isBlockSolidOnSide(par2, par3 - 1, par4, ForgeDirection.getOrientation(0)) ||

		par1World.getBlockId(par2 - 1, par3, par4) == Block.pistonExtension.blockID || par1World.getBlockId(par2 + 1, par3, par4) == Block.pistonExtension.blockID || par1World.getBlockId(par2, par3, par4 - 1) == Block.pistonExtension.blockID || par1World.getBlockId(par2, par3, par4 + 1) == Block.pistonExtension.blockID || par1World.getBlockId(par2, par3 + 1, par4) == Block.pistonExtension.blockID || par1World.getBlockId(par2, par3 - 1, par4) == Block.pistonExtension.blockID ||

		par1World.getBlockId(par2 - 1, par3, par4) == Block.pistonBase.blockID || par1World.getBlockId(par2 + 1, par3, par4) == Block.pistonBase.blockID || par1World.getBlockId(par2, par3, par4 - 1) == Block.pistonBase.blockID || par1World.getBlockId(par2, par3, par4 + 1) == Block.pistonBase.blockID || par1World.getBlockId(par2, par3 + 1, par4) == Block.pistonBase.blockID || par1World.getBlockId(par2, par3 - 1, par4) == Block.pistonBase.blockID ||

		par1World.getBlockId(par2 - 1, par3, par4) == Block.pistonStickyBase.blockID || par1World.getBlockId(par2 + 1, par3, par4) == Block.pistonStickyBase.blockID || par1World.getBlockId(par2, par3, par4 - 1) == Block.pistonStickyBase.blockID || par1World.getBlockId(par2, par3, par4 + 1) == Block.pistonStickyBase.blockID || par1World.getBlockId(par2, par3 + 1, par4) == Block.pistonStickyBase.blockID || par1World.getBlockId(par2, par3 - 1, par4) == Block.pistonStickyBase.blockID;
	}

	/**
	 * Triggered whenever an entity collides with
	 * this block (enters into the block). Args:
	 * world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		// If the entity is a living entity
		if (par5Entity instanceof EntityLiving)
		{
			par5Entity.attackEntityFrom(DamageSource.cactus, 1);

			if (par1World.getBlockMetadata(par2, par3, par4) == 1)
			{
				((EntityLiving) par5Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 7 * 20, 0));
			}
			else if (par1World.getBlockMetadata(par2, par3, par4) == 2)
			{
				((EntityLiving) par5Entity).setFire(7);
			}
		}
	}

	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < 3; i++)
		{
			par3List.add(new ItemStack(this, 1, i));
		}
	}

}
