package com.example.demo.controller;

import lombok.Data;

@Data
public class ModifyResponse {
    private Integer current;

    public ModifyResponse(Integer current) {
        this.current = current;
    }
}
