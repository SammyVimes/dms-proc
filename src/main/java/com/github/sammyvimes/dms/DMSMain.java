package com.github.sammyvimes.dms;

import java.io.*;

/**
 * Created by Semyon on 13.02.2016.
 */
public class DMSMain {

    private static final String PATH = "E:\\development\\qtdev\\build-dme-test-Desktop_Qt_5_5_1_MinGW_32bit-Debug\\debug\\dme-test.exe";

    public static void main(String[] args) {
        final Object monitor = new Object();
        final ResultHandler resultHandler = new ResultHandler(monitor);
        TaskRunner taskRunner = new TaskRunner(PATH, () -> "Say hello to my little", resultHandler);
        taskRunner.start();
        try {
            while (!resultHandler.isFinished()) {
                synchronized (monitor) {
                    if (!resultHandler.isFinished()) {
                        monitor.wait();
                    }
                }
            }
            String result = resultHandler.getResult();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ResultHandler implements InputStreamHandler.InputHandler {

        private String result;
        private boolean finished;
        private final Object monitor;

        public ResultHandler(final Object monitor) {
            this.monitor = monitor;
        }

        @Override
        public void onInput(final String input) {
            this.result = input;
            synchronized (monitor) {
                this.finished = true;
                monitor.notifyAll();
            }
        }

        public String getResult() {
            return result;
        }

        public boolean isFinished() {
            return finished;
        }

    }

}
