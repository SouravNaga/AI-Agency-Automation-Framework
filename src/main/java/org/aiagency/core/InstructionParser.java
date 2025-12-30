package org.aiagency.core;

import org.aiagency.agents.Agent;
import org.aiagency.registry.AgentRegistry;
import org.aiagency.utils.PageXPathStore;
import java.util.ArrayList;
import java.util.List;
public class InstructionParser {

    public static void parseAndExecute(String instruction) {
//        Verify the heading text swag_labs_heading is Swag Labs
        String[] parts = instruction.split(" ");
        String keyword = parts[0].toLowerCase();

        Agent agent = AgentRegistry.getAgent(keyword);
        if (agent == null) {
            throw new RuntimeException("No agent found for: " + keyword);
        }

        switch (keyword) {

            case "navigate":
                agent.execute(parts[1]);
                break;
//            Click bike_light in login page
            case "click":
            case "wait": {
                String key  = parts[1];
                String page = parts[3];

                String xpath = PageXPathStore.get(page, key);
                agent.execute(xpath,key,page);
                break;
            }

            case "enter": {
                String key   = parts[1];
                String value = parts[2];
                String page  = parts[4];

                String xpath = PageXPathStore.get(page, key);
                agent.execute(value, xpath);
                break;
            }

            case "verify": {
                // Example:

//                Verify within login page the heading_text swag_labs_heading is Swag Labs
//                Verify within login page the current_url is https://www.saucedemo.com/inventory.html
//                Verify within login page the element swag_labs_heading is visible
                List<String> new_parts = parseStatement(instruction);
                String key  = new_parts.get(1);
                String page = parts[2];
                String xpath = (key == null || key.equalsIgnoreCase("N/A"))
                        ? ""
                        : PageXPathStore.get(page, key);

                agent.execute(new_parts.get(0), xpath, new_parts.get(2)); // keep as-is for now
                break;
            }

            default:
                throw new RuntimeException("Unsupported instruction: " + instruction);
        }
    }
    public static List<String> parseStatement(String statement) {
        String[] parts = new String[8]; // parts[0] and parts[1] unused for consistency
        String[] tokens = statement.split(" ");
        List<String> final_list = new ArrayList<>();

        parts[5] = tokens[5]; // heading_text / current_url / element

        // Find index of "is"
        int isIndex = statement.indexOf(" is ");

        // Dynamic assignment
        switch (parts[5]) {
            case "heading_text":
                parts[6] = tokens[6]; // element name
                parts[7] = statement.substring(isIndex + 4); // everything after " is "
                break;

            case "current_url":
                parts[6] = "N/A";
                parts[7] = statement.substring(isIndex + 4);
                break;

            case "element":
                parts[6] = tokens[6]; // element name
                parts[7] = statement.substring(isIndex + 4); // visible or other value
                break;
        }
        final_list.add(parts[5]);
        final_list.add(parts[6]);
        final_list.add(parts[7]);
        // Print mapping
        System.out.println("Statement: " + statement);
        System.out.println("Verify = " + parts[5]);
        System.out.println("element = " + parts[6]);
        System.out.println("expected value = " + parts[7]);
        System.out.println("-------------------------");
        return final_list;
    }
}
