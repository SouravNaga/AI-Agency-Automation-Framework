package org.aiagency.agents;

import org.aiagency.core.DriverManager;

public class NavigationAgent implements Agent {

    @Override
    public void execute(String... args) {
        // Example: Navigate to https://google.com
        String url = args[0];
        DriverManager.getDriver().get(url);
    }
}
