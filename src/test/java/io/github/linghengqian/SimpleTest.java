package io.github.linghengqian;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.seata.core.rpc.netty.RmNettyRemotingClient;
import org.apache.seata.core.rpc.netty.TmNettyRemotingClient;
import org.apache.seata.rm.RMClient;
import org.apache.seata.rm.datasource.DataSourceProxy;
import org.apache.seata.tm.TMClient;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@SuppressWarnings("resource")
public class SimpleTest {

    @Test
    void test() {
        assertThat(System.getProperty("service.default.grouplist"), is(nullValue()));
        try (GenericContainer<?> seataContainer = new GenericContainer<>("apache/seata-server:2.2.0")
                .withExposedPorts(7091, 8091)
                .waitingFor(Wait.forHttp("/health").forPort(7091).forStatusCode(200).forResponsePredicate("ok"::equals))
        ) {
            seataContainer.start();
            System.setProperty("service.default.grouplist", "127.0.0.1:" + seataContainer.getMappedPort(8091));
            TMClient.init("test-first", "default_tx_group");
            RMClient.init("test-first", "default_tx_group");
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
            config.setJdbcUrl("jdbc:tc:postgresql:17.1-bookworm://test/demo_ds_0?TC_INITSCRIPT=init.sql");
            try (HikariDataSource hikariDataSource = new HikariDataSource(config)) {
                DataSourceProxy seataDataSource = new DataSourceProxy(hikariDataSource);
                Awaitility.await().atMost(Duration.ofSeconds(15L)).ignoreExceptions().until(() -> {
                    seataDataSource.getConnection().close();
                    return true;
                });
            }
            RmNettyRemotingClient.getInstance().destroy();
            TmNettyRemotingClient.getInstance().destroy();
            System.clearProperty("service.default.grouplist");
        }
        Awaitility.await().timeout(Duration.ofMinutes(5L)).pollDelay(Duration.ofMinutes(2L)).until(() -> true);
    }
}
