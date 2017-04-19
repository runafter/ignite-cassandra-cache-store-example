# ignite-cassandra-cache-store-example

- cache configuration
```
<bean class="org.apache.ignite.configuration.CacheConfiguration">
    <property name="name" value="cacheCassandraReadWriteThrough"/>
    <property name="readThrough" value="true"/>
    <property name="writeThrough" value="true"/>
    <property name="writeBehindEnabled" value="true"/>
    <property name="writeBehindFlushFrequency" value="5000"/>

    <property name="cacheStoreFactory">
        <bean class="org.apache.ignite.cache.store.cassandra.CassandraCacheStoreFactory">
            <property name="dataSourceBean" value="cassandraAdminDataSource"/>
            <property name="persistenceSettingsBean" value="cache_pojo_persistence_settings"/>
        </bean>
    </property>

    <property name="queryEntities">
        <list>
            <bean class="org.apache.ignite.cache.QueryEntity">
                <!-- Setting indexed type's key class -->
                <property name="keyType" value="java.lang.String"/>

                <!-- Setting indexed type's value class -->
                <property name="valueType"
                          value="org.runafter.ignite.example.CacheValue"/>

                <!-- Defining fields that will be either indexed or queryable.
                Indexed fields are added to 'indexes' list below.-->
                <property name="fields">
                    <map>
                        <entry key="firstKey" value="java.lang.Integer"/>
                    </map>
                </property>

                <!-- Defining indexed fields.-->
                <property name="indexes">
                    <list>
                        <!-- Single field (aka. column) index -->
                        <bean class="org.apache.ignite.cache.QueryIndex">
                            <constructor-arg value="firstKey"/>
                        </bean>
                    </list>
                </property>
            </bean>
        </list>
    </property>
</bean>
```

- pojo persistence setting
```
<persistence keyspace="test1" table="pojo_test1">
    <keyPersistence class="java.lang.String" strategy="PRIMITIVE" />
    <valuePersistence class="org.runafter.ignite.example.CacheValue"
                      strategy="POJO"
                      serializer="org.apache.ignite.cache.store.cassandra.serializer.KryoSerializer">
        <field name="firstKey" column="first_name" index="true"/>
        <field name="secondKey" column="last_name" />
        <field name="value" />
    </valuePersistence>
</persistence>
```

- blob persistence setting
```
<persistence keyspace="test1" table="blob_test1">
    <keyPersistence class="java.lang.String" strategy="PRIMITIVE" />
    <valuePersistence class="org.runafter.ignite.example.CacheValue"
                      strategy="BLOB"
                      serializer="org.apache.ignite.cache.store.cassandra.serializer.KryoSerializer">
    </valuePersistence>
</persistence>
```

