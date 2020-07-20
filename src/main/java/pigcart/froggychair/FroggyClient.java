package pigcart.froggychair;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;

public class FroggyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.INSTANCE.register(FroggyChair.SIT_ENTITY_TYPE, (entityRenderDispatcher, context) -> new EmptyRenderer(entityRenderDispatcher));
		registerClientboundPackets();
	}

	private static class EmptyRenderer extends EntityRenderer<FroggySitEntity>

	{
		protected EmptyRenderer(EntityRenderDispatcher entityRenderDispatcher)
		{
			super(entityRenderDispatcher);
		}

		@Override
		public boolean shouldRender(FroggySitEntity entity, Frustum frustum, double d, double e, double f)
		{
			return false;
		}

		@Override
		public Identifier getTexture(FroggySitEntity entity)
		{
			return null;
		}
	}

	public static void registerClientboundPackets() {
		ClientSidePacketRegistry.INSTANCE.register(S2CEntitySpawnPacket.ID, S2CEntitySpawnPacket::onPacket);
	}
}
