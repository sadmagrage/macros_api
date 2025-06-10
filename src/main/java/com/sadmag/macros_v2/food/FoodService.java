package com.sadmag.macros_v2.food;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public Food findById(UUID id) {
        return foodRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Food save(FoodRecord foodRecord) {
        var food = new Food();

        food.setName(foodRecord.name());
        food.setCarb(foodRecord.carb() / foodRecord.quantity());
        food.setProt(foodRecord.prot() / foodRecord.quantity());
        food.setFat(foodRecord.fat() / foodRecord.quantity());
        food.setImage(foodRecord.image());

        return foodRepository.save(food);
    }

    @Transactional
    public Food update(FoodRecord foodRecord, UUID id) {
        var food = foodRepository.findById(id).orElseThrow();

        food.setName(foodRecord.name());
        food.setCarb(foodRecord.carb() / foodRecord.quantity());
        food.setProt(foodRecord.prot() / foodRecord.quantity());
        food.setFat(foodRecord.fat() / foodRecord.quantity());
        food.setImage(foodRecord.image());

        return foodRepository.save(food);
    }

    @Transactional
    public void delete(UUID id) {
        var food = foodRepository.findById(id).orElseThrow();

        foodRepository.delete(food);
    }
}