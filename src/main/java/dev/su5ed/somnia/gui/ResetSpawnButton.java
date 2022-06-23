package dev.su5ed.somnia.gui;

import dev.su5ed.somnia.capability.CapabilityFatigue;
import dev.su5ed.somnia.network.server.ResetSpawnPacket;
import dev.su5ed.somnia.network.SomniaNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;

public class ResetSpawnButton extends AbstractButton {
    public boolean resetSpawn = true;

    public ResetSpawnButton(int xIn, int yIn, int widthIn, int heightIn) {
        super(xIn, yIn, widthIn, heightIn, new TextComponent("Reset spawn: Yes"));
    }

    @Override
    public void onPress() {
        // TODO Localization
        this.resetSpawn = !this.resetSpawn;
        setMessage(new TextComponent("Reset spawn: " + (this.resetSpawn ? "Yes" : "No")));
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.getCapability(CapabilityFatigue.INSTANCE)
                .ifPresent(props -> SomniaNetwork.INSTANCE.sendToServer(new ResetSpawnPacket(this.resetSpawn)));
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.HINT, getMessage());
    }
}