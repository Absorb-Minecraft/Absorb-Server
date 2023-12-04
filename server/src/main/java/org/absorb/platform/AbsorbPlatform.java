package org.absorb.platform;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface AbsorbPlatform {

    @NotNull File configsFolder();

    @NotNull File worldsFolder();

    void onServerStopped();

}
