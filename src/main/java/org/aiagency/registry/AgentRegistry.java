package org.aiagency.registry;

import org.aiagency.agents.*;

import java.util.HashMap;
import java.util.Map;

public class AgentRegistry {

    private static final Map<String, Agent> AGENTS = new HashMap<>();

    static {
        AGENTS.put("Navigate", new NavigationAgent());
        AGENTS.put("Click", new ClickAgent());
        AGENTS.put("Enter", new InputAgent());
        AGENTS.put("Wait", new WaitAgent());
        AGENTS.put("Verify",new VerificationAgent());
    }

    public static Agent getAgent(String keyword) {
        return AGENTS.get(keyword);
    }
}
