package com.example.demo.controller;

import com.example.demo.dto.ModifyRequestDTO;
import com.example.demo.dto.ModifyResponseDTO;
import com.example.demo.service.ModifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modify")
@Slf4j
public class ModifyController {

    private final ModifyService modifyService;

    public ModifyController(ModifyService modifyService) {
        this.modifyService = modifyService;
    }

    @PostMapping
    public ResponseEntity<ModifyResponseDTO> modifyValue(@RequestBody ModifyRequestDTO request) {
        try {
            Long id = request.getId();
            Integer add = request.getAdd();

            ResponseEntity<ModifyResponseDTO> response = modifyService.modifyValue(id, add);


            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}




