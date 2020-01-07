package com.pchudzik.blog.example.toggablecache;

public class Sleeper {
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
