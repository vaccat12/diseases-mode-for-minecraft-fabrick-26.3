package com.example.randomdiseases.disease;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundEvent;
import java.util.List;

public class Disease {
    public final String id;
    public final String name;
    public final int severity;
    public final String description;
    public final List<MobEffectInstance> effects;
    public final SoundEvent sound;

    public Disease(String id, String name, int severity, String description, List<MobEffectInstance> effects, SoundEvent sound) {
        this.id = id;
        this.name = name;
        this.severity = severity;
        this.description = description;
        this.effects = effects;
        this.sound = sound;
    }

    public static Disease of(String id, String name, int severity, String desc, MobEffectInstance... effects) {
        return new Disease(id, name, severity, desc, List.of(effects), null);
    }

    public static Disease of(String id, String name, int severity, String desc, SoundEvent sound, MobEffectInstance... effects) {
        return new Disease(id, name, severity, desc, List.of(effects), sound);
    }
}