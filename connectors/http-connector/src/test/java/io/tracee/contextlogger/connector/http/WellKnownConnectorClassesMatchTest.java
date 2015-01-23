package io.tracee.contextlogger.connector.http;

import io.tracee.contextlogger.connector.WellKnownConnectorClassNames;
import io.tracee.contextlogger.connector.http.HttpConnector;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Checks wether the well known class name matches.
 * Created by Tobias Gindler, holisticon AG on 12.03.14.
 */
public class WellKnownConnectorClassesMatchTest {

    @Test
    public void check_if_well_known_class_name_matches () {

        MatcherAssert.assertThat(HttpConnector.class.getCanonicalName(), Matchers.equalTo(WellKnownConnectorClassNames.HTTP_CONNECTOR));

    }

}
