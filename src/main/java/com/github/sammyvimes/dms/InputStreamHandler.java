package com.github.sammyvimes.dms;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.*;

/**
 * Created by Semyon on 13.02.2016.
 */
public class InputStreamHandler extends Thread {

    @NotNull
    private InputStream stream;

    @Nullable
    private InputHandler callback;

    private boolean running = true;

    public InputStreamHandler(@NotNull final InputStream stream, @Nullable final InputHandler callback) {
        this.stream = stream;
        this.callback = callback;
    }

    @Override
    public void run() {
        InputStreamReader reader = new InputStreamReader(stream);
        try {
            while (running || stream.available() > 0) {
                String line = "";
                while (!((line = readFully(stream, "UTF-8")).isEmpty())) {
                    if (callback != null) {
                        callback.onInput(line);
                    }
                }
                Thread.sleep(150);
            }
            stream.close();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error occured while reading stdout: ");
            e.printStackTrace(new PrintStream(System.err));
        }
    }

    private String readFully(InputStream inputStream, String encoding)
            throws IOException {
        return new String(readFully(inputStream), encoding);
    }

    private byte[] readFully(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }


    public interface InputHandler {

        void onInput(final String input);

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }
}
