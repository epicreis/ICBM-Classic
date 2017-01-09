package icbm.classic.content.blocks;

import icbm.classic.Reference;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;

public class BlockGlassPressurePlate extends BlockPressurePlate
{
    public BlockGlassPressurePlate()
    {
        super(Reference.PREFIX + "glassPressurePlate", Material.glass, BlockPressurePlate.Sensitivity.everything);
        this.setTickRandomly(true);
        this.setResistance(1F);
        this.setHardness(0.3F);
        this.setStepSound(soundTypeGlass);
        this.setBlockName(Reference.PREFIX + "glassPressurePlate");
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getMobilityFlag()
    {
        return 1;
    }
}