package io.tracee.contextlogger.output.internal.testclasses;

/**
 * Test class for unit tests.
 */
public class SuperTestClassA {

    public static final String SUPER_STRING_FIELD = "A";
    public static final Long SUPER_LONG_FIELD = 5L;

    private String superStringField = SUPER_STRING_FIELD;
    private Long superLongField = SUPER_LONG_FIELD;

    public SuperTestClassA() {

    }

    public String getSuperStringField() {
        return superStringField;
    }

    public void setSuperStringField(final String superStringField) {
        this.superStringField = superStringField;
    }

    public Long getSuperLongField() {
        return superLongField;
    }

    public void setSuperLongField(final Long superLongField) {
        this.superLongField = superLongField;
    }
}
