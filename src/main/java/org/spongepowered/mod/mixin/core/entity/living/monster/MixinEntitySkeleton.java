/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.mod.mixin.core.entity.living.monster;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3f;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.monster.Skeleton;
import org.spongepowered.api.entity.living.monster.SkeletonType;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.entity.projectile.source.ProjectileSource;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.mod.entity.SpongeEntityConstants;
import org.spongepowered.mod.entity.SpongeEntityMeta;
import org.spongepowered.mod.util.SpongeHooks;

@NonnullByDefault
@Mixin(EntitySkeleton.class)
@Implements(@Interface(iface = Skeleton.class, prefix = "skeleton$"))
public abstract class MixinEntitySkeleton extends EntityMob {

    @Shadow
    public abstract void setSkeletonType(int type);

    public MixinEntitySkeleton(World worldIn) {
        super(worldIn);
    }

    public SkeletonType skeleton$getSkeletonType() {
        return SpongeEntityConstants.SKELETON_IDMAP.get((int) this.dataWatcher.getWatchableObjectByte(13));
    }

    public void skeleton$setSkeletonType(SkeletonType skeletonType) {
        this.setSkeletonType(((SpongeEntityMeta) skeletonType).type);
    }

    public <T extends Projectile> T launchProjectile(Class<T> projectileClass) {
        return launchProjectile(projectileClass, null);
    }

    public <T extends Projectile> T launchProjectile(Class<T> projectileClass, Vector3f velocity) {
        double x = posX ;
        double y = getEntityBoundingBox().minY + (double)(height / 3.0F) - posY;
        double z = posZ;

        return (T) SpongeHooks.launchProjectile(getEntityWorld(), new Vector3d(x, y, z), ((ProjectileSource) this), projectileClass, velocity);
    }

}
