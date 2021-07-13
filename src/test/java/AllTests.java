import V2.V2AllTests;
import V3.V3AllTests;
import V4.V4AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import v1.V1AllTests;
import v5.V5AllTests;
import v6.V6AllTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTests.class, V2AllTests.class,
        V3AllTests.class, V4AllTests.class, V5AllTests.class, V6AllTests.class})
public class AllTests {
}
