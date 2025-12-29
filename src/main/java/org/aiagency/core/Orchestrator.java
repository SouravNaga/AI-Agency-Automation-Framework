package org.aiagency.core;

import java.util.List;

public class Orchestrator {

    public void run(List<String> instructions) {
        for (String step : instructions) {
            InstructionParser.parseAndExecute(step);
        }
        DriverManager.quitDriver();
    }
}
