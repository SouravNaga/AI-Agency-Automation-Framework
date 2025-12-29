package org.aiagency.registry;

import org.aiagency.agents.*;

import java.util.HashMap;
import java.util.Map;

public class AgentRegistry {

    private static final Map<String, Agent> AGENTS = new HashMap<>();

    static {
        AGENTS.put("navigate", new NavigationAgent());
        AGENTS.put("click", new ClickAgent());
        AGENTS.put("enter", new InputAgent());
        AGENTS.put("wait", new WaitAgent());
        AGENTS.put("verify",new VerificationAgent());
    }

    public static Agent getAgent(String keyword) {
        String key = keyword.toLowerCase();
        return AGENTS.get(key);
    }
}
