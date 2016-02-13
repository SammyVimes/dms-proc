package com.github.sammyvimes.dms;

import java.io.*;

/**
 * Created by Semyon on 13.02.2016.
 */
public class DMSMain {

    private static final String PATH = "E:\\development\\qtdev\\build-dme-test-Desktop_Qt_5_5_1_MinGW_32bit-Debug\\debug\\dme-test.exe";

    public static void main(String[] args) {
        TaskRunner taskRunner = new TaskRunner(PATH, () -> "Say hello to my little", System.out::println);
        taskRunner.start();
    }

}
