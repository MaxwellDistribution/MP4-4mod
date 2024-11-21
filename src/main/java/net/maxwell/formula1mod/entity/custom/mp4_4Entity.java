package net.maxwell.formula1mod.entity.custom;

import com.mojang.blaze3d.platform.InputConstants;
import net.maxwell.formula1mod.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class mp4_4Entity extends LivingEntity implements PlayerRideable
{
    public mp4_4Entity(EntityType<mp4_4Entity> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
    }

    int Gear = 0;
    int GearRatio;
    int GearAccel;
    boolean OnOff = false;
    boolean u = false;
    boolean d = false;
    boolean l = false;
    boolean right = false;
    boolean left = false;
    boolean checkRPM = true;
    boolean IsSliding = false;
    float newSpeed = 0;
    float rawSpeed = 0;
    float idleRPM = 5000;
    float RPM = 0;
    float maxRPM = 0;
    float tempRPM1;
    float tempRPM2;
    float deltaRPM;
    Vec3 SlidingDirection;
    Vec3 SlidingDirectionForAngle;
    float tempSlideSpeed;
    float externalUseGrip;
    int slipDegree = 0;
    public final AnimationState DrivingAnimation1State = new AnimationState();
    public boolean DoesLoseTraction(float grip)
    {
        if(grip < 2 && this.getSpeed() > 0.001f && Gear != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public float GetRPM()
    {
        return RPM;
    }
    public int GetGear()
    {
        return Gear;
    }
    public boolean GetOnOff()
    {
        return OnOff;
    }
    public float GetGrip()
    {
        return externalUseGrip;
    }
    public float SpinMultiplier(float grip)
    {
        return (3 - grip);
    }
    public Vec3 RotateVector(Vec3 initialVector, float angleInDegrees)
    {
        double angleInRadians = Math.toRadians(angleInDegrees);

        // Вычисляем компоненты нового вектора после поворота
        double cosAngle = Math.cos(angleInRadians);
        double sinAngle = Math.sin(angleInRadians);

        // Поворачиваем вектор
        double newX1 = initialVector.x * cosAngle - initialVector.z * sinAngle;
        double newZ1 = initialVector.x * sinAngle + initialVector.z * cosAngle;

        return new Vec3(newX1, 0, newZ1);
    }

    private void SetupAnimationStates()
    {
        if(this.getDeltaMovement().horizontalDistance() > 0.001)
        {
            DrivingAnimation1State.start(0);
            //System.out.println("Animation allowed in main class");
        }
        else
        {
            DrivingAnimation1State.stop();
            //System.out.println("Animation disallowed in main class");
        }
    }

    private float getFrictionFactor(BlockPos pos)
    {
        float friction = 0.91F;

        BlockState blockState = this.level().getBlockState(this.blockPosition().below());
        Block block = blockState.getBlock();

        if (block == Blocks.ICE) {
            friction = 0.99F;
        }
        else if (block == ModBlocks.ASPHALT_BLOCK.get())
        {
            friction = 0.85F;
        }
        else if (block == ModBlocks.HALF_RUBBERED_ASPHALT_BLOCK.get())
        {
            friction = 0.85F;
        }
        else if (block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.SMOOTH_STONE)
        {
            friction = 0.85F;
        }
        else if (block == Blocks.GRASS_BLOCK)
        {
            friction = 0.95F;
        }

        return friction;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots()
    {
        return List.of(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack)
    {

    }
    //boolean OnOff = false;

    @Override
    public boolean isVehicle()
    {
        return true;
    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4D)
                .add(Attributes.FOLLOW_RANGE, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0f)
                .add(Attributes.ATTACK_DAMAGE, 0.0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9f);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand)
    {
        if (!pPlayer.isPassenger())
        {
            boolean success = pPlayer.startRiding(this);

            if (success)
            {
                pPlayer.setYRot(this.getYRot());
                pPlayer.setXRot(this.getXRot());
                //System.out.println("Player successfully started riding the entity.");
                return InteractionResult.SUCCESS;
            }
            /*else
            {
                //System.out.println("Failed to start riding the entity.");
            }*/
        }
        /*else
        {
            System.out.println("Player is already a passenger.");
        }*/

        return InteractionResult.PASS;
    }

    @Override
    public boolean startRiding(Entity rider)
    {
        Player player = (Player) rider;
        return super.startRiding(player);
    }

    @Override
    public double getPassengersRidingOffset()
    {
        return -0.4;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger()
    {
        return (LivingEntity) this.getFirstPassenger();
    }

    @Override
    public void travel(Vec3 pTravelVector)
    {
        if(this.isVehicle() && getControllingPassenger() instanceof Player)
        {
            LivingEntity livingEntity = this.getControllingPassenger();

            if(this.isControlledByLocalInstance())
            {
                float turnSpeed = 0;

                BlockPos blockPos = this.blockPosition();
                float frictionFactor = getFrictionFactor(blockPos);

                //System.out.println("general");
                float tempF = this.getSpeed();



                float downforce;
                if(Math.abs(this.getSpeed()) < 0.2f)
                {
                    downforce = tempF/0.25f;
                }
                else
                {
                    downforce = (float) Math.pow((tempF/0.25), 2);
                }

                float Grip;

                if(Gear == 0)
                {
                    rawSpeed = 0;
                    GearRatio = 50;
                    GearAccel = 350;
                    //System.out.println("neutral");
                }
                else if(Gear == 1)
                {
                    rawSpeed = 0.25f;
                    GearRatio = 100;
                    GearAccel = 300;
                    //System.out.println("1");
                }
                else if (Gear == 2)
                {
                    rawSpeed = 0.3f;
                    GearRatio = 20;
                    GearAccel = 220;
                    //System.out.println("2");
                }
                else if (Gear == 3)
                {
                    rawSpeed = 0.4f;
                    GearRatio = 10;
                    GearAccel = 150;
                    //System.out.println("3");
                }
                else if (Gear == 4)
                {
                    rawSpeed = 0.5f;
                    GearRatio = 5;
                    GearAccel = 80;
                    //System.out.println("4");
                }
                else if(Gear == -1)
                {
                    rawSpeed = -0.20f;
                    GearRatio = 50;
                    GearAccel = 250;
                    //System.out.println("-1");
                }

                if(Gear == 0)
                {
                    maxRPM = 10000;
                }
                else if(Gear == -1)
                {
                    maxRPM = 11000;
                }
                else
                {
                    maxRPM = 15000;
                }

                float accelSpeed = rawSpeed/10;
                float breakSpeed = 0.7f;

                if(rawSpeed > 0 && newSpeed <= rawSpeed && Minecraft.getInstance().options.keyUp.isDown() && Gear != -1)
                {
                    newSpeed = newSpeed + accelSpeed * (1 - frictionFactor);
                    //System.out.println("+");
                    //System.out.println(rawSpeed);
                    //System.out.println(accelSpeed);

                    if(newSpeed + accelSpeed * (1 - frictionFactor) > rawSpeed)
                    {
                        newSpeed = rawSpeed;
                        //System.out.println("0");
                    }

                    if(newSpeed < 0.001f)
                    {
                        newSpeed = 0;
                    }
                }
                else if(rawSpeed > 0 && newSpeed <= rawSpeed && Minecraft.getInstance().options.keyDown.isDown() && Gear != -1)
                {
                    newSpeed = newSpeed * breakSpeed;
                    //System.out.println("+");
                    //System.out.println(rawSpeed);
                    //System.out.println(accelSpeed);
                    if(newSpeed < 0.001f)
                    {
                        newSpeed = 0;
                    }
                }
                else if(rawSpeed < 0 && newSpeed >= rawSpeed && Minecraft.getInstance().options.keyDown.isDown() && Gear == -1)
                {
                    newSpeed = newSpeed + accelSpeed * (1 - frictionFactor);
                    //System.out.println("+");
                    //System.out.println(rawSpeed);
                    //System.out.println(accelSpeed);

                    if(newSpeed + accelSpeed * (1 - frictionFactor) < rawSpeed)
                    {
                        newSpeed = rawSpeed;
                        //System.out.println("0");
                    }

                    if(newSpeed > -0.001f)
                    {
                        newSpeed = 0;
                    }
                }
                else if(rawSpeed < 0 && newSpeed >= rawSpeed && Minecraft.getInstance().options.keyUp.isDown() && Gear == -1)
                {
                    newSpeed = newSpeed * breakSpeed;
                    //System.out.println("+");
                    //System.out.println(rawSpeed);
                    //System.out.println(accelSpeed);
                    if(newSpeed > -0.001f)
                    {
                        newSpeed = 0;
                    }
                }
                else if(this.getSpeed() != 0)
                {
                    newSpeed = newSpeed * frictionFactor;
                    //System.out.println("-");
                    tempF = tempF * frictionFactor;

                    if(Math.abs(newSpeed) < 0.001f)
                    {
                        newSpeed = 0.0f;
                        //System.out.println(newSpeed);
                    }
                    else if(Math.abs(tempF) < 0.001f)
                    {
                        tempF = 0.0f;
                        //System.out.println(tempF);
                    }
                }

                if((Minecraft.getInstance().options.keyUp.isDown() && Gear != -1) && RPM <= maxRPM+5500 && OnOff)
                {
                    RPM = RPM + (float) (GearAccel*Math.pow(GearRatio ,-((RPM-idleRPM)/15000)));
                    if(RPM + (float) (GearAccel*Math.pow(GearRatio ,-((RPM-idleRPM)/15000))) > maxRPM)
                    {
                        RPM = maxRPM;
                    }
                    //System.out.println(RPM + "_1_");
                }
                else if((Minecraft.getInstance().options.keyDown.isDown() && Gear == -1) && RPM <= maxRPM+5500 && OnOff)
                {
                    RPM = RPM + (float) (GearAccel*Math.pow(GearRatio ,-((RPM-idleRPM)/15000)));
                    if(RPM + (float) (GearAccel*Math.pow(GearRatio ,-((RPM-idleRPM)/15000))) > maxRPM)
                    {
                        RPM = maxRPM;
                    }
                }
                else if((Minecraft.getInstance().options.keyUp.isDown() || Minecraft.getInstance().options.keyDown.isDown()) && Gear == 0 && RPM <= maxRPM+5500 && OnOff)
                {
                    RPM = RPM + (float) (GearAccel*Math.pow(GearRatio ,-((RPM-idleRPM)/15000)));
                    if(RPM + (float) (GearAccel*Math.pow(GearRatio ,-((RPM-idleRPM)/15000))) > maxRPM)
                    {
                        RPM = maxRPM;
                    }
                }
                else if(RPM <= maxRPM+5500 && RPM >= idleRPM && OnOff)
                {
                    RPM = RPM - (float) (GearAccel*(1 - Math.pow(60 ,-((RPM-idleRPM)/15000))));
                    if(RPM - (float) (GearAccel*(1 - Math.pow(60 ,-((RPM-idleRPM)/15000)))) < idleRPM)
                    {
                        RPM = idleRPM;
                    }
                    //System.out.println(RPM + "_2_");
                }
                else if(!OnOff)
                {
                    RPM = RPM - (float) (GearAccel*(1 - Math.pow(60 ,-((RPM-idleRPM)/15000))));
                    if(RPM - (float) (GearAccel*(1 - Math.pow(60 ,-((RPM-idleRPM)/15000)))) < 0)
                    {
                        RPM = 0;
                    }
                    //System.out.println(RPM + "_3_");
                }

                if(checkRPM)
                {
                    tempRPM1 = RPM;
                    checkRPM = false;
                }

                if(RPM != tempRPM1)
                {
                    tempRPM2 = RPM;
                    deltaRPM = Math.abs(tempRPM2 - tempRPM1);
                    tempRPM1 = tempRPM2;
                    //System.out.println("DeltaRPM between ticks = " + deltaRPM);
                    //checkRPM = true;
                }
                else
                {
                    deltaRPM = 0;
                    //System.out.println("DeltaRPM between ticks = " + deltaRPM);
                }

                if(this.getSpeed() != 0)
                {
                    Grip = (float) (1 + downforce - 1.5*(1 - Math.pow(2.72, -(deltaRPM/100))));
                    if(Grip < 0)
                    {
                        Grip = 0;
                    }
                    //System.out.println("Grip value = " + Grip);
                }
                else
                {
                    Grip = 1;
                    //System.out.println("Grip value = " + Grip);
                }
                externalUseGrip = Grip;


                if(this.getSpeed() <= 0.0001f && this.getSpeed() >= 0)
                {
                    turnSpeed = 0f;
                }
                else if(this.getSpeed() <= 0.2f && this.getSpeed() > 0)
                {
                    turnSpeed = 4.0f;
                }
                else if(this.getSpeed() <= 0.3f && this.getSpeed() > 0.2f)
                {
                    turnSpeed = 3.5f;
                }
                else if(this.getSpeed() <= 0.4f && this.getSpeed() > 0.3f)
                {
                    turnSpeed = 2.5f;
                }
                else if(this.getSpeed() > 0.4f)
                {
                    turnSpeed = 1.0f;
                }
                else if (this.getSpeed() < 0)
                {
                    turnSpeed = 3.5f;
                }



                Vec3 Direction = this.calculateViewVector(0, this.getYRot());

                Vec3 DrivingDirection = RotateVector(Direction, -3 * this.getYRot());

                if(DoesLoseTraction(Grip))
                {
                    if(!IsSliding)
                    {
                        SlidingDirection = DrivingDirection;
                        //System.out.println("First direction = " + SlidingDirection);
                    }
                    else if(Minecraft.getInstance().options.keyUp.isDown() && IsSliding)
                    {
                        SlidingDirection = SlidingDirection.add(DrivingDirection.scale(0.25)).normalize();
                        //System.out.println("Powered direction = " + SlidingDirection);
                    }

                    IsSliding = true;

                    //SlidingDirection = RotateVector(SlidingDirection, -3 * this.getYRot());
                    //SlidingDirection = RotateVector(SlidingDirection, turnSpeed * SpinMultiplier(Grip));

                    if(Minecraft.getInstance().options.keyRight.isDown() && Minecraft.getInstance().options.keyUp.isDown())
                    {
                        right = true;
                        left = false;
                        this.setYRot(this.getYRot() - turnSpeed * SpinMultiplier(Grip));
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                        tempSlideSpeed = turnSpeed * SpinMultiplier(Grip);
                        //System.out.println("Right Pressed = " + SlidingDirection);
                    }
                    else if(Minecraft.getInstance().options.keyRight.isDown() && !Minecraft.getInstance().options.keyUp.isDown())
                    {
                        right = true;
                        left = false;
                        this.setYRot(this.getYRot() - turnSpeed * SpinMultiplier(Grip));
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                        tempSlideSpeed = turnSpeed * SpinMultiplier(Grip);
                        //System.out.println("Right Pressed = " + SlidingDirection);
                        SlidingDirection = RotateVector(SlidingDirection, turnSpeed * SpinMultiplier(Grip));
                    }

                    if(right && !Minecraft.getInstance().options.keyRight.isDown())
                    {
                        tempSlideSpeed = tempSlideSpeed * 0.5f;
                        this.setYRot(this.getYRot() - tempSlideSpeed);
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                        if(tempSlideSpeed < 0.01)
                        {
                            tempSlideSpeed = 0;
                            right = false;
                        }
                        //System.out.println("Right Unpressed = " + SlidingDirection);
                    }

                    if(Minecraft.getInstance().options.keyLeft.isDown() && Minecraft.getInstance().options.keyUp.isDown())
                    {
                        left = true;
                        right = false;
                        this.setYRot(this.getYRot() + turnSpeed * SpinMultiplier(Grip));
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                        tempSlideSpeed = turnSpeed * SpinMultiplier(Grip);
                        //System.out.println("Left Pressed = " + SlidingDirection);
                    }
                    else if(Minecraft.getInstance().options.keyLeft.isDown() && !Minecraft.getInstance().options.keyUp.isDown())
                    {
                        left = true;
                        right = false;
                        this.setYRot(this.getYRot() + turnSpeed * SpinMultiplier(Grip));
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                        tempSlideSpeed = turnSpeed * SpinMultiplier(Grip);
                        //System.out.println("Left Pressed = " + SlidingDirection);
                        SlidingDirection = RotateVector(SlidingDirection, -turnSpeed * SpinMultiplier(Grip));
                    }

                    if(left && !Minecraft.getInstance().options.keyLeft.isDown())
                    {
                        tempSlideSpeed = tempSlideSpeed * 0.5f;
                        this.setYRot(this.getYRot() + tempSlideSpeed);
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                        if(tempSlideSpeed < 0.01)
                        {
                            tempSlideSpeed = 0;
                            left = false;
                        }
                        //System.out.println("Left Unpressed = " + SlidingDirection);
                    }

                    if(Minecraft.getInstance().options.keyUp.isDown() && Gear != 0 && Gear != -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(SlidingDirection);
                        //System.out.println(newSpeed);
                    }
                    else if(Minecraft.getInstance().options.keyDown.isDown() && Gear != 0 && Gear != -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(SlidingDirection);
                        //System.out.println(newSpeed);
                    }
                    else if(Minecraft.getInstance().options.keyDown.isDown() && Gear == -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(SlidingDirection);
                        //System.out.println(newSpeed);
                    }
                    else if(Minecraft.getInstance().options.keyUp.isDown() && Gear == -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(SlidingDirection);
                        //System.out.println(newSpeed);
                    }
                    else
                    {
                        this.setSpeed(tempF);
                        super.travel(SlidingDirection);
                        //System.out.println("No pressing only");
                        //System.out.println(tempF);
                    }
                }
                else
                {
                    IsSliding = false;

                    if(Minecraft.getInstance().options.keyRight.isDown() && Gear != -1)
                    {
                        this.setYRot(this.getYRot() - turnSpeed);
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                    }
                    else if(Minecraft.getInstance().options.keyRight.isDown() && Gear == -1)
                    {
                        this.setYRot(this.getYRot() + turnSpeed);
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                    }

                    if(Minecraft.getInstance().options.keyLeft.isDown() && Gear != -1)
                    {
                        this.setYRot(this.getYRot() + turnSpeed);
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                    }
                    else if(Minecraft.getInstance().options.keyLeft.isDown() && Gear == -1)
                    {
                        this.setYRot(this.getYRot() - turnSpeed);
                        this.yBodyRot = 360 - this.getYRot();
                        this.yHeadRot = 360 - this.getYRot();
                    }

                    if(Minecraft.getInstance().options.keyUp.isDown() && Gear != 0 && Gear != -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(DrivingDirection);
                        //System.out.println(newSpeed);
                    }
                    else if(Minecraft.getInstance().options.keyDown.isDown() && Gear != 0 && Gear != -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(DrivingDirection);
                        //System.out.println(newSpeed);
                    }
                    else if(Minecraft.getInstance().options.keyDown.isDown() && Gear == -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(DrivingDirection);
                        //System.out.println(newSpeed);
                    }
                    else if(Minecraft.getInstance().options.keyUp.isDown() && Gear == -1 && OnOff)
                    {
                        this.setSpeed(newSpeed);
                        super.travel(DrivingDirection);
                        //System.out.println(newSpeed);
                    }
                    else
                    {
                        this.setSpeed(tempF);
                        super.travel(DrivingDirection);
                        //System.out.println("No pressing only");
                        //System.out.println(tempF);
                    }
                }

                if(Minecraft.getInstance().options.keyJump.isDown() && !u && this.isControlledByLocalInstance())
                {
                    if(Gear < 4)
                    {
                        Gear += 1;
                        if(this.getSpeed() != 0)
                        {
                            RPM -= 1000;
                        }
                        System.out.println("Upshift " + "/" + Gear + "/");
                    }
                    else
                    {
                        System.out.println("Max gear");
                    }
                    u = true;
                }
                else if(!Minecraft.getInstance().options.keyJump.isDown())
                {
                    u = false;
                }

                if(Minecraft.getInstance().options.keyDrop.isDown() && !d && this.isControlledByLocalInstance())
                {
                    if(Gear > -1)
                    {
                        Gear -= 1;
                        if(this.getSpeed() != 0)
                        {
                            RPM += 1000;
                        }
                        System.out.println("Downshift " + "/" + Gear + "/");
                    }
                    else
                    {
                        System.out.println("Min gear");
                    }
                    d = true;
                }
                else if(!Minecraft.getInstance().options.keyDrop.isDown())
                {
                    d = false;
                }

                if(Minecraft.getInstance().options.keyUse.isDown() && !l && this.isControlledByLocalInstance())
                {
                    if(!OnOff)
                    {
                        OnOff = true;
                        RPM = idleRPM;
                        System.out.println("Vrooooom");
                    }
                    else
                    {
                        OnOff = false;
                        RPM = 0;
                        this.setSpeed(0f);
                        System.out.println("No vroooom");
                    }
                    l = true;
                }
                else if(!Minecraft.getInstance().options.keyUse.isDown())
                {
                    l = false;
                }
                GetGear();
            }
        }

        else
        {
            super.travel(pTravelVector);
        }
    }

    public boolean ShouldGenerateSmoke()
    {
        SlidingDirectionForAngle = this.getDeltaMovement();

        Vec3 Direction = this.calculateViewVector(0, this.getYRot());
        Vec3 DrivingDirection = RotateVector(Direction, -2 * this.getYRot());

        double slideAngleRadians = Math.atan2(SlidingDirectionForAngle.x, SlidingDirectionForAngle.z);
        float slideAngleDegrees = Math.abs((float) Math.toDegrees(slideAngleRadians));

        double viewAngleRadians = Math.atan2(DrivingDirection.x, DrivingDirection.z);
        float viewAngle = Math.abs((float) Math.toDegrees(viewAngleRadians));

        if(Minecraft.getInstance().options.keyUp.isDown())
        {
            if(IsSliding && Math.abs(slideAngleDegrees - viewAngle) <= 30 && this.getDeltaMovement().horizontalDistance() < 0.1)
            {
                //System.out.println(Math.abs(slideAngleDegrees - viewAngle) + "LESS than 30");
                System.out.println(Math.abs(slideAngleDegrees) + "Slide");
                System.out.println(Math.abs(viewAngle) + "View");
                return true;
            }
            else if(IsSliding && Math.abs(slideAngleDegrees - viewAngle) > 30)
            {
                //System.out.println(Math.abs(slideAngleDegrees - viewAngle) + "MORE than 30");
                System.out.println(Math.abs(slideAngleDegrees) + "Slide");
                System.out.println(Math.abs(viewAngle) + "View");
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(IsSliding && Math.abs(slideAngleDegrees - viewAngle) <= 30 && (this.getDeltaMovement().horizontalDistance() > 0.1 && this.getDeltaMovement().horizontalDistance() < 0.15))
            {
                return true;
            }
            else if(IsSliding && Math.abs(slideAngleDegrees - viewAngle) > 30 && this.getDeltaMovement().horizontalDistance() > 0.05)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public void GenerateSmokeUnderWheels()
    {
        double entityX = this.getX();
        double entityY = this.getY();
        double entityZ = this.getZ();

        float viewAngleInitial = (float) Math.toRadians(this.getYRot());
        float viewAngle = viewAngleInitial - 2 * (float) Math.toRadians(this.getYRot());

        double rearLeftWheelOffsetX = 0.9;
        double rearRightWheelOffsetX = -0.9;
        double rearWheelOffsetZ = -2.5;

        double distanceToWheel = Math.sqrt((Math.pow(0.9, 2)) + (Math.pow(rearWheelOffsetZ, 2)));

        double leftWheelX = entityX + distanceToWheel * Math.sin(viewAngle + Math.atan(rearLeftWheelOffsetX/rearWheelOffsetZ));
        double leftWheelZ = entityZ - distanceToWheel * Math.cos(viewAngle + Math.atan(rearLeftWheelOffsetX/rearWheelOffsetZ));

        double rightWheelX = entityX + distanceToWheel * Math.sin(viewAngle + Math.atan(rearRightWheelOffsetX/rearWheelOffsetZ));
        double rightWheelZ = entityZ - distanceToWheel * Math.cos(viewAngle + Math.atan(rearRightWheelOffsetX/rearWheelOffsetZ));

        double particleY = entityY + this.getBbHeight() * 0.5 - 0.1;

        int particlesPerWheel = 2;

        // Генерация частиц под правым задним колёсом
        for (int i = 0; i < particlesPerWheel; i++)
        {
            this.level().addParticle
                    (
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            rightWheelX + (this.random.nextDouble() - 0.25) * 0.75,
                            particleY,
                            rightWheelZ + (this.random.nextDouble() - 0.25) * 0.75,
                            0,
                            0.01,
                            0
                    );
        }

        // Генерация частиц под левым задним колёсом
        for (int i = 0; i < particlesPerWheel; i++)
        {
            this.level().addParticle
                    (
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            leftWheelX + (this.random.nextDouble() - 0.5) * 0.75,
                            particleY,
                            leftWheelZ + (this.random.nextDouble() - 0.5) * 0.75,
                            0,
                            0.01,
                            0
                    );
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.level().isClientSide())
        {
            SetupAnimationStates();
            //System.out.println("Animation state setup");
        }

        if(ShouldGenerateSmoke())
        {
            GenerateSmokeUnderWheels();
        }

        GetGear();
    }
}