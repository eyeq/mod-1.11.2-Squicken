package eyeq.squicken.item;

import eyeq.squicken.entity.projectile.EntityEggSquicken;
import eyeq.util.item.ItemThrow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ItemEggSquicken extends ItemThrow {
    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.ENTITY_EGG_THROW;
    }

    @Override
    public EntityThrowable createEntityThrowable(World world, EntityPlayer player) {
        return new EntityEggSquicken(world, player);
    }
}
