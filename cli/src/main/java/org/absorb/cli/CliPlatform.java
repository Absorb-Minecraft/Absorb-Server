package org.absorb.cli;

import org.absorb.platform.AbsorbPlatform;

import java.io.File;

public class CliPlatform implements AbsorbPlatform {
    @Override
    public File configsFolder() {
        return new File("configs");
    }

    @Override
    public File worldsFolder() {
        return new File("worlds");
    }

    @Override
    public void onServerStopped() {

    }
}
