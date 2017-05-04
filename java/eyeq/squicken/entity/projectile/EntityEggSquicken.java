package eyeq.squicken.entity.projectile;

import eyeq.squicken.Squicken;
import eyeq.squicken.entity.passive.EntitySquicken;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEggSquicken extends EntityThrowable {
    public EntityEggSquicken(World world) {
        super(world);
    }

    public EntityEggSquicken(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    public EntityEggSquicken(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        for(int i = 0; i < 8; i++) {
            double xSpeed = (this.rand.nextFloat() - 0.5) * 0.08;
            double ySpeed = (this.rand.nextFloat() - 0.5) * 0.08;
            double zSpeed = (this.rand.nextFloat() - 0.5) * 0.08;
            this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, xSpeed, ySpeed, zSpeed, Item.getIdFromItem(Squicken.eggSquiken));
        }
        if(this.world.isRemote) {
            return;
        }
        EntitySquicken entity = new EntitySquicken(world);
        entity.setGrowingAge(-24000);
        entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        this.world.spawnEntity(entity);
        this.world.setEntityState(this, (byte) 3);
        this.setDead();
    }
}
