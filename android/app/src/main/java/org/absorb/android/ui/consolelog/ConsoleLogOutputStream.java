package org.absorb.android.ui.consolelog;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleLogOutputStream extends ByteArrayOutputStream {

    private final @NotNull TextInputEditText log;

    public ConsoleLogOutputStream(@NotNull TextInputEditText log){
        this.log = log;
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        String line = new String(this.buf);
        this.log.append(line + "\n");
        this.buf = new byte[0];
    }
}
