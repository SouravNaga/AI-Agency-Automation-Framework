package org.aiagency.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class InstructionFileReader {

    private InstructionFileReader() {
        // utility class
    }

    public static List<String> readSteps(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath))
                    .stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .filter(line -> !line.startsWith("#"))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to read instruction file: " + filePath, e);
        }
    }
}
