@echo off
SET ROOT_LOGGER_LEVEL=ERROR
SET APP_LOGGER_LEVEL=INFO

SET JAR=build\libs\ignite-cassandra-benchmark-1.0-SNAPSHOT.jar
SET SIZE=1000
SET ITEM_SIZE=1000

SET GC_LOG=-verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps

REM FOR %%o in (0, 1, 2, 3, 4, 5, 6, 7) do (
FOR %%o in (3, 4, 5, 7) do (
    FOR %%m in (4096) do (
        java -server %GC_LOG% -Xms512m -Xmx%%mm -Droot-logger-level=%ROOT_LOGGER_LEVEL% -Dapp-logger-level=%APP_LOGGER_LEVEL% -jar %JAR% %%o %SIZE% %ITEM_SIZE%
    )
)
echo on