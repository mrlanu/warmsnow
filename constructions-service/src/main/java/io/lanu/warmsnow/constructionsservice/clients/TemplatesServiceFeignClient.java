package io.lanu.warmsnow.constructionsservice.clients;


import io.lanu.warmsnow.common_models.FieldType;
import io.lanu.warmsnow.templates.templates_client.dto.FieldDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "templates-server")
public interface TemplatesServiceFeignClient {

    @GetMapping(value = "/templates/fields/{fieldLevel}/{fieldType}")
    FieldDto getFieldByLevelAndType(@PathVariable("fieldLevel") int fieldLevel,
                                    @PathVariable("fieldType") FieldType fieldType);
}
