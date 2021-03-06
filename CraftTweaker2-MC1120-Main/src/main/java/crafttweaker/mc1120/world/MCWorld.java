package crafttweaker.mc1120.world;

import crafttweaker.api.block.*;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Stan
 */
public class MCWorld extends MCBlockAccess implements IWorld {

    private final World world;

    public MCWorld(World world) {
        super(world);
        this.world = world;
    }

    @Override
    public int getBrightness(int x, int y, int z) {
        return world.getLight(new BlockPos(x, y, z));
    }
    
    @Override
    public int getBrightness(IBlockPos pos) {
        return world.getLight((BlockPos)pos.getInternal());
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return CraftTweakerMC.getBlock(world, x, y, z);
    }
    
    @Override
    public IBlock getBlock(IBlockPos pos) {
        return CraftTweakerMC.getBlock(world, pos.getX(), pos.getY(), pos.getZ());
    }
    
    @Override
    public IBiome getBiome(Position3f position) {
    	return getBiome(position.asBlockPos());
    }
    
    @Override
    public IBiome getBiome(IBlockPos position) {
    	return new MCBiome(world.getBiome((BlockPos) position.getInternal()));
    }

	@Override
	public IWorldInfo getWorldInfo() {
		return new MCWorldInfo(world.getWorldInfo());
	}

	@Override
	public boolean isRemote() {
		return world.isRemote;
	}

	@Override
	public boolean isRaining() {
		return world.isRaining();
	}

	@Override
	public boolean isDayTime() {
		return world.isDaytime();
	}

	@Override
	public boolean isSurfaceWorld() {
		return world.provider.isSurfaceWorld();
	}

	@Override
	public long getWorldTime() {
		return world.getTotalWorldTime();
	}

	@Override
	public int getMoonPhase() {
		return world.getMoonPhase();
	}

	@Override
	public int getDimension() {
		return world.provider.getDimension();
	}

	@Override
	public String getDimensionType() {
		return world.provider.getDimensionType().getName();
	}

	@Override
	public String getWorldType() {
		return world.getWorldType().getName();
	}
	
	@Override
	public Object getInternal() {
		return world;
	}
    
    @Override
    public boolean spawnEntity(IEntity entity) {
        return world.spawnEntity(CraftTweakerMC.getEntity(entity));
    }
    
    @Override
	public boolean setBlockState(IBlockState state, IBlockPos pos) {
		return world.setBlockState((BlockPos)pos.getInternal(), (net.minecraft.block.state.IBlockState)state.getInternal());
	}

	@Override
	public IWorldProvider getProvider() {
		return new MCWorldProvider(world.provider);
	}
}
