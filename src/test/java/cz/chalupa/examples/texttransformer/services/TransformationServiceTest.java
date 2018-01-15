package cz.chalupa.examples.texttransformer.services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TransformationServiceTest {

    private static final TransformationService SERVICE = new TransformationService();

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("  ", " "),
                Arguments.of("   ", " "),
                Arguments.of("   A   ", " a "),
                Arguments.of("   aeiou   ", " UOIEA "),
                Arguments.of("123   456", "654 321"),
                Arguments.of("Ahoj, jak se máš?", "?šÁm es kaj ,joha"),
                Arguments.of("Je     mi   fajn.", ".Njaf Im ej"),
                Arguments.of("ěščřžýáíóúůťňď.", ".ďňťůúóíáýžřčšě")
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    public void test(String value, String expected) {
        assertThat(SERVICE.transform(value)).isEqualTo(expected);
    }
}
