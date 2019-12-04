package agh.iet.devs.error;

public class SimulationError extends RuntimeException {

    private final static String header =
            "<SIMULATION-EXCEPTION>";

    public SimulationError(String message, Phase phase) {
        super(constructMessage(message) + """


                """ + "PHASE: " + phase + "\n" + message);
    }

    private static String constructMessage(String msg) {
        final int append = (msg.length() - header.length()) / 2;

        if (append > 0) {
            return "=".repeat(append) +
                    header +
                    "=".repeat(append);
        } else
            return header;
    }

    public enum Phase {
        PARSE,
        INIT,
        RUNTIME
    }

}
