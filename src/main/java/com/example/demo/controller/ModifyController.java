package com.example.demo.controller;

import com.example.demo.service.ModifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modify")
public class ModifyController {

    private final ModifyService modifyService;

    public ModifyController(ModifyService modifyService) {
        this.modifyService = modifyService;
    }

    @PostMapping
    public ResponseEntity<ModifyResponse> modifyValue(@RequestBody ModifyRequest request) {
        try {
            Long id = request.getId();
            Integer add = request.getAdd();

            ResponseEntity<ModifyResponse> response = modifyService.modifyValue(id, add);

            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}




