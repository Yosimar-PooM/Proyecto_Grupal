package Modelo;

public class MemoryLogger {
    private static MemoryLogger instance = new MemoryLogger();
    private double maxMemory = 0.0D;
    public static MemoryLogger getInstance() {
        return instance;
    }

    public double getMaxMemory() {
        return this.maxMemory;
    }

    public void reset() {
        this.maxMemory = 0.0D;
    }

    public void checkMemory() {
        double currentMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                / 1024.0D / 1024.0D;
        if (currentMemory > this.maxMemory) {
            this.maxMemory = currentMemory;
        }
    }
}
