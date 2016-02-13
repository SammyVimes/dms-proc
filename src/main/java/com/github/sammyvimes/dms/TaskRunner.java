package com.github.sammyvimes.dms;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.xml.crypto.Data;
import java.io.*;

/**
 * Created by Semyon on 13.02.2016.
 */
public class TaskRunner extends Thread {

    @NotNull
    private String taskLoc;

    @Nullable
    private InputStreamHandler.InputHandler inputHandler;

    @NotNull
    private DataProvider dataProvider;

    public TaskRunner(@NotNull final String taskLoc, @NotNull final DataProvider dataProvider, @Nullable final InputStreamHandler.InputHandler inputHandler) {
        this.taskLoc = taskLoc;
        this.dataProvider = dataProvider;
        this.inputHandler = inputHandler;
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(taskLoc);

            InputStream inputStream = process.getInputStream();
            OutputStream outputStream = process.getOutputStream();

            InputStreamHandler handler = new InputStreamHandler(inputStream, inputHandler);
            handler.start();

            String data = dataProvider.getData();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(data + "\n");
            writer.flush();
            while (process.isAlive()) {

            }
            handler.setRunning(false);
        } catch (IOException e) {
            System.err.println("Error occured: ");
            e.printStackTrace(new PrintStream(System.err));
        }
    }
}
