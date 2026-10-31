package com.example.randomdiseases.disease;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;

import java.util.*;

public class DiseaseManager {
    private static final int INTERVAL_TICKS = 20 * 60 * 5;
    private static int tickCounter = 0;
    private static final Random RANDOM = new Random();

    private static final Map<UUID, List<Disease>> activeDiseases = new HashMap<>();
    private static final Map<UUID, Integer> infectionTicks = new HashMap<>(); 
    private static final Map<UUID, Double[]> lastPos = new HashMap<>(); // Отслеживание координат

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(DiseaseManager::onServerTick);
        
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world.isClientSide() || !(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
            playHitSound(serverPlayer);
            return InteractionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClientSide() || !(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
            playHitSound(serverPlayer);
            return InteractionResult.PASS;
        });
    }

    private static void playHitSound(ServerPlayer player) {
        List<Disease> active = activeDiseases.get(player.getUUID());
        if (active != null && !active.isEmpty()) {
            Disease d = active.get(0);
            if (d.id.equals("bone_ache")) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SKELETON_HURT, SoundSource.AMBIENT, 0.6F, 1.0F);
            } else if (d.id.equals("spasms")) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_HURT, SoundSource.AMBIENT, 0.6F, 1.0F);
            }
        }
    }

    private static void onServerTick(MinecraftServer server) {
        tickCounter++;

        if (tickCounter % 20 == 0) {
            int secondsLeft = (INTERVAL_TICKS - tickCounter) / 20;
            if (secondsLeft < 0) secondsLeft = 0;
            String timeStr = String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60);
            Component msg = Component.literal("⏳ До следующей болезни: " + timeStr).withStyle(ChatFormatting.YELLOW);
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                player.connection.send(new ClientboundSetActionBarTextPacket(msg));
            }
        }

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            List<Disease> active = activeDiseases.get(player.getUUID());
            if (active == null || active.isEmpty()) continue;

            Disease d = active.get(0);
            int infTick = infectionTicks.getOrDefault(player.getUUID(), 0);

            if (player.tickCount - infTick < 100) continue; 

            boolean hasAnyEffect = false;
            for (MobEffectInstance effect : d.effects) {
                if (player.hasEffect(effect.getEffect())) {
                    hasAnyEffect = true;
                    break;
                }
            }

            if (!hasAnyEffect) {
                active.clear();
                infectionTicks.remove(player.getUUID());
                lastPos.remove(player.getUUID());
                player.sendSystemMessage(Component.literal("✔ Вы полностью излечились от болезни!").withStyle(ChatFormatting.GREEN));
                continue; 
            }

            // --- ШАГИ (Хруст) ---
            if (d.id.equals("weak_knees") || d.id.equals("stiff_neck")) {
                Double[] last = lastPos.get(player.getUUID());
                if (last == null) {
                    last = new Double[]{player.getX(), player.getZ()};
                    lastPos.put(player.getUUID(), last);
                }
                
                double dx = player.getX() - last[0];
                double dz = player.getZ() - last[1];
                double distSq = dx * dx + dz * dz;
                
                if (distSq > 0.0001) {
                    if (player.tickCount % 10 == 0) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SKELETON_STEP, SoundSource.AMBIENT, 0.6F, 1.0F);
                    }
                }
                last[0] = player.getX();
                last[1] = player.getZ();
            }

            // ОСТАЛЬНЫЕ ЗВУКИ
            else if (d.id.equals("hiccups")) {
                if (player.tickCount % 100 == 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.AMBIENT, 1.0F, 1.0F);
                    player.level().broadcastEntityEvent(player, (byte) 2); 
                }
            }
            else if (d.id.equals("sneezing") || d.id.equals("lung_disease")) {
                if (player.tickCount % 200 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0));
                    if (d.id.equals("sneezing")) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PANDA_SNEEZE, SoundSource.AMBIENT, 1.0F, 1.0F);
                    } else {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.DROWNED_AMBIENT, SoundSource.AMBIENT, 0.8F, 1.0F);
                    }
                    player.level().broadcastEntityEvent(player, (byte) 2);
                }
            }
            else if (d.id.equals("indigestion") || d.id.equals("stomach_bug")) {
                if (player.tickCount % 400 == 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.AMBIENT, 0.8F, 1.0F);
                    player.level().broadcastEntityEvent(player, (byte) 2);
                }
            }
            else if (d.id.equals("paranoia")) {
                if (player.tickCount % 200 == 0) { 
                    int choice = RANDOM.nextInt(4);
                    switch(choice) {
                        case 0 -> player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CREEPER_PRIMED, SoundSource.AMBIENT, 0.5F, 1.0F);
                        case 1 -> player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SCREAM, SoundSource.AMBIENT, 0.3F, 1.0F);
                        case 2 -> player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMBIENT_CAVE.value(), SoundSource.AMBIENT, 1.0F, 1.0F);
                        case 3 -> player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.AMBIENT, 1.0F, 1.0F);
                    }
                }
            }
            else if (d.id.equals("claustrophobia")) {
                if (player.tickCount % 80 == 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.AMBIENT, 1.0F, 1.0F);
                }
            }
            else if (d.id.equals("bleeding") || d.id.equals("blood_loss")) {
                if (player.tickCount % 300 == 0) { 
                    float damageAmount = d.id.equals("bleeding") ? 1.0F : 2.0F; 
                    player.hurt(player.damageSources().magic(), damageAmount);
                }
            }
            else if (d.id.equals("plague") || d.id.equals("deadly_poison")) {
                if (player.tickCount % 40 == 0) { 
                    player.hurt(player.damageSources().magic(), 1.0F);
                }
            }
            else if (d.id.equals("hallucinations") || d.id.equals("blind_terror") || d.id.equals("absolute_despair")) {
                if (player.tickCount % 240 == 0) {
                    if (RANDOM.nextBoolean()) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.AMBIENT, 0.5F, 1.0F);
                    } else {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.AMBIENT, 0.8F, 1.2F);
                    }
                }
            }
            else if (d.id.equals("void_sickness")) {
                if (player.tickCount % 100 == 0) {
                    if (RANDOM.nextBoolean()) {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_TRIGGER, SoundSource.AMBIENT, 0.3F, 1.2F);
                    } else {
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.AMBIENT, 1.0F, 0.8F);
                    }
                }
            }
            else if (d.id.equals("dark_fog")) {
                if (player.tickCount % 300 == 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_AMBIENT, SoundSource.AMBIENT, 0.6F, 1.0F);
                }
            }
            else if (d.id.equals("withering_touch") || d.id.equals("soul_wither") || d.id.equals("necrosis") || d.id.equals("flesh_rot") || d.id.equals("soul_tear")) {
                if (player.tickCount % 200 == 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITHER_HURT, SoundSource.AMBIENT, 0.4F, 1.0F);
                }
            }
        }

        if (tickCounter < INTERVAL_TICKS) return;
        tickCounter = 0;

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            List<Disease> available = getAvailableDiseases(player);
            if (!available.isEmpty()) {
                applyDisease(player, available.get(RANDOM.nextInt(available.size())));
            }
        }
    }

    public static void applyDisease(ServerPlayer player, Disease disease) {
        List<Disease> oldDiseases = activeDiseases.get(player.getUUID());
        if (oldDiseases != null) {
            for (Disease oldD : oldDiseases) {
                for (MobEffectInstance effect : oldD.effects) {
                    player.removeEffect(effect.getEffect());
                }
            }
            oldDiseases.clear();
        } else {
            activeDiseases.put(player.getUUID(), new ArrayList<>());
        }

        activeDiseases.get(player.getUUID()).add(disease);
        disease.effects.forEach(player::addEffect);
        infectionTicks.put(player.getUUID(), player.tickCount);
        lastPos.put(player.getUUID(), new Double[]{player.getX(), player.getZ()}); // Запоминаем позицию

        if (disease.sound != null) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), disease.sound, SoundSource.AMBIENT, 1.0F, 1.0F);
        }

        Component message = Component.literal("☠ Вы заболели: ")
            .withStyle(ChatFormatting.RED)
            .append(Component.literal(disease.name).withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD))
            .append(Component.literal(" (Степень " + disease.severity + "/5)").withStyle(ChatFormatting.GOLD))
            .append(Component.literal("\n» " + disease.description).withStyle(ChatFormatting.GRAY));

        player.sendSystemMessage(message);
    }

    private static List<Disease> getAvailableDiseases(ServerPlayer player) {
        int infections = activeDiseases.getOrDefault(player.getUUID(), List.of()).size();
        int maxSeverity = Math.min(1 + infections / 2, 5);
        return DiseaseRegistry.byMaxSeverity(maxSeverity);
    }

    public static List<Disease> getActiveDiseases(UUID playerUuid) {
        return activeDiseases.getOrDefault(playerUuid, List.of());
    }

    public static int getTicksUntilNext() {
        return INTERVAL_TICKS - tickCounter;
    }
}