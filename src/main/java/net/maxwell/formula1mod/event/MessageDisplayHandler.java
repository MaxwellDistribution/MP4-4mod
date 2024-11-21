package net.maxwell.formula1mod.event;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.maxwell.formula1mod.Formula1Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.maxwell.formula1mod.entity.custom.mp4_4Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

import static net.maxwell.formula1mod.entity.ModEntities.MP4_4;

@Mod.EventBusSubscriber(modid = Formula1Mod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MessageDisplayHandler
{
    private static String message = "";
    private static long messageStartTime = 0;
    private static final long DISPLAY_DURATION = 7000;
    private static boolean showMessage = true;
    private static String previousVehicleName = null;

    private static String currentVehicleName;

    mp4_4Entity carEntity = new mp4_4Entity(MP4_4.get(), Minecraft.getInstance().level);

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        Entity currentVehicle = player.getVehicle();

        if(currentVehicle == null)
        {
            if (event.phase != TickEvent.Phase.END) return;
            currentVehicleName = null;
            //System.out.println((Object) null);
        }
        else if(currentVehicle.isControlledByLocalInstance())
        {
            if (event.phase != TickEvent.Phase.START) return;
            currentVehicleName = currentVehicle.getName().getString();
            //System.out.println(currentVehicle.getName().getString());
        }

        if(!Objects.equals(currentVehicleName, previousVehicleName))
        {
            previousVehicleName = currentVehicleName;
            if(currentVehicle == null)
            {
                //currentVehicleName = null;
                //System.out.println((Object) null);
            }
            else
            {
                //currentVehicleName = currentVehicle.getName().getString();
                //System.out.println(currentVehicleName);
            }
            //System.out.println("PREVIOUS CHANGE");
        }
    }
    @SubscribeEvent
    public static void onMount(EntityMountEvent event)
    {
        //System.out.println(currentVehicleName + "1");
        //if(showMessage)
        if (event.getEntity().level().isClientSide() &&
                event.getEntity().getVehicle() != null &&
                event.getEntity().isControlledByLocalInstance() &&
                showMessage && Objects.equals(currentVehicleName, "entity.formula1mod.mp4_4"))
        {
            //System.out.println(currentVehicleName + "2");
            // Начинаем отображение сообщения
            messageStartTime = System.currentTimeMillis();
            showMessage = true;
            // Можно задать конкретный текст
            MessageDisplayHandler.setMessage("""
                    Press RMB to turn OFF/ON the engine
                    (Engine ON -> instruments appear on the dashboard)
                    Press Q to shift down a gear
                    Press space to shift up a gear
                    W to go forward
                    S to brake/go backwards
                    A/D to go left/right""");
            //System.out.println(currentVehicleName + "3");
        }
        //System.out.println(currentVehicleName + "4");
    }

    //(Engine ON -> instruments appear on the dashboard)
    public static void setMessage(String msg)
    {
        message = msg;
        messageStartTime = System.currentTimeMillis();
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event)
    {
        if (message.isEmpty()) return;

        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - messageStartTime;

        if (elapsed > DISPLAY_DURATION)
        {
            message = "";
            return;
        }

        float opacity = 1.0f;

        if (elapsed > DISPLAY_DURATION - 1000)
        { // Последняя секунда затухания
            opacity = 1.0f - ((float)(elapsed - (DISPLAY_DURATION - 1000)) / 1000.0f);
        }

        if (opacity < 0)
        {
            opacity = 0;
        }

        int alpha = (int)(opacity * 255);
        int color = (alpha << 24) | 0xFFFFFF;

        // Рендер текста с заданной прозрачностью
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        // Позиция: слева сверху
        int x = 10;
        int y = 10;

        // Установка прозрачности
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        String[] lines = message.split("\n");
        int lineHeight = font.lineHeight + 1; // Добавляем небольшой отступ между строками

        GuiGraphics guiGraphics = event.getGuiGraphics();

        for (String line : lines)
        {
            guiGraphics.drawString(font, line, x, y, color, false);
            y += lineHeight; // Смещаем y для следующей строки
        }

        //guiGraphics.drawString(font, message, x, y, color, false);
        //font.drawInBatch(message, x, y, color, true, , event.getGuiGraphics().bufferSource(), Font.DisplayMode.NORMAL, 0, 0, false);
        RenderSystem.disableBlend();
    }
}
