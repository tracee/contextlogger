package io.tracee.contextlogger.output.internal;

/**
 * Creates instance that holds the recursive state of the recusive output element tree creation.
 * Used to limit depth of Output Element tree.
 */
public class RecursiveOutputElementTeeBuilderState {

    private final int maxDepth;
    private final int currentDepth;

    private RecursiveOutputElementTeeBuilderState child = null;

    public RecursiveOutputElementTeeBuilderState(int maxDepth) {
        this.maxDepth = maxDepth;
        this.currentDepth = 1;
    }

    public RecursiveOutputElementTeeBuilderState(int maxDepth, int currentDepth) {
        this.maxDepth = maxDepth;
        this.currentDepth = currentDepth;
    }

    public boolean maxDepthNotReached() {
        return this.currentDepth < this.maxDepth;
    }

    /**
     * Gets the childs levels state instance. (Immutable - is shared for all children)
     *
     * @return
     */
    public RecursiveOutputElementTeeBuilderState next() {
        if (child == null) {
            child = new RecursiveOutputElementTeeBuilderState(maxDepth, currentDepth + 1);
        }

        return child;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }
}
