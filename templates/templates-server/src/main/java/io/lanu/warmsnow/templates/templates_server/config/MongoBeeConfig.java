package io.lanu.warmsnow.templates.templates_server.config;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@DependsOn("mongoTemplate")
public class MongoBeeConfig {

    private static final String MONGODB_URL_FORMAT = "mongodb://%s:%s@%s:%d/%s";
    private static final String MONGODB_CHANGELOGS_PACKAGE = "io.lanu.warmsnow.templates.templates_server.config.changelogs";

    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.database}")
    private String database;

    public MongoBeeConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee(String.format(MONGODB_URL_FORMAT, username, password, host, port, database));
        runner.setMongoTemplate(mongoTemplate);
        runner.setDbName(database);
        runner.setChangeLogsScanPackage(MONGODB_CHANGELOGS_PACKAGE);
        return runner;
    }

}
