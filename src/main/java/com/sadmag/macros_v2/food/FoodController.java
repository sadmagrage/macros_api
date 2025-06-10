package com.sadmag.macros_v2.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(foodService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(foodService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody FoodRecord foodRecord) {
        var food = foodService.save(foodRecord);

        return ResponseEntity.created(null).body(food);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@RequestBody FoodRecord foodRecord, @PathVariable UUID id) {
        var food = foodService.update(foodRecord, id);

        return ResponseEntity.ok().body(food);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        foodService.delete(id);

        return ResponseEntity.noContent().build();
    }
}