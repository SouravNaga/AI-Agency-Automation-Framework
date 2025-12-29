package org.aiagency.agents;

import org.aiagency.core.DriverManager;

public class NavigationAgent implements Agent {

    @Override
    public void execute(String instruction) {
        // Example: Navigate to https://google.com
        String url = instruction.replace("Navigate to", "").trim();
        DriverManager.getDriver().get(url);
    }
}
