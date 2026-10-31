package com.example.randomdiseases.client;

import net.fabricmc.api.ClientModInitializer;

public class RandomDiseasesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DiseaseHud.register();
    }
}