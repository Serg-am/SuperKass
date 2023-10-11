package com.example.demo.service;

import com.example.demo.dto.ModifyResponseDTO;
import com.example.demo.entity.ExampleEntity;
import com.example.demo.repository.ExampleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModifyService {

    private final ExampleRepository exampleRepository;
    private final ObjectMapper objectMapper;

    public ModifyService(ExampleRepository exampleRepository, ObjectMapper objectMapper) {
        this.exampleRepository = exampleRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ResponseEntity<ModifyResponseDTO> modifyValue(Long id, Integer add) {
        try {
            ExampleEntity entity = exampleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Entity not found"));

            String jsonString = entity.getObj();

            // Преобразовать JSON-строку из obj в объект JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Извлечь значение поля "current" как Integer
            int current = jsonNode.get("current").asInt();

            current += add;

            // Создать обновленный объект JSON
            ObjectNode updatedObj = objectMapper.createObjectNode();
            updatedObj.put("current", current);

            // Преобразовать обновленный объект JSON в строку и сохранить в поле obj
            entity.setObj(updatedObj.toString());

            // Сохранить обновленное значение obj в базе данных
            exampleRepository.save(entity);

            return ResponseEntity.ok(new ModifyResponseDTO(current));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        }
    }
}

