package tools.redstone.redstonetools.utils;

import tools.redstone.redstonetools.telemetry.TelemetryClient;
import tools.redstone.redstonetools.telemetry.dto.TelemetryCommand;
import tools.redstone.redstonetools.telemetry.dto.TelemetryException;
import net.minecraft.util.crash.CrashReport;

import static tools.redstone.redstonetools.RedstoneToolsClient.INJECTOR;

public class TelemetryUtils {
    private TelemetryUtils() {
    }

    public static void sendCommand(String command) {
        INJECTOR.getInstance(TelemetryClient.class).sendCommandAsync(new TelemetryCommand(command));
    }

    public static void sendException(Throwable throwable) {
        // Write the stack trace to a string
        var writer = new java.io.StringWriter();
        var printWriter = new java.io.PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        printWriter.close();

        INJECTOR.getInstance(TelemetryClient.class).sendExceptionAsync(new TelemetryException(writer.toString(), false));
    }

    public static void sendCrash(CrashReport report) {
        INJECTOR.getInstance(TelemetryClient.class).sendExceptionAsync(new TelemetryException(report.asString(), true)).join();
    }
}