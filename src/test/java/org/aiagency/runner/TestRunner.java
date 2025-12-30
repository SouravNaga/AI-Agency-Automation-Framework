package org.aiagency.runner;

import org.aiagency.core.InstructionFileReader;
import org.aiagency.core.Orchestrator;
import org.aiagency.utils.PropertyUtil;
import org.aiagency.utils.ScreenshotUtil;

import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        String stepsFilePath = PropertyUtil.getStepsFile();
        String filePath = "src/test/resources/steps/"+stepsFilePath;

        List<String> steps = InstructionFileReader.readSteps(filePath);
        ScreenshotUtil.initTestFolder();
        Orchestrator orchestrator = new Orchestrator();
        orchestrator.run(steps);
    }
}
