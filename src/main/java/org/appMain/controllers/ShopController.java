package org.appMain.controllers;

import org.appMain.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("admin/filter={filt}")
    public ResponseEntity<Void> filterExpiredProducts(@PathVariable(name = "filt") boolean filter) {
        if (filter)
            shopService.filterExpiredProducts();
        return ResponseEntity.ok().build();
    }

}
