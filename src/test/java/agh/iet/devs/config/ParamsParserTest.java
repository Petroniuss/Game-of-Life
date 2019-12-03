package agh.iet.devs.config;

import agh.iet.devs.error.SimulationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

class ParamsParserTest {

    @Test
    void testParse_withDefaultCorrectJson() {
        assertDoesNotThrow((ThrowingSupplier<Params>) ParamsParser::parse);
    }

    @Test
    void testParse_whenInvalidPath() {
        final var invalidPath = "src/main/root/invalid.json";

        assertThrows(SimulationError.class, () -> ParamsParser.parse(invalidPath));
    }
}