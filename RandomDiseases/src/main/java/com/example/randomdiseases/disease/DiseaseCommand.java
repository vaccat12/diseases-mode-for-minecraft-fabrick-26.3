package com.example.randomdiseases.disease;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;

public class DiseaseCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            dispatcher.register(
                Commands.literal("diseases")
                    .executes(ctx -> {
                        var src = ctx.getSource();
                        src.sendSuccess(() -> Component.literal("═══ Все болезни (" + DiseaseRegistry.all().size() + ") ═══").withStyle(ChatFormatting.GOLD), false);
                        int currentSeverity = -1;
                        for (Disease d : DiseaseRegistry.all()) {
                            if (d.severity != currentSeverity) {
                                currentSeverity = d.severity;
                                final int finalSeverity = currentSeverity;
                                src.sendSuccess(() -> Component.literal("▸ Степень " + finalSeverity).withStyle(ChatFormatting.YELLOW), false);
                            }
                            ChatFormatting color = switch (d.severity) {
                                case 1 -> ChatFormatting.GREEN; case 2 -> ChatFormatting.YELLOW;
                                case 3 -> ChatFormatting.GOLD;  case 4 -> ChatFormatting.RED;
                                default -> ChatFormatting.DARK_RED;
                            };
                            src.sendSuccess(() -> Component.literal("  • " + d.name + " (" + d.id + ") — " + d.description).withStyle(color), false);
                        }
                        return 1;
                    })
                    .then(Commands.literal("timer")
                        .executes(ctx -> {
                            int ticks = DiseaseManager.getTicksUntilNext();
                            int sec = ticks / 20;
                            String timeStr = String.format("%02d:%02d", sec / 60, sec % 60);
                            ctx.getSource().sendSuccess(() -> Component.literal("⏳ До следующей болезни: " + timeStr).withStyle(ChatFormatting.YELLOW), false);
                            return 1;
                        })
                    )
                    .then(Commands.literal("infect")
                        .then(Commands.argument("id", StringArgumentType.word())
                            .suggests((context, builder) -> {
                                return SharedSuggestionProvider.suggest(
                                    DiseaseRegistry.all().stream().map(d -> d.id).toList(),
                                    builder
                                );
                            })
                            .executes(ctx -> {
                                String id = StringArgumentType.getString(ctx, "id");
                                ServerPlayer player = ctx.getSource().getPlayerOrException();
                                
                                var found = DiseaseRegistry.all().stream().filter(d -> d.id.equalsIgnoreCase(id)).findFirst();
                                if (found.isEmpty()) {
                                    ctx.getSource().sendFailure(Component.literal("Болезнь не найдена: " + id));
                                    return 0;
                                }
                                
                                Disease d = found.get();
                                DiseaseManager.applyDisease(player, d);
                                ctx.getSource().sendSuccess(() -> Component.literal("Успешно вызвана болезнь: " + d.name).withStyle(ChatFormatting.GREEN), false);
                                return 1;
                            })
                        )
                    )
                    .then(Commands.argument("id", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            return SharedSuggestionProvider.suggest(
                                DiseaseRegistry.all().stream().map(d -> d.id).toList(),
                                builder
                            );
                        })
                        .executes(ctx -> {
                            String id = StringArgumentType.getString(ctx, "id");
                            var found = DiseaseRegistry.all().stream().filter(d -> d.id.equalsIgnoreCase(id)).findFirst();
                            if (found.isEmpty()) {
                                ctx.getSource().sendFailure(Component.literal("Болезнь не найдена: " + id));
                                return 0;
                            }
                            Disease d = found.get();
                            ctx.getSource().sendSuccess(() -> Component.literal("☠ " + d.name + " [Степень " + d.severity + "/5]").withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD), false);
                            ctx.getSource().sendSuccess(() -> Component.literal(d.description).withStyle(ChatFormatting.GRAY), false);
                            return 1;
                        }))
            );
        });
    }
}