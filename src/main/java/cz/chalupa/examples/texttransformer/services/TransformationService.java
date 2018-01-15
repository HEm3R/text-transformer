package cz.chalupa.examples.texttransformer.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

@Service
public class TransformationService {

    private static final Pattern SPACES_PATTERN = Pattern.compile(" +");
    private static final Set<Character> TRANSFORMED_CHARS = new HashSet<>(2);

    static {
        TRANSFORMED_CHARS.add('a');
        TRANSFORMED_CHARS.add('e');
        TRANSFORMED_CHARS.add('i');
        TRANSFORMED_CHARS.add('o');
        TRANSFORMED_CHARS.add('u');
    }

    public String transform(@NonNull String value) {
        return replaceMultipleSpaces(applyUpperIndexes(reverse(value), findUpperIndexes(value)));
    }

    private String reverse(String value) {
        return new StringBuilder(value).reverse().toString();
    }

    private Set<Integer> findUpperIndexes(String value) {
        char[] chars = value.toCharArray();
        return IntStream.range(0, chars.length)
                .flatMap(i ->  TRANSFORMED_CHARS.contains(chars[i]) ? IntStream.of(i) : IntStream.empty())
                .boxed().collect(Collectors.toSet());
    }

    private String applyUpperIndexes(String value, Set<Integer> indexes) {
        char[] chars = value.toCharArray();
        IntStream.range(0, chars.length).forEach(i -> chars[i] = indexes.contains(i) ? toUpperCase(chars[i]) : toLowerCase(chars[i]));
        return new String(chars);
    }

    private String replaceMultipleSpaces(String value) {
        return SPACES_PATTERN.matcher(value).replaceAll(" ");
    }
}
