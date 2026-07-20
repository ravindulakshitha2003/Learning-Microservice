package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryDto;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryDto create(InventoryDto inventoryDto) {



        Inventory inventory = new Inventory();
        inventory.setId(inventoryDto.getId());
        inventory.setProductName(inventoryDto.getProductName());
        inventory.setAvailableQuantity(inventoryDto.getAvailableQuantity());
        inventory.setWarehouse(inventoryDto.getWarehouse());

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToDto(savedInventory);
    }




    public List<InventoryDto> getAll() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public InventoryDto getById(String id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        return mapToDto(inventory);
    }

    public InventoryDto update(String id, InventoryDto inventoryDto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));

        inventory.setProductName(inventoryDto.getProductName());
        inventory.setAvailableQuantity(inventoryDto.getAvailableQuantity());
        inventory.setWarehouse(inventoryDto.getWarehouse());

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return mapToDto(updatedInventory);
    }

    public void delete(String id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        inventoryRepository.deleteById(inventory.getId());
    }

    private InventoryDto mapToDto(Inventory inventory) {
        InventoryDto dto = new InventoryDto();
        dto.setId(inventory.getId());
        dto.setProductName(inventory.getProductName());
        dto.setAvailableQuantity(inventory.getAvailableQuantity());
        dto.setWarehouse(inventory.getWarehouse());
        return dto;
    }

}
