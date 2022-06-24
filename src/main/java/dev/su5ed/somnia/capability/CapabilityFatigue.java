package dev.su5ed.somnia.capability;

import dev.su5ed.somnia.Somnia;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class CapabilityFatigue {
    public static final Capability<Fatigue> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation NAME = new ResourceLocation(Somnia.MODID, "fatigue");
    
    private CapabilityFatigue() {}
}
