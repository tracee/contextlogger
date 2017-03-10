package io.tracee.contextlogger.outputgenerator;

import io.tracee.contextlogger.TraceeContextLoggerConstants;

/**
 * Creates instance that holds the recursive state of the recursive output element tree creation.
 * Used to limit depth of Output Element tree.
 */
public class RecursiveOutputElementTreeBuilderState {

    private final int maxDepth;
    private final int currentDepth;

    private RecursiveOutputElementTreeBuilderState child = null;

    public RecursiveOutputElementTreeBuilderState() {
        this(TraceeContextLoggerConstants.MAX_DEPTH);
    }

    public RecursiveOutputElementTreeBuilderState(int maxDepth) {
        this.maxDepth = maxDepth;
        this.currentDepth = 1;
    }

    public RecursiveOutputElementTreeBuilderState(int maxDepth, int currentDepth) {
        this.maxDepth = maxDepth;
        this.currentDepth = currentDepth;
    }

    public boolean maxDepthReached() {
        return this.currentDepth > this.maxDepth;
    }

    /**
     * Gets the childs levels state instance. (Immutable - is shared for all children)
     *
     * @return
     */
    public RecursiveOutputElementTreeBuilderState next() {
        if (child == null) {
            child = new RecursiveOutputElementTreeBuilderState(maxDepth, currentDepth + 1);
        }

        return child;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }
}
