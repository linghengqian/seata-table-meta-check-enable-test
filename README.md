# seata-client-re-create-test

- For https://github.com/apache/shardingsphere/pull/33872 .

- Verified unit test under Ubuntu 22.04.4 LTS with `SDKMAN!` and `Docker CE`.
  **The unit test deliberately takes 2 minutes to complete
  through `Awaitility.await().timeout(Duration.ofMinutes(5L)).pollDelay(Duration.ofMinutes(2L)).until(() -> true);`.**

```shell
sdk install java 23-open

git clone git@github.com:linghengqian/seata-client-re-create-test.git
cd ./seata-client-re-create-test/
sdk use java 23-open
./mvnw -T 1C clean test
```

- The log is as follows.

```shell
$ ./mvnw -T 1C clean test
[INFO] Scanning for projects...
[INFO] 
[INFO] Using the MultiThreadedBuilder implementation with a thread count of 16
[INFO] 
[INFO] -----< io.github.linghengqian:seata-table-meta-check-enable-test >------
[INFO] Building seata-table-meta-check-enable-test 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.2.0:clean (default-clean) @ seata-table-meta-check-enable-test ---
[INFO] Deleting /home/linghengqian/TwinklingLiftWorks/git/public/seata-table-meta-check-enable-test/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ seata-table-meta-check-enable-test ---
[INFO] skip non existing resourceDirectory /home/linghengqian/TwinklingLiftWorks/git/public/seata-table-meta-check-enable-test/src/main/resources
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ seata-table-meta-check-enable-test ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ seata-table-meta-check-enable-test ---
[INFO] Copying 4 resources from src/test/resources to target/test-classes
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ seata-table-meta-check-enable-test ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 1 source file with javac [debug target 23] to target/test-classes
[INFO] 
[INFO] --- surefire:3.2.5:test (default-test) @ seata-table-meta-check-enable-test ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running io.github.linghengqian.SimpleTest
[ERROR] 2024-12-02 23:08:55.234 [main] o.a.s.config.ConfigurationFactory - failed to load non-spring configuration :not found service provider for : org.apache.seata.config.ConfigurationProvider
org.apache.seata.common.loader.EnhancedServiceNotFoundException: not found service provider for : org.apache.seata.config.ConfigurationProvider
[ERROR] 2024-12-02 23:09:56.314 [tableMetaRefresh_1_1] o.a.s.r.d.s.s.TableMetaCacheFactory - table refresh error:HikariDataSource HikariDataSource (HikariPool-1) has been closed.
java.sql.SQLException: HikariDataSource HikariDataSource (HikariPool-1) has been closed.
        at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:95)
        at org.apache.seata.rm.datasource.DataSourceProxy.getConnection(DataSourceProxy.java:212)
        at org.apache.seata.rm.datasource.sql.struct.TableMetaCacheFactory$TableMetaRefreshHolder.lambda$new$0(TableMetaCacheFactory.java:129)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:1575)
[ERROR] 2024-12-02 23:10:56.323 [tableMetaRefresh_1_1] o.a.s.r.d.s.s.TableMetaCacheFactory - table refresh error:HikariDataSource HikariDataSource (HikariPool-1) has been closed.
java.sql.SQLException: HikariDataSource HikariDataSource (HikariPool-1) has been closed.
        at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:95)
        at org.apache.seata.rm.datasource.DataSourceProxy.getConnection(DataSourceProxy.java:212)
        at org.apache.seata.rm.datasource.sql.struct.TableMetaCacheFactory$TableMetaRefreshHolder.lambda$new$0(TableMetaCacheFactory.java:129)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:1575)
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 129.5 s -- in io.github.linghengqian.SimpleTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:11 min (Wall Clock)
[INFO] Finished at: 2024-12-02T23:10:59+08:00
[INFO] ------------------------------------------------------------------------
```
