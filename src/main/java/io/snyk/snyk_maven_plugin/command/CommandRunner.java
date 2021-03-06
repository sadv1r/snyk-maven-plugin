package io.snyk.snyk_maven_plugin.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandRunner {

    public static int run(CommandLine commandLine, LineLogger infoLogger, LineLogger errorLogger) {
        try {
            Process process = commandLine.start();
            logStream(process.getInputStream(), infoLogger); // process stdout goes to plugin input
            logStream(process.getErrorStream(), errorLogger);
            process.waitFor();
            return process.exitValue();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("command execution failed", e);
        }
    }

    private static void logStream(InputStream stream, LineLogger logger) {
        new BufferedReader(new InputStreamReader(stream)).lines().forEach(logger::log);
    }

    public interface LineLogger {
        void log(String line);
    }

}
