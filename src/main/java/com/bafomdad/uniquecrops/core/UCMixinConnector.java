package com.bafomdad.uniquecrops.core;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class UCMixinConnector implements IMixinConnector {

    @Override
    public void connect() {

        Mixins.addConfiguration("assets/uniquecrops/uniquecrops.mixins.json");
    }
}
