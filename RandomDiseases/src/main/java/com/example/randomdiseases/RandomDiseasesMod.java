package com.example.randomdiseases;

import com.example.randomdiseases.disease.DiseaseCommand;
import com.example.randomdiseases.disease.DiseaseManager;
import net.fabricmc.api.ModInitializer;

public class RandomDiseasesMod implements ModInitializer {
    @Override
    public void onInitialize() {
        DiseaseManager.register();
        DiseaseCommand.register();
    }
}