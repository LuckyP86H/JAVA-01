package com.xianyanyang.web;

import com.xianyanyang.domain.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestInventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping(value = "/products/{productId}")
    public void reduceInventory(@PathVariable("productId") String productId) {
        inventoryService.reduceInventory(productId, 1);
    }

}
