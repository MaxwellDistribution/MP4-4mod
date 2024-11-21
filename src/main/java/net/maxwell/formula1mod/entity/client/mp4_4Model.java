package net.maxwell.formula1mod.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.maxwell.formula1mod.entity.animation.ModAnimationDefinitions;
import net.maxwell.formula1mod.entity.custom.mp4_4Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import static net.maxwell.formula1mod.entity.ModEntities.MP4_4;

public class mp4_4Model<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart car;
    private final ModelPart body;
    private final ModelPart front_suspension;
    private final ModelPart rear_suspension;
    private final ModelPart seat;
    private final ModelPart wheels;
    private final ModelPart LF;
    private final ModelPart LR;
    private final ModelPart RF;
    private final ModelPart RR;
    public float initialAngleFront = 0;
    public float initialAngleRear = 0;
    public float LeftFrontRot = 0;
    public float LeftRearRot = 0;
    public float RightFrontRot = 0;
    public float RightRearRot = 0;
    public float Turning = 0;
    public int gear;

    public mp4_4Model(ModelPart root) {
        this.car = root.getChild("car");
        this.body = car.getChild("body");
        this.front_suspension = car.getChild("front_suspension");
        this.rear_suspension = car.getChild("rear_suspension");
        this.seat = car.getChild("seat");
        this.wheels = car.getChild("wheels");
        this.LF = car.getChild("wheels").getChild("LF");
        this.LR = car.getChild("wheels").getChild("LR");
        this.RF = car.getChild("wheels").getChild("RF");
        this.RR = car.getChild("wheels").getChild("RR");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition car = partdefinition.addOrReplaceChild("car", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = car.addOrReplaceChild("body", CubeListBuilder.create().texOffs(38, 57).addBox(-2.0F, 2.0F, -11.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 135).addBox(2.0F, 3.0F, 41.0F, 1.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(38, 44).addBox(9.0F, 1.0F, 3.0F, 1.0F, 7.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(22, 117).addBox(17.0F, 2.0F, 9.0F, 1.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(87, 127).addBox(16.0F, 2.0F, 25.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 86).addBox(15.0F, 2.0F, 26.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(61, 43).addBox(14.0F, 2.0F, 27.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(13.0F, 2.0F, 28.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 97).addBox(8.0F, 3.0F, -11.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(79, 87).addBox(-3.0F, 3.0F, -11.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(42, 87).addBox(9.0F, 2.0F, -4.0F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(61, 159).addBox(17.0F, 3.0F, 3.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 60).addBox(16.0F, 3.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 120).addBox(10.0F, 3.0F, 7.0F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 120).addBox(-11.0F, 3.0F, 7.0F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 110).addBox(7.0F, 4.0F, -18.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(117, 138).addBox(19.0F, 8.0F, -34.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(87, 129).addBox(19.0F, 7.0F, -35.0F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 86).addBox(19.0F, 6.0F, -32.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(102, 93).addBox(19.0F, 5.0F, -30.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(118, 160).addBox(19.0F, 4.0F, -28.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(89, 100).addBox(19.0F, 3.0F, -26.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(85, 60).addBox(19.0F, 2.0F, -25.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(88, 90).addBox(19.0F, 1.0F, -24.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(103, 137).addBox(-14.0F, 8.0F, -34.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(72, 127).addBox(-14.0F, 7.0F, -35.0F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(76, 43).addBox(-14.0F, 6.0F, -32.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(79, 99).addBox(-14.0F, 5.0F, -30.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(110, 159).addBox(-14.0F, 4.0F, -28.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(9, 97).addBox(-14.0F, 3.0F, -26.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(65, 57).addBox(-14.0F, 2.0F, -25.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(79, 90).addBox(-14.0F, 1.0F, -24.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(62, 134).addBox(6.0F, 5.0F, -25.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(0.0F, 6.0F, -30.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(23, 37).addBox(5.0F, 6.0F, -30.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(96, 110).addBox(-1.0F, 5.0F, -25.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(66, 109).addBox(-2.0F, 4.0F, -18.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(124, 5).addBox(-12.0F, 2.0F, 9.0F, 1.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(126, 112).addBox(-3.0F, 1.0F, -4.0F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(157, 55).addBox(-3.0F, 2.0F, -4.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 129).addBox(-2.0F, 0.0F, -4.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(144, 59).addBox(-1.0F, 1.0F, -11.0F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(156, 136).addBox(0.0F, 0.0F, -11.0F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(102, 102).addBox(1.0F, 1.0F, -17.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 100).addBox(1.0F, 3.0F, -24.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 52).addBox(2.0F, 2.0F, -21.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(126, 156).addBox(0.0F, 2.0F, -18.0F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(61, 52).addBox(2.0F, 6.0F, -33.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(96, 29).addBox(5.0F, 8.0F, -33.0F, 14.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-13.0F, 8.0F, -33.0F, 14.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(143, 86).addBox(5.0F, 7.0F, -28.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(13, 141).addBox(-13.0F, 7.0F, -28.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 121).addBox(1.0F, 5.0F, -30.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(79, 99).addBox(2.0F, 4.0F, -27.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(154, 90).addBox(0.0F, 4.0F, -25.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(131, 138).addBox(-1.0F, 3.0F, -18.0F, 8.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(96, 0).addBox(2.0F, 7.0F, -35.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-12.0F, 8.0F, 3.0F, 30.0F, 1.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(38, 43).addBox(12.0F, 2.0F, 29.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(25, 0).addBox(11.0F, 2.0F, 31.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 110).addBox(10.0F, 2.0F, 33.0F, 1.0F, 6.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(55, 159).addBox(13.0F, -10.0F, 50.0F, 1.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(58, 87).addBox(13.0F, -10.0F, 49.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(138, 4).addBox(13.0F, -10.0F, 52.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 162).addBox(13.0F, -10.0F, 53.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 161).addBox(13.0F, -10.0F, 54.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 43).addBox(13.0F, -10.0F, 56.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(120, 0).addBox(-4.0F, 5.0F, 42.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(38, 69).addBox(-4.0F, 3.0F, 46.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(-4.0F, 4.0F, 44.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(126, 47).addBox(-4.0F, 8.0F, -4.0F, 14.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(126, 120).addBox(-3.0F, 8.0F, -11.0F, 12.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(129, 130).addBox(-2.0F, 8.0F, -18.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(17, 145).addBox(-1.0F, 8.0F, -25.0F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(96, 15).addBox(0.0F, 8.0F, -30.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(96, 143).addBox(1.0F, 7.0F, -34.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(30, 0).addBox(5.0F, -2.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-4.0F, 1.0F, 3.0F, 1.0F, 7.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(76, 60).addBox(-4.0F, 2.0F, -4.0F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(53, 151).addBox(10.0F, 2.0F, 2.0F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(96, 112).addBox(10.0F, 2.0F, 9.0F, 7.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(78, 87).addBox(10.0F, 1.0F, 16.0F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(21, 127).addBox(10.0F, 2.0F, 25.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 102).addBox(10.0F, 2.0F, 26.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 89).addBox(10.0F, 2.0F, 27.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(89, 105).addBox(10.0F, 2.0F, 28.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 96).addBox(10.0F, 2.0F, 29.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 90).addBox(10.0F, 2.0F, 31.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 57).addBox(10.0F, 8.0F, 2.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(120, 20).addBox(-11.0F, 8.0F, 2.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(51, 90).addBox(-5.0F, 2.0F, 31.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(118, 60).addBox(-12.0F, 3.0F, 3.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 57).addBox(-11.0F, 3.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(156, 128).addBox(-3.0F, 1.0F, 17.0F, 12.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 9).addBox(-2.0F, 3.0F, -6.0F, 10.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 37).addBox(-4.0F, 0.0F, 17.0F, 14.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 87).addBox(-2.0F, -1.0F, 17.0F, 10.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(106, 93).addBox(0.0F, -3.0F, 17.0F, 6.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(118, 60).addBox(0.0F, -4.0F, 17.0F, 6.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(136, 93).addBox(1.0F, -5.0F, 17.0F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(96, 0).addBox(1.0F, -6.0F, 17.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(9, 110).addBox(2.0F, -5.0F, 27.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 3).addBox(2.0F, -1.0F, 41.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(83, 37).addBox(2.0F, 0.0F, 44.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 40).addBox(2.0F, 1.0F, 47.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 51).addBox(2.0F, -3.0F, 35.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 40).addBox(2.0F, -6.0F, 24.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(92, 57).addBox(2.0F, -2.0F, 38.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(79, 103).addBox(2.0F, -4.0F, 31.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 87).addBox(-1.0F, -2.0F, 17.0F, 8.0F, 1.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(12, 86).addBox(-8.0F, 2.0F, 28.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(76, 43).addBox(-11.0F, 2.0F, 25.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 51).addBox(-10.0F, 2.0F, 26.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(69, 43).addBox(-8.0F, -10.0F, 56.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 18).addBox(-9.0F, 2.0F, 27.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-7.0F, 2.0F, 29.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, 2.0F, 31.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(144, 67).addBox(-4.0F, 1.0F, 39.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(145, 158).addBox(-2.0F, 1.0F, 42.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(160, 73).addBox(-1.0F, 1.0F, 44.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 124).addBox(-1.0F, 0.0F, 41.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(71, 136).addBox(0.0F, 0.0F, 43.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 124).addBox(0.0F, -1.0F, 39.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(71, 134).addBox(0.0F, 1.0F, 46.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 23).addBox(-3.0F, 1.0F, 40.0F, 12.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(158, 42).addBox(-2.0F, 0.0F, 39.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(102, 79).addBox(-4.0F, 2.0F, 39.0F, 14.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(143, 147).addBox(-3.0F, 1.0F, 15.0F, 12.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 20).addBox(-4.0F, 0.0F, 15.0F, 14.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(158, 39).addBox(-2.0F, -1.0F, 15.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 54).addBox(-1.0F, -2.0F, 15.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 73).addBox(0.0F, -4.0F, 15.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(1.0F, -6.0F, 15.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(149, 29).addBox(-11.0F, 2.0F, 2.0F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(96, 0).addBox(-5.0F, 1.0F, 16.0F, 1.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(118, 71).addBox(-10.0F, 2.0F, 25.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-9.0F, 2.0F, 26.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 87).addBox(-8.0F, 2.0F, 27.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 99).addBox(-7.0F, 2.0F, 28.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(102, 93).addBox(-6.0F, 2.0F, 29.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(66, 110).addBox(-11.0F, 2.0F, 9.0F, 7.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(38, 43).addBox(11.0F, 8.0F, 39.0F, 5.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(36, 142).addBox(10.0F, 8.0F, 41.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(38, 66).addBox(-5.0F, 8.0F, 39.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(136, 105).addBox(-4.0F, 7.0F, 39.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(126, 55).addBox(-4.0F, 6.0F, 40.0F, 14.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(92, 150).addBox(-5.0F, 8.0F, 41.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-10.0F, 8.0F, 39.0F, 5.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(45, 109).addBox(-5.0F, 2.0F, 33.0F, 1.0F, 6.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(49, 157).addBox(-8.0F, -10.0F, 50.0F, 1.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 87).addBox(-8.0F, -10.0F, 52.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 162).addBox(-8.0F, -10.0F, 53.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 37).addBox(-8.0F, -10.0F, 54.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(38, 37).addBox(-7.0F, -7.0F, 48.0F, 20.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(118, 75).addBox(-7.0F, -8.0F, 53.0F, 20.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(21, 112).addBox(-7.0F, -9.0F, 55.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 110).addBox(-7.0F, -10.0F, 56.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(49, 134).addBox(3.0F, 3.0F, 41.0F, 1.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(26, 162).addBox(-8.0F, -10.0F, 47.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 51).addBox(-8.0F, -10.0F, 49.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 162).addBox(13.0F, -10.0F, 48.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(120, 93).addBox(13.0F, -10.0F, 47.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(94, 37).addBox(-8.0F, -10.0F, 48.0F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 87).addBox(4.0F, -1.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(51, 87).addBox(5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 37).addBox(6.0F, -1.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(85, 64).addBox(2.0F, -1.0F, -5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(65, 61).addBox(2.0F, -2.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 47).addBox(1.0F, -2.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 44).addBox(1.0F, -3.0F, 1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 18).addBox(0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(79, 87).addBox(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 87).addBox(1.0F, -1.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(7, 37).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, -16.0F));

        PartDefinition front_suspension = car.addOrReplaceChild("front_suspension", CubeListBuilder.create(), PartPose.offset(-8.0F, -3.0F, -34.0F));

        PartDefinition cube_r1 = front_suspension.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(144, 69).addBox(-8.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 26).addBox(-8.0F, -1.0F, 2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r2 = front_suspension.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(136, 109).addBox(-13.0F, -1.5F, 6.0F, 17.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.0F, 4.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition cube_r3 = front_suspension.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(151, 0).addBox(-8.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(144, 71).addBox(-8.0F, -1.0F, 2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r4 = front_suspension.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(21, 114).addBox(-4.0F, -1.5F, 6.0F, 17.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -1.0F, 4.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition cube_r5 = front_suspension.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(154, 2).addBox(-4.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(154, 99).addBox(-4.0F, -1.0F, 10.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, -4.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r6 = front_suspension.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(154, 101).addBox(-4.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(145, 156).addBox(-4.0F, -1.0F, 10.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -2.0F, -4.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition rear_suspension = car.addOrReplaceChild("rear_suspension", CubeListBuilder.create(), PartPose.offset(-12.0F, -5.0F, 25.0F));

        PartDefinition cube_r7 = rear_suspension.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(157, 117).addBox(-8.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 115).addBox(-8.0F, -1.0F, 2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r8 = rear_suspension.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(157, 111).addBox(-8.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 113).addBox(-8.0F, -1.0F, 2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r9 = rear_suspension.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(157, 120).addBox(-4.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 122).addBox(-4.0F, -1.0F, 10.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.0F, 2.0F, -4.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r10 = rear_suspension.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(158, 37).addBox(-4.0F, -1.0F, 6.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(157, 124).addBox(-4.0F, -1.0F, 2.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition seat = car.addOrReplaceChild("seat", CubeListBuilder.create().texOffs(76, 60).addBox(-6.0F, -1.0F, -17.0F, 12.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));

        PartDefinition wheels = car.addOrReplaceChild("wheels", CubeListBuilder.create(), PartPose.offset(-15.0F, 1.0F, 26.0F));

        PartDefinition LF = wheels.addOrReplaceChild("LF", CubeListBuilder.create().texOffs(0, 153).addBox(-3.5F, -5.5F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(152, 161).addBox(-3.5F, -3.5F, 4.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 154).addBox(-3.5F, 4.5F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(126, 29).addBox(-3.5F, -4.5F, -4.5F, 7.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(161, 45).addBox(-3.5F, -3.5F, -5.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(33.5F, -6.5F, -55.5F));

        PartDefinition LR = wheels.addOrReplaceChild("LR", CubeListBuilder.create().texOffs(143, 78).addBox(-4.0F, -5.5F, -3.5F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(120, 4).addBox(-4.0F, -3.5F, -5.5F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-4.0F, -4.5F, -4.5F, 8.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(120, 12).addBox(-4.0F, -3.5F, 4.5F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(144, 12).addBox(-4.0F, 4.5F, -3.5F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(33.0F, -6.5F, 3.5F));

        PartDefinition RF = wheels.addOrReplaceChild("RF", CubeListBuilder.create().texOffs(74, 152).addBox(-3.5F, -5.5F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(-3.5F, -4.5F, -4.5F, 7.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 161).addBox(-3.5F, -3.5F, -5.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(105, 151).addBox(-3.5F, 4.5F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(75, 160).addBox(-3.5F, -3.5F, 4.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -6.5F, -55.5F));

        PartDefinition RR = wheels.addOrReplaceChild("RR", CubeListBuilder.create().texOffs(144, 4).addBox(-4.0F, 4.5F, -3.5F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(21, 116).addBox(-4.0F, -3.5F, -5.5F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -4.5F, -4.5F, 8.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(73, 143).addBox(-4.0F, -5.5F, -3.5F, 8.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(42, 116).addBox(-4.0F, -3.5F, 4.5F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.5F, 3.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }
    public int pseudoTime = 0;
    public float slipAddRot = 0;
    float rotationAngleRadiansInitial = 0;
    float rotationAngleRadians = 0;
    int rotDirection = 1;
    public void setGear(int gear)
    {
        this.gear = gear;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        mp4_4Entity carEntity = new mp4_4Entity(MP4_4.get(), Minecraft.getInstance().level);

        double speed = entity.getDeltaMovement().horizontalDistance();

        double turningAngle;

        if(speed <= 0.2)
        {
            turningAngle = 22.5;
        }
        else if(speed > 0.2 && speed <= 0.3)
        {
            turningAngle = 20.0;
        }
        else if(speed > 0.3 && speed <= 0.4)
        {
            turningAngle = 17.5;
        }
        else
        {
            turningAngle = 12.5;
        }

        if(entity.isVehicle() && entity.isControlledByLocalInstance())
        {
            if (speed > 0.001)
            {
                if(gear == -1)
                {
                    rotDirection = -1;
                }
                else
                {
                    rotDirection = 1;
                }

                rotationAngleRadiansInitial = (float) (speed*32/22);
                rotationAngleRadians = rotDirection*rotationAngleRadiansInitial;
                float rotationAngleDegrees = (float) Math.toDegrees(rotationAngleRadians);

                if(rotationAngleDegrees >= 360 || rotationAngleDegrees <= -360)
                {
                    rotationAngleRadians = 0;
                }

                LeftFrontRot += rotationAngleRadians;
                LF.xRot = LeftFrontRot;

                RightFrontRot += rotationAngleRadians;
                RF.xRot = RightFrontRot;

                initialAngleFront = RightFrontRot;

                if((entity.getDeltaMovement().x <= 0.2 || entity.getDeltaMovement().z <= 0.2) &&
                        Minecraft.getInstance().options.keyUp.isDown() && gear != -1)
                {
                    slipAddRot = (float) ((4.0 - Math.pow(2.72, -0.05*pseudoTime)));

                    LeftRearRot += slipAddRot;
                    LR.xRot = LeftRearRot;

                    RightRearRot += slipAddRot;
                    RR.xRot = RightRearRot;

                    pseudoTime += 1;
                }
                else
                {
                    pseudoTime = 0;
                    if(Math.abs(rotationAngleRadians - slipAddRot) > 0.2 && gear != -1)
                    {
                        slipAddRot -= (float) ((slipAddRot - rotationAngleRadians)/1.005);

                        LeftRearRot += slipAddRot;
                        LR.xRot = LeftRearRot;

                        RightRearRot += slipAddRot;
                        RR.xRot = RightRearRot;
                    }
                    else
                    {
                        LeftRearRot += rotationAngleRadians;
                        LR.xRot = LeftRearRot;

                        RightRearRot += rotationAngleRadians;
                        RR.xRot = RightRearRot;
                    }
                }

                initialAngleRear = RightRearRot;

                //System.out.println(speed*20);
                //System.out.println(rotationAngleRadians);
                //System.out.println(LeftFrontRot);
                //System.out.println("FUCK OFF STUPID BITCH ASS MOD");
                //System.out.println(gear);
            }
            else
            {
                LF.xRot = initialAngleFront;
                RF.xRot = initialAngleFront;
                LR.xRot = initialAngleRear;
                RR.xRot = initialAngleRear;
            }

            if(Minecraft.getInstance().options.keyRight.isDown())
            {
                if(Turning < turningAngle/57.2)
                {
                    Turning += (float) (turningAngle/450);
                }
                else
                {
                    Turning = (float) (turningAngle/57.2);
                }
                LF.yRot = Turning;
                RF.yRot = Turning*((float) 14/15);
                //System.out.println("Turning right" + Turning);
            }
            else if(Minecraft.getInstance().options.keyLeft.isDown())
            {
                if(Turning > -turningAngle/57.2)
                {
                    Turning -= (float) (turningAngle/450);
                }
                else
                {
                    Turning = (float) (-turningAngle/57.2);
                }
                LF.yRot = Turning*((float) 14/15);
                RF.yRot = Turning;
                //System.out.println("Turning left" + Turning);
            }
            else
            {
                if(Turning < 0)
                {
                    Turning += (float) (turningAngle/450);
                }
                else if(Turning > 0)
                {
                    Turning -= (float) (turningAngle/450);
                }
                else
                {
                    Turning = 0;
                }
                LF.yRot = Turning;
                RF.yRot = Turning;
                //System.out.println("Zero steering" + Turning);
            }
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        car.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return car;
    }
}
