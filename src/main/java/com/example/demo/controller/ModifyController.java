package com.example.demo.controller;

import com.example.demo.entity.ExampleEntity;
import com.example.demo.repository.ExampleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/modify")
public class ModifyController {

    private final ExampleRepository exampleRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ModifyController(ExampleRepository exampleRepository, ObjectMapper objectMapper) {
        this.exampleRepository = exampleRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ModifyResponse> modifyValue(@RequestBody ModifyRequest request) {
        try {
            Long id = request.getId();
            Integer add = request.getAdd();

            ExampleEntity entity = exampleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Entity not found"));

            String jsonString = entity.getObj();

            // Преобразовать JSON-строку из obj в объект JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Извлечь значение поля "current" как Integer
            System.out.println(jsonNode.get("current").asInt());
            int current = jsonNode.get("current").asInt();

            current += add;

            // Создать обновленный объект JSON
            ObjectNode updatedObj = objectMapper.createObjectNode();
            updatedObj.put("current", current);

            // Преобразовать обновленный объект JSON в строку и сохранить в поле obj
            entity.setObj(updatedObj.toString());

            // Сохранить обновленное значение obj в базе данных
            exampleRepository.save(entity);

            return ResponseEntity.ok(new ModifyResponse(current));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }
    }
}



