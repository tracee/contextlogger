package io.tracee.contextlogger.integrationtest;

import io.tracee.contextlogger.TraceeToStringBuilder;
import io.tracee.contextlogger.contextprovider.core.CoreImplicitContextProviders;
import io.tracee.contextlogger.contextprovider.core.utility.NameValuePair;
import io.tracee.contextlogger.outputgenerator.writer.BasicOutputWriterConfiguration;
import org.junit.Test;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class MdcContextLoggerTest {

    private MdcContextLoggerTest cycle1 = this;
    private MdcContextLoggerTest cycle2 = this;
    private String field = "FIELD";
    private List<String> list = new ArrayList<String>();

    public List<String> getList() {
        return list;
    }

    public String getField() {
        return field;
    }

    {
        Collections.addAll(list, "ABC", "DEF");

    }

    public MdcContextLoggerTest getCycle1() {
        return cycle1;
    }

    public MdcContextLoggerTest getCycle2() {
        return cycle2;
    }

    @Test
    public void test_should_use_mdc_logger_correctly() {

        MDC.put("KEY", "VALUE");

        NameValuePair<String> nvPair = new NameValuePair<String>("NVPAIR", "VALUE");

        TraceeToStringBuilder.createDefault().toString("ABC", "DEF");
        // TraceeToStringBuilder.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INTENDED).enforceProfile(Profile.FULL).disableTypes(TypeX.class).apply().toString("ABC",
        // "DEF");

        System.out.println(TraceeToStringBuilder.create().enforceOrder().apply()
                .toString("ABC", new NullPointerException(), CoreImplicitContextProviders.TRACEE, this, CoreImplicitContextProviders.COMMON));

        System.out.println(TraceeToStringBuilder.create().enforceOutputWriterConfiguration(BasicOutputWriterConfiguration.JSON_INLINE)
                .disableTypes(MdcContextLoggerTest.class).apply().wrap("ABC", this, CoreImplicitContextProviders.COMMON, CoreImplicitContextProviders.TRACEE));

    }
}
