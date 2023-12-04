package org.absorb.android.launcher;

import androidx.lifecycle.AndroidViewModel;

import org.absorb.AbsorbServer;
import org.absorb.android.MainActivity;
import org.absorb.platform.AbsorbPlatform;

import java.io.File;
import java.io.IOException;

public class AbsorbAndroidLauncher implements AbsorbPlatform {
    @Override
    public File dataFolder() {
        File folder = MainActivity.MEDIA;
        if(!folder.canRead()){
            throw new RuntimeException("Do not have read permissions for '" + folder.getAbsolutePath() + "'");
        }
        if(!folder.canWrite()){
            throw new RuntimeException("Do not have write permissions for '" + folder.getAbsolutePath() + "'");
        }
        return folder;
    }

    @Override
    public void onServerStopped() {
        System.out.println("Stopped");
    }
}
