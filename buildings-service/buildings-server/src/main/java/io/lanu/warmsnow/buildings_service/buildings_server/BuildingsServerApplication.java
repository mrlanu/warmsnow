package io.lanu.warmsnow.buildings_service.buildings_server;

import io.lanu.warmsnow.buildings_service.buildings_server.entities.BuildingEntity;
import io.lanu.warmsnow.buildings_service.buildings_server.services.BuildingsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class BuildingsServerApplication {

    private BuildingsService buildingsService;

    public BuildingsServerApplication(BuildingsService buildingsService) {
        this.buildingsService = buildingsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BuildingsServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner usage() {
        return args -> {
            if (buildingsService.findAll().size() == 0){
                Map<Integer, Integer> produces = new HashMap<>();
                Map<String, Integer> constCost = new HashMap<>();
                produces.put(5, 7);
                produces.put(15, 14);
                produces.put(1, 34);
                produces.put(4, 56);
                produces.put(8, 84);
                produces.put(24, 169);
                constCost.put("coins", 96);
                BuildingEntity hunter = new BuildingEntity(null,
                        "hunter", 28, constCost, 10000, produces, false);
                BuildingEntity blacksmith = new BuildingEntity(null,
                        "blacksmith", 12, constCost, 30000, produces, false);
                buildingsService.save(hunter);
                buildingsService.save(blacksmith);
            }
        };
    }


}
