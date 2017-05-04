package eyeq.squicken.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSquicken extends ModelBase {

    public ModelRenderer[] tentacles;
    public ModelRenderer body;
    public ModelRenderer bill;
    public ModelRenderer chin;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;

    public ModelSquicken() {
        bill = new ModelRenderer(this, 0, 0);
        bill.addBox(-2.0F, 2.0F, -8.0F, 4, 2, 2);
        bill.setRotationPoint(0.0F, 10.0F, 0.0F);
        chin = new ModelRenderer(this, 2, 4);
        chin.addBox(-1.0F, 4.0F, -8.0F, 2, 2, 2);
        chin.setRotationPoint(0.0F, 10.0F, 0.0F);

        body = new ModelRenderer(this, 0, 0);
        body.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
        body.setRotationPoint(0.0F, 10.0F, 0.0F);

        rightWing = new ModelRenderer(this, 48, 20);
        rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
        rightWing.setRotationPoint(-7.0F, 2.0F, 0.0F);
        body.addChild(rightWing);
        leftWing = new ModelRenderer(this, 48, 20);
        leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
        leftWing.setRotationPoint(7.0F, 2.0F, 0.0F);
        body.addChild(leftWing);

        rightLeg = new ModelRenderer(this, 36, 0);
        rightLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 6, 3);
        rightLeg.setRotationPoint(-2.0F, 18.0F, 1.0F);
        leftLeg = new ModelRenderer(this, 36, 0);
        leftLeg.addBox(-1.5F, 0.0F, -3.0F, 3, 6, 3);
        leftLeg.setRotationPoint(2.0F, 18.0F, 1.0F);

        tentacles = new ModelRenderer[8];
        double angle = Math.PI * 2 / tentacles.length;
        for(int i = 0; i < this.tentacles.length; i++) {
            float d = (float) (i * angle);
            float x = MathHelper.cos(d) * 5;
            float z = MathHelper.sin(d) * 5;
            this.tentacles[i] = new ModelRenderer(this, 48, 0);
            this.tentacles[i].addBox(-1.0F, -18.0F, -1.0F, 2, 18, 2, 0.0F);
            this.tentacles[i].setRotationPoint(x, -8.0F, z);
            this.body.addChild(this.tentacles[i]);
        }
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        if(this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            bill.render(scale);
            body.render(scale);
            rightLeg.render(scale);
            leftLeg.render(scale);
            GlStateManager.popMatrix();
        } else {
            bill.render(scale);
            chin.render(scale);
            body.render(scale);
            rightLeg.render(scale);
            leftLeg.render(scale);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        double angle = Math.PI * 2 / tentacles.length;
        for(int i = 0; i < tentacles.length; i++) {
            ModelRenderer tentacle = tentacles[i];
            if(i % 2 == 0) {
                tentacle.rotateAngleX = MathHelper.cos(limbSwing) * (limbSwingAmount + ageInTicks) / 3.0F;
            } else {
                tentacle.rotateAngleX = MathHelper.sin(limbSwing) * (limbSwingAmount + ageInTicks) / 3.0F;
            }
            tentacle.rotateAngleY = (float) (1.570796326794897 - angle * i);
        }
        this.body.rotateAngleX = headPitch * 0.017453292F;
        this.body.rotateAngleY = netHeadYaw * 0.017453292F;
        this.bill.rotateAngleX = this.body.rotateAngleX;
        this.bill.rotateAngleY = this.body.rotateAngleY;
        this.chin.rotateAngleX = this.body.rotateAngleX;
        this.chin.rotateAngleY = this.body.rotateAngleY;
        this.rightWing.rotateAngleZ = ageInTicks;
        this.leftWing.rotateAngleZ = -ageInTicks;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    }
}
