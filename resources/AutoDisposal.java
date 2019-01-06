package resources;

import javax.swing.*;
import java.lang.Thread.UncaughtExceptionHandler;

class AutoDisposal implements Runnable, UncaughtExceptionHandler {
    private Thread mainThread;
    private JFrame frame;
    private UncaughtExceptionHandler uncaughtHandler;
    private boolean running;

    public AutoDisposal(Thread mainThread, JFrame frame) {
        this.running = false;
        this.frame = frame;
        this.mainThread = mainThread;
        this.uncaughtHandler = mainThread.getUncaughtExceptionHandler();
        this.mainThread.setUncaughtExceptionHandler(this);
    }

    public void run() {
        running = true;
        while (mainThread.isAlive() && running) {
            try {
                mainThread.join(1000);
            } catch (InterruptedException e) {
            }
        }
        running = false;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.dispose();
            }
        });
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        if (uncaughtHandler != null) {
            uncaughtHandler.uncaughtException(thread, exception);
        }
        running = false;
    }
}
