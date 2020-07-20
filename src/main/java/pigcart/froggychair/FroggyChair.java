package pigcart.froggychair;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.block.Block;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FroggyChair implements ModInitializer
{
	public static final String MOD_ID = "froggychair";
	public static final EntityType<FroggySitEntity> SIT_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "froggy_sit"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, FroggySitEntity::new).size(EntityDimensions.fixed(0.001F, 0.001F)).build()
			);
	public static final Block FROGGY_CHAIR = new FroggyChairBlock();
	private static final Identifier PLAINS_HOUSE_LOOT_TABLE_ID = new Identifier("minecraft", "chests/village/village_plains_house");
	private static final Identifier TAIGA_HOUSE_LOOT_TABLE_ID = new Identifier("minecraft", "chests/village/village_taiga_house");
	private static final Identifier SNOWY_HOUSE_LOOT_TABLE_ID = new Identifier("minecraft", "chests/village/village_snowy_house");
	private static final Identifier SAVANNA_HOUSE_LOOT_TABLE_ID = new Identifier("minecraft", "chests/village/village_savanna_house");
	private static final Identifier DESERT_HOUSE_LOOT_TABLE_ID = new Identifier("minecraft", "chests/village/village_desert_house");

	@Override
	public void onInitialize()
	{
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "froggy_chair"), FROGGY_CHAIR);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "froggy_chair"), new BlockItem(FROGGY_CHAIR, new Item.Settings().group(ItemGroup.DECORATIONS)));

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (PLAINS_HOUSE_LOOT_TABLE_ID.equals(id) || TAIGA_HOUSE_LOOT_TABLE_ID.equals(id) || SNOWY_HOUSE_LOOT_TABLE_ID.equals(id) || SAVANNA_HOUSE_LOOT_TABLE_ID.equals(id) || DESERT_HOUSE_LOOT_TABLE_ID.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(ItemEntry.builder(FROGGY_CHAIR));

				supplier.pool(poolBuilder);
			}
		});
	}
}
