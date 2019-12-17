package agh.iet.devs.error;

/**
 * Class existing solely for no purpose :x
 */
public class SimulationError extends RuntimeException {
    private final static String header =
            "------------<SIMULATION-EXCEPTION>-----------------" + "\n" +
            "--STH-WENT-HORRIBLY-WRONG!-------------------------" + "\n";

    public SimulationError(String message, Phase phase) {
        super(constructMessage(message, phase));
    }

    private static String constructMessage(String msg, Phase phase) {
        return header + "----PHASE:  " + phase + "\n" +
                "----MSG:  " + msg + "\n";
    }

    public enum Phase {
        PARSE,
        INIT,
        RUNTIME
    }

}
