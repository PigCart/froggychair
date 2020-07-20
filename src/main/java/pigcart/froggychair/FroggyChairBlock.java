package pigcart.froggychair;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


import static pigcart.froggychair.FroggyChair.SIT_ENTITY_TYPE;

public class FroggyChairBlock extends HorizontalFacingBlock {
	public static final BooleanProperty OCCUPIED;
    public FroggyChairBlock() {
        super(FabricBlockSettings.of(Material.STONE).breakByHand(true).sounds(BlockSoundGroup.CORAL).strength(0, 0.1f).build());
        setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(OCCUPIED, false));
    }

	public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
    	if (!blockState.get(OCCUPIED))
		{
			FroggySitEntity froggysit = SIT_ENTITY_TYPE.create(world);
			Vec3d pos = new Vec3d(blockHitResult.getBlockPos().getX() + 0.5D, blockHitResult.getBlockPos().getY() + 0.25D, blockHitResult.getBlockPos().getZ() + 0.5D);

			world.setBlockState(blockPos, blockState.with(OCCUPIED, true));
			froggysit.updatePosition(pos.getX(), pos.getY(), pos.getZ());

			world.spawnEntity(froggysit);
			playerEntity.startRiding(froggysit);
			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
    		stateManager.add(Properties.HORIZONTAL_FACING);
    		stateManager.add(Properties.OCCUPIED);
    	}
    
    	@Override
    	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
    		Direction dir = state.get(FACING);
    		switch(dir) {
	    		case NORTH:
                    return VoxelShapes.union(VoxelShapes.cuboid(0.0f, 0.5f, 0.0f, 1.0f, 0.6f, 1.0f), VoxelShapes.cuboid(0.0f, 0.5f, 0f, 1.0f, 1.4f, 0.1f));
                case SOUTH:
                    return VoxelShapes.union(VoxelShapes.cuboid(0.0f, 0.5f, 0.0f, 1.0f, 0.6f, 1.0f), VoxelShapes.cuboid(0.0f, 0.5f, 0.9f, 1.0f, 1.4f, 1.0f));
	    		case EAST:
	    			return VoxelShapes.union(VoxelShapes.cuboid(0.0f, 0.5f, 0.0f, 1.0f, 0.6f, 1.0f), VoxelShapes.cuboid(0.9f, 0.5f, 0.0f, 1.0f, 1.4f, 1.0f));
	    		case WEST:
	    			return VoxelShapes.union(VoxelShapes.cuboid(0.0f, 0.5f, 0.0f, 1.0f, 0.6f, 1.0f), VoxelShapes.cuboid(0.0f, 0.5f, 0.0f, 0.1f, 1.4f, 1.0f));
    			default:
			    	return VoxelShapes.fullCube();
		    }
	    }
    
	    public BlockState getPlacementState(ItemPlacementContext ctx) {
    		return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
    }
	static {
		OCCUPIED = Properties.OCCUPIED;
	}
}