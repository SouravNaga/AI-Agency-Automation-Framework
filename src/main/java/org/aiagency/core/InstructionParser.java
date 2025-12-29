package org.aiagency.core;

import org.aiagency.agents.Agent;
import org.aiagency.registry.AgentRegistry;

public class InstructionParser {

    public static void parseAndExecute(String instruction) {
        String keyword = instruction.split(" ")[0];
        Agent agent = AgentRegistry.getAgent(keyword);

        if (agent == null) {
            throw new RuntimeException("No agent found for instruction: " + instruction);
        }

        agent.execute(instruction);
    }
}
