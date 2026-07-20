package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryDto;
import com.example.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> create(@RequestBody InventoryDto inventoryDto) {
        InventoryDto createdInventory = inventoryService.create(inventoryDto);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAll() {
        List<InventoryDto> inventories = inventoryService.getAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getById(@PathVariable String id) {
        InventoryDto inventory = inventoryService.getById(id);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> update(@PathVariable String id, @RequestBody InventoryDto inventoryDto) {
        InventoryDto updatedInventory = inventoryService.update(id, inventoryDto);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        inventoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
