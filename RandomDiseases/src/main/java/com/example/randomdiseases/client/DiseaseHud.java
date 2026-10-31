package com.example.randomdiseases.client;

import com.example.randomdiseases.disease.Disease;
import com.example.randomdiseases.disease.DiseaseManager;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;

import java.util.List;

public class DiseaseHud {
    private static final Identifier HUD_ID = Identifier.fromNamespaceAndPath("random-diseases", "disease_hud");

    public static void register() {
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            HUD_ID,
            (context, tickCounter) -> render(context)
        );
    }

    private static void render(GuiGraphicsExtractor context) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        int screenWidth = context.guiWidth();
        int x = screenWidth - 160;
        int y = 10;

        int ticksLeft = DiseaseManager.getTicksUntilNext();
        int totalSeconds = ticksLeft / 20;
        String time = String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);

        context.text(client.font, Component.literal("⏳ Следующая болезнь: " + time).withStyle(ChatFormatting.YELLOW), x, y, 0xFFFFFF);
        y += 12;

        List<Disease> active = DiseaseManager.getActiveDiseases(client.player.getUUID());
        if (!active.isEmpty()) {
            context.text(client.font, Component.literal("🦠 Активные болезни:").withStyle(ChatFormatting.RED), x, y, 0xFFFFFF);
            y += 10;

            int shown = Math.min(active.size(), 8);
            for (int i = 0; i < shown; i++) {
                Disease d = active.get(i);
                ChatFormatting color = switch (d.severity) {
                    case 1 -> ChatFormatting.GREEN; case 2 -> ChatFormatting.YELLOW;
                    case 3 -> ChatFormatting.GOLD;  case 4 -> ChatFormatting.RED;
                    default -> ChatFormatting.DARK_RED;
                };
                String line = "• " + d.name + " [" + d.severity + "/5]";
                context.text(client.font, Component.literal(line).withStyle(color), x, y, 0xFFFFFF);
                y += 10;
            }
            if (active.size() > 8) {
                context.text(client.font, Component.literal("… и ещё " + (active.size() - 8)).withStyle(ChatFormatting.GRAY), x, y, 0xFFFFFF);
            }
        }
    }
}