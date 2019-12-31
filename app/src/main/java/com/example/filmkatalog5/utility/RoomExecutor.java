package com.example.filmkatalog5.utility;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomExecutor {
    private static final int THREAD_COUNT = 3;
    private static volatile RoomExecutor roomExecutor;
    private final Executor diskIO;

    private final Executor mainThread;

    private final Executor networkIO;

    public RoomExecutor(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static RoomExecutor getInstance() {
        if (roomExecutor == null) {
            synchronized (RoomExecutor.class) {
                if (roomExecutor == null) {
                    roomExecutor = new RoomExecutor(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(THREAD_COUNT),
                            new MainThreadExecutor());
                }
            }
        }
        return roomExecutor;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);

        }
    }

}
