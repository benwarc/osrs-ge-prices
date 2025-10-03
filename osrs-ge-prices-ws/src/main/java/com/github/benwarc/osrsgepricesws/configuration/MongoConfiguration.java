package com.github.benwarc.osrsgepricesws.configuration;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClientFactoryBean mongoClient(@Value("${spring.data.mongodb.host}") String host,
                                              @Value("${spring.data.mongodb.port}") int port) {

        var mongoClient = new MongoClientFactoryBean();
        mongoClient.setHost(host);
        mongoClient.setPort(port);
        return mongoClient;
    }

    @Bean
    public MongoDatabaseFactory mongoDatabase(MongoClient mongoClient,
                                              @Value("${spring.data.mongodb.database}") String database) {

        return new SimpleMongoClientDatabaseFactory(mongoClient, database);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabase) {
        return new MongoTemplate(mongoDatabase);
    }
}
