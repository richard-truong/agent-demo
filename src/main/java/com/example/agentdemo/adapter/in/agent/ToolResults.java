package com.example.agentdemo.adapter.in.agent;

import dev.langchain4j.exception.ToolErrorVisibleToLlm;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A failed tool call must not kill the agent loop: the model gets readable text instead and
 * can retry or explain. Turning infrastructure errors into text the model can read is the
 * agent adapter's job, not the core's.
 *
 * This is LangChain4j's own mechanism - anything thrown that implements ToolErrorVisibleToLlm
 * is handed to the model as the tool's result, while other exceptions still abort the loop.
 */
final class ToolResults {

    private static final Logger log = LoggerFactory.getLogger(ToolResults.class);

    private ToolResults() {
    }

    static String safe(Supplier<String> call) {
        try {
            return call.get();
        } catch (Exception e) {
            log.warn("tool call failed: {}", e.toString());
            throw ToolErrorVisibleToLlm.from("ERROR: " + e.getMessage(), e);
        }
    }
}
