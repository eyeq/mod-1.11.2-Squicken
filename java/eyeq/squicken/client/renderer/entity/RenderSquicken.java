package eyeq.squicken.client.renderer.entity;

import eyeq.squicken.client.model.ModelSquicken;
import eyeq.squicken.entity.passive.EntitySquicken;
import eyeq.util.client.renderer.EntityRenderResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import static eyeq.squicken.Squicken.MOD_ID;

public class RenderSquicken extends RenderLiving<EntitySquicken> {
    protected static final ResourceLocation textures = new EntityRenderResourceLocation(MOD_ID, "squicken");
    protected static final ResourceLocation texturesChick = new EntityRenderResourceLocation(MOD_ID, "squick");

    private static float scale = 0.5F;

    public RenderSquicken(RenderManager renderManager) {
        super(renderManager, new ModelSquicken(), 0.3F);
    }

    @Override
    protected void preRenderCallback(EntitySquicken entity, float partialTickTime) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySquicken entity) {
        if(entity.isChild()) {
            return texturesChick;
        }
        return textures;
    }

    @Override
    protected float handleRotationFloat(EntitySquicken entity, float partialTicks) {
        float angle = entity.oFlap + (entity.wingRotation - entity.oFlap) * partialTicks;
        float f1 = entity.oFlapSpeed + (entity.destPos - entity.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(angle) + 1.0F) * f1;
    }
}
