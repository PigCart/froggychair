package pigcart.froggychair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FroggySitEntity extends Entity
{
	public FroggySitEntity(EntityType<? extends FroggySitEntity> type, World world)
	{
		super(type, world);
		noClip = true;
	}

	@Override
	public void equipStack(EquipmentSlot slot, ItemStack stack) {

	}

	@Override
	public Packet<?> createSpawnPacket() {
		return S2CEntitySpawnPacket.createPacket(this);
	}

	@Override
	protected void initDataTracker() {

	}

	@Override
	public void tick()
	{
		super.tick();

		if(!hasPassengers()||world.getBlockState(getBlockPos()).isAir())
		{
			Block block = world.getBlockState(getBlockPos()).getBlock();

			if (block instanceof FroggyChairBlock) {
				FroggyChairBlock cb = (FroggyChairBlock) block;
				Direction facing = world.getBlockState(getBlockPos()).get(FroggyChairBlock.FACING);
				world.setBlockState(getBlockPos(), cb.getDefaultState().with(FroggyChairBlock.OCCUPIED, false).with(FroggyChairBlock.FACING, facing));
			}


			remove();
		}
	}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {

	}

	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {

	}

	@Override
	protected void removePassenger(Entity passenger) {
		BlockPos pos = this.getBlockPos();
		BlockState state = this.world.getBlockState(pos);
		if(state.getBlock() instanceof FroggyChairBlock) {
			Direction d = state.get(FroggyChairBlock.FACING);
			passenger.updatePosition(pos.getX() + d.getOffsetX() + 0.5D, pos.getY(), pos.getZ() + d.getOffsetZ() + 0.5D);
		}
		super.removePassenger(passenger);
	}

	@Override
	protected void addPassenger(Entity passenger) {
		BlockPos pos = this.getBlockPos();
		BlockState state = this.world.getBlockState(pos);
		if(state.getBlock() instanceof FroggyChairBlock) {
			Direction d = state.get(FroggyChairBlock.FACING);
			passenger.setYaw(d.getHorizontal() * 90F);
		}
		super.addPassenger(passenger);
	}

}