package com.example.randomdiseases.disease;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import java.util.List;

public final class DiseaseRegistry {
    private static final int LONG = 20 * 60 * 5; // 5 минут
    
    public static final List<Disease> DISEASES = List.of(
        // ================= СТЕПЕНЬ 1 =================
        Disease.of("sneezing", "Внезапное чихание", 1, "Нос чешется, а резкие звуки заставляют жмуриться, теряя обзор на долю секунды.", SoundEvents.PANDA_SNEEZE, new MobEffectInstance(MobEffects.BLINDNESS, 20 * 1, 0), new MobEffectInstance(MobEffects.LUCK, LONG, 0)),
        Disease.of("hiccups", "Икота", 1, "Диафрагма предательски сокращается, вызывая легкое головокружение при каждом вздохе.", SoundEvents.PLAYER_BURP, new MobEffectInstance(MobEffects.NAUSEA, LONG, 0)),
        Disease.of("weak_knees", "Дрожь в коленях", 1, "Суставы издают неприятный хруст, а походка становится неуверенной и медленной.", SoundEvents.SKELETON_STEP, new MobEffectInstance(MobEffects.SLOWNESS, LONG, 0)),
        Disease.of("itchy_eyes", "Зуд в глазах", 1, "Постоянное желание почесать глаза заставляет картинку перед ними слегка плыть.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.NAUSEA, LONG, 0)),
        Disease.of("mild_fatigue", "Легкая усталость", 1, "Мышцы наливаются свинцом. Держать кирку или меч становится не так удобно.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.MINING_FATIGUE, LONG, 0)),
        Disease.of("stiff_neck", "Затекла шея", 1, "Любое резкое движение отдает болью, из-за чего удары выходят слабыми.", SoundEvents.SKELETON_STEP, new MobEffectInstance(MobEffects.WEAKNESS, LONG, 0), new MobEffectInstance(MobEffects.SLOWNESS, LONG, 0)),
        Disease.of("indigestion", "Несварение", 1, "Желудок тихо урчит, а пища усваивается быстрее, чем хотелось бы.", SoundEvents.PLAYER_BURP, new MobEffectInstance(MobEffects.HUNGER, LONG, 0)),
        Disease.of("drowsiness", "Сонливость", 1, "Веки тяжелеют. В глазах темнеет, а шаги становятся вялыми.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.SLOWNESS, LONG, 0), new MobEffectInstance(MobEffects.DARKNESS, LONG, 0)),
        
        // ================= СТЕПЕНЬ 2 =================
        Disease.of("fever", "Лихорадка", 2, "Тело ломит от жара. Вас слегка подташнивает, а руки теряют былую силу.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.WEAKNESS, LONG, 0), new MobEffectInstance(MobEffects.NAUSEA, LONG, 0)),
        Disease.of("migraine", "Мигрень", 2, "Пульсирующая боль в висках. Яркий свет режет глаза, вызывая приступы временной слепоты.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.NAUSEA, LONG, 1), new MobEffectInstance(MobEffects.BLINDNESS, 20 * 2, 0)),
        Disease.of("stomach_bug", "Кишечная инфекция", 2, "Спазмы в животе вызывают тошноту и дикий голод, который невозможно утолить.", SoundEvents.PLAYER_BURP, new MobEffectInstance(MobEffects.HUNGER, LONG, 0), new MobEffectInstance(MobEffects.NAUSEA, LONG, 0)),
        Disease.of("claustrophobia", "Клаустрофобия", 2, "Вам кажется, что стены сжимаются. Дышать становится тяжело, а движения сковывает страх.", SoundEvents.WARDEN_HEARTBEAT, new MobEffectInstance(MobEffects.SLOWNESS, LONG, 0), new MobEffectInstance(MobEffects.DARKNESS, LONG, 0)),
        Disease.of("tremor", "Тремор рук", 2, "Конечности предательски дрожат. Держать инструменты и наносить точные удары сложнее.", SoundEvents.SKELETON_STEP, new MobEffectInstance(MobEffects.MINING_FATIGUE, LONG, 0), new MobEffectInstance(MobEffects.WEAKNESS, LONG, 0)),
        Disease.of("mild_poisoning", "Пищевое отравление", 2, "Вы съели что-то не то. Токсины медленно распространяются по крови.", SoundEvents.SPIDER_HURT, new MobEffectInstance(MobEffects.POISON, LONG, 0)),
        Disease.of("vertigo", "Вертиго", 2, "Земля буквально уходит из-под ног. Пространство вокруг искажается, а координация нарушена.", SoundEvents.ENDERMAN_TELEPORT, new MobEffectInstance(MobEffects.NAUSEA, LONG, 1), new MobEffectInstance(MobEffects.SLOWNESS, LONG, 0)),
        Disease.of("insomnia", "Бессонница", 2, "Отсутствие сна изматывает. В глазах темнеет, а копать становится невыносимо тяжело.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.DARKNESS, LONG, 0), new MobEffectInstance(MobEffects.MINING_FATIGUE, LONG, 0)),

        // ================= СТЕПЕНЬ 3 =================
        Disease.of("bone_ache", "Костный хруст", 3, "Кости ломит так сильно, что ваши удары почти не наносят врагам вреда.", SoundEvents.SKELETON_STEP, new MobEffectInstance(MobEffects.WEAKNESS, LONG, 1)),
        Disease.of("high_fever", "Жар", 3, "Вы горите изнутри. Тело отказывается слушаться, а окружающий мир плывет перед глазами.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.NAUSEA, LONG, 1), new MobEffectInstance(MobEffects.SLOWNESS, LONG, 1), new MobEffectInstance(MobEffects.WEAKNESS, LONG, 0)),
        Disease.of("hallucinations", "Галлюцинации", 3, "Реальность искажается. Вы слышите шаги за спиной и видите мрак там, где его нет.", SoundEvents.ELDER_GUARDIAN_CURSE, new MobEffectInstance(MobEffects.NAUSEA, LONG, 2), new MobEffectInstance(MobEffects.DARKNESS, LONG, 0), new MobEffectInstance(MobEffects.BLINDNESS, 20 * 3, 0)),
        Disease.of("withering_touch", "Иссушающее прикосновение", 3, "Плоть начинает гнить заживо. Тьма медленно высасывает из вас жизнь.", SoundEvents.WITHER_HURT, new MobEffectInstance(MobEffects.WITHER, LONG, 0)),
        Disease.of("paranoia", "Паранойя", 3, "Дикий, иррациональный страх сковывает движения. Вам кажется, что за вами наблюдают из темноты.", SoundEvents.WARDEN_HEARTBEAT, new MobEffectInstance(MobEffects.WEAKNESS, LONG, 1), new MobEffectInstance(MobEffects.SLOWNESS, LONG, 1), new MobEffectInstance(MobEffects.DARKNESS, LONG, 0)),
        Disease.of("dehydration", "Обезвоживание", 3, "Жажда мучает вас. Силы покидают тело, а голод становится зверским.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.HUNGER, LONG, 2), new MobEffectInstance(MobEffects.MINING_FATIGUE, LONG, 0)),
        Disease.of("spasms", "Мышечные спазмы", 3, "Мышцы сводит судорогой. Каждый шаг отдается болью и требует невероятных усилий.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.NAUSEA, LONG, 1), new MobEffectInstance(MobEffects.SLOWNESS, LONG, 2)),
        Disease.of("blood_infection", "Заражение крови", 3, "Грязь из раны попала в кровь. Организм борется с инфекцией, теряя силы.", SoundEvents.SPIDER_HURT, new MobEffectInstance(MobEffects.POISON, LONG, 1), new MobEffectInstance(MobEffects.WEAKNESS, LONG, 0)),

        // ================= СТЕПЕНЬ 4 =================
        Disease.of("blind_terror", "Слепой ужас", 4, "Внезапная потеря зрения. Вы полностью дезориентированы и не можете найти выход.", SoundEvents.ELDER_GUARDIAN_CURSE, new MobEffectInstance(MobEffects.BLINDNESS, 20 * 30, 0), new MobEffectInstance(MobEffects.SLOWNESS, 20 * 30, 1)),
        Disease.of("bleeding", "Кровотечение", 4, "Рана не заживает. Вы медленно теряете кровь, становясь все слабее.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.WEAKNESS, LONG, 1)),
        Disease.of("soul_wither", "Иссушение души", 4, "Темная магия выжигает вашу душу, оставляя лишь пустую оболочку.", SoundEvents.WITHER_HURT, new MobEffectInstance(MobEffects.WITHER, 20 * 45, 1)),
        Disease.of("severe_poisoning", "Тяжелое отравление", 4, "Смертельная доза токсинов в крови. Яд разъедает вас изнутри.", SoundEvents.SPIDER_HURT, new MobEffectInstance(MobEffects.POISON, 20 * 40, 1), new MobEffectInstance(MobEffects.NAUSEA, LONG, 1)),
        Disease.of("lung_disease", "Болезнь легких", 4, "Вам не хватает воздуха даже на суше. Каждый вдох дается с хрипом и болью.", SoundEvents.PLAYER_SWIM, new MobEffectInstance(MobEffects.MINING_FATIGUE, LONG, 2), new MobEffectInstance(MobEffects.SLOWNESS, LONG, 1)),
        Disease.of("coma", "Кома", 4, "Вы впадаете в забытье. Тело парализовано, а сознание угасает.", SoundEvents.ANVIL_LAND, new MobEffectInstance(MobEffects.BLINDNESS, 20 * 90, 0), new MobEffectInstance(MobEffects.SLOWNESS, 20 * 90, 2), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 90, 1)),
        Disease.of("dark_fog", "Туман в сознании", 4, "Густой мрак застилает глаза. Ваши мысли путаются, а движения замедляются.", SoundEvents.ELDER_GUARDIAN_CURSE, new MobEffectInstance(MobEffects.DARKNESS, 20 * 60, 0), new MobEffectInstance(MobEffects.NAUSEA, 20 * 60, 1)),
        Disease.of("blood_loss", "Обильная кровопотеря", 4, "Жизнь утекает сквозь пальцы. Вы теряете сознание от потери крови.", SoundEvents.PLAYER_HURT, new MobEffectInstance(MobEffects.WEAKNESS, LONG, 2), new MobEffectInstance(MobEffects.DARKNESS, 20 * 30, 0)),

        // ================= СТЕПЕНЬ 5 =================
        Disease.of("total_paralysis", "Полный паралич", 5, "Вы не можете пошевелить ни единым мускулом. Остается только ждать конца.", SoundEvents.ANVIL_LAND, new MobEffectInstance(MobEffects.SLOWNESS, 20 * 60, 6), new MobEffectInstance(MobEffects.BLINDNESS, 20 * 60, 0)),
        Disease.of("plague", "Чума", 5, "Смертельный недуг пожирает вас изнутри, вызывая невыносимые муки и голод.", SoundEvents.WITHER_HURT, new MobEffectInstance(MobEffects.POISON, 20 * 60, 1), new MobEffectInstance(MobEffects.WITHER, 20 * 60, 0), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 60, 1), new MobEffectInstance(MobEffects.HUNGER, 20 * 60, 2)),
        Disease.of("necrosis", "Некроз", 5, "Ткани отмирают. Плоть отслаивается от костей, а боль становится невыносимой.", SoundEvents.WITHER_HURT, new MobEffectInstance(MobEffects.WITHER, 20 * 45, 2), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 45, 2)),
        Disease.of("void_sickness", "Искажение пустоты", 5, "Гравитация искажается. Неведомая сила тянет вас в небеса, лишая ориентации.", SoundEvents.ENDERMAN_TELEPORT, new MobEffectInstance(MobEffects.LEVITATION, 20 * 20, 0), new MobEffectInstance(MobEffects.NAUSEA, 20 * 20, 2)),
        Disease.of("deadly_poison", "Смертельный яд", 5, "Концентрированный яд останавливает сердце. Только чудо может вас спасти.", SoundEvents.SPIDER_HURT, new MobEffectInstance(MobEffects.POISON, 20 * 60, 1), new MobEffectInstance(MobEffects.NAUSEA, 20 * 60, 1), new MobEffectInstance(MobEffects.SLOWNESS, 20 * 60, 1)),
        Disease.of("flesh_rot", "Разложение плоти", 5, "Ваша плоть разлагается заживо, издавая невыносимое зловоние.", SoundEvents.WITHER_HURT, new MobEffectInstance(MobEffects.WITHER, 20 * 45, 1), new MobEffectInstance(MobEffects.POISON, 20 * 45, 1), new MobEffectInstance(MobEffects.HUNGER, 20 * 45, 2)),
        Disease.of("soul_tear", "Разрыв души", 5, "Душа разрывается на части. Тьма поглощает вас, а сердце пропускает удары.", SoundEvents.WITHER_DEATH, new MobEffectInstance(MobEffects.WITHER, 20 * 30, 2), new MobEffectInstance(MobEffects.DARKNESS, 20 * 30, 0), new MobEffectInstance(MobEffects.SLOWNESS, 20 * 30, 2)),
        Disease.of("absolute_despair", "Абсолютное отчаяние", 5, "Безысходность парализует волю. Мир вокруг рушится, и надежды больше нет.", SoundEvents.ELDER_GUARDIAN_CURSE, new MobEffectInstance(MobEffects.SLOWNESS, 20 * 90, 4), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 90, 3), new MobEffectInstance(MobEffects.BLINDNESS, 20 * 90, 0))
    );

    private DiseaseRegistry() {}

    public static List<Disease> all() { return DISEASES; }

    public static List<Disease> byMaxSeverity(int maxSeverity) {
        return DISEASES.stream().filter(d -> d.severity <= maxSeverity).toList();
    }
}