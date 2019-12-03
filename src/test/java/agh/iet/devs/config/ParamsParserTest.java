package agh.iet.devs.config;

import agh.iet.devs.data.Params;
import agh.iet.devs.error.SimulationError;
import agh.iet.devs.utils.ParamsParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ParamsParserTest {

    final String testPath = "src/main/resources/test.json".replace("/", File.separator);

    @Test
    void testParse_withDefaultCorrectJson() {
        assertDoesNotThrow((ThrowingSupplier<Params>) ParamsParser::parse);
    }

    @Test
    void testParse_whenInvalidPath() {
        final var invalidPath = "src/main/root/invalid.json";

        assertThrows(SimulationError.class, () -> ParamsParser.parse(invalidPath));
    }

    @Test
    void testParseParameters_whenWidth200andHeight100() {
        final var params = ParamsParser.parse(testPath);

        assertEquals(200, params.width);
        assertEquals(100, params.height);
    }
}