package org.absorb.cli;

import org.absorb.AbsorbServer;

import java.io.IOException;

public class CliLaunch {

    public static void main(String... args) throws IOException {
        System.out.println("Launching Server");
        AbsorbServer.launch(new CliPlatform(), args);
        System.out.println("Launched server");
    }
}
