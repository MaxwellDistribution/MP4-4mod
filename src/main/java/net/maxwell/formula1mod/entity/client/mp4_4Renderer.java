package net.maxwell.formula1mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.maxwell.formula1mod.Formula1Mod;
import net.maxwell.formula1mod.entity.custom.mp4_4Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class mp4_4Renderer extends EntityRenderer<mp4_4Entity>
{
    private static final ResourceLocation MP4_4_TEXTURE = new ResourceLocation(Formula1Mod.MOD_ID, "textures/entity/mp4_4.png");
    public final mp4_4Model<mp4_4Entity> model;
    public mp4_4Renderer(EntityRendererProvider.Context pContext)
    {
        super(pContext);
        model = new mp4_4Model<>(pContext.bakeLayer(ModModelLayers.MP4_4_LAYER));
    }
    ///////////////////////////////////////////////////////
    public void renderText(PoseStack poseStack, MultiBufferSource buffer, String text, mp4_4Entity entity, int packedLight, float partialTicks) {
        Font font = this.getFont();

        float Yrotation = (float) Math.toRadians(entity.getYRot());
        float Ysin = (float) Math.sin(Yrotation);
        float Ycos = (float) Math.cos(Yrotation);

        poseStack.translate(0.5*Ysin, 0.6f, 0.5*Ycos);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(Yrotation)));

        poseStack.pushPose();
        poseStack.scale(-0.005F, -0.005F, 0.005F);

        // Центрирование текста
        float textWidth = font.width(text) / 2.0F;
        font.drawInBatch(text, -textWidth, 0, 0x959595, false, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, packedLight);
        poseStack.popPose();
    }
    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    private boolean isPlayerRiding(mp4_4Entity entity)
    {
        if (entity.getPassengers().isEmpty())
        {
            return false;
        }
        for (var passenger : entity.getPassengers())
        {
            if (passenger instanceof Player)
            {
                return true;
            }
        }
        return false;
    }
    ///////////////////////////////////////////////////////

    public void render(mp4_4Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();

        float rpm = pEntity.GetRPM();
        float grip = pEntity.GetGrip();
        //int gear = pEntity.GetGear();

        float Yrotation = (float) Math.toRadians(pEntity.getYRot());
        float Ysin = (float) Math.sin(Yrotation);
        float Ycos = (float) Math.cos(Yrotation);

        pPoseStack.scale(-1.0F, -1.0F, 1.0F);

        pPoseStack.translate(0.3*Ysin, -1.5f, -0.3+0.3*(1-Ycos));

        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));

        this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer $$24 = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        this.model.renderToBuffer(pPoseStack, $$24, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();

        if(isPlayerRiding(pEntity) && pEntity.GetOnOff())
        {
            int RPM = (int) pEntity.GetRPM();
            int gear = pEntity.GetGear();

            model.setGear(gear);

            double speed = Math.sqrt(pEntity.getDeltaMovement().x * pEntity.getDeltaMovement().x +
                    pEntity.getDeltaMovement().z * pEntity.getDeltaMovement().z);

            // Формирование текста
            String speedText = String.format("%.1f", speed*20) + "bps  " + RPM + " " + gear;

            // Отрисовка текста
            renderText(pPoseStack, pBuffer, speedText, pEntity, pPackedLight, pPartialTicks);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(mp4_4Entity pEntity)
    {
        return MP4_4_TEXTURE;
    }
}