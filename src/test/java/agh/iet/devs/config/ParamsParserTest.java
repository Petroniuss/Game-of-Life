package agh.iet.devs.config;

import agh.iet.devs.error.SimulationError;
import agh.iet.devs.utils.ParamsParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

class ParamsParserTest {

    final String test = "test.json";

    @Test
    void testParse_withDefaultCorrectJson() {
        assertDoesNotThrow((ThrowingSupplier<Params>) ParamsParser::parse);
    }

    @Test
    void testParse_whenInvalidName() {
        final var invalidName = "invalid.json";

        assertThrows(SimulationError.class, () -> ParamsParser.parse(invalidName));
    }

    @Test
    void testParseParameters_whenWidth200andHeight100() {
        final var params = ParamsParser.parse(test);

        assertEquals(200, params.width);
        assertEquals(100, params.height);
    }
}