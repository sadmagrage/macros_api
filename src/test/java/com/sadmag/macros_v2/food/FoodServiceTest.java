package com.sadmag.macros_v2.food;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @Autowired
    @InjectMocks
    private FoodService foodService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("save() should return the saved food")
    void shouldReturnTheSavedFood() {
        var foodRecord = new FoodRecord("food1", 1, 2, 3, "image1", 2);
        Mockito.when(foodRepository.save(Mockito.any(Food.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = foodService.save(foodRecord);

        Assertions.assertEquals("food1", result.getName());
        Assertions.assertEquals(0.5f, result.getCarb());
        Assertions.assertEquals(1.0f, result.getProt());
        Assertions.assertEquals(1.5f, result.getFat());
        Assertions.assertEquals("image1", result.getImage());
    }

    @Test
    @DisplayName("update() should return the updated food")
    void shouldReturnTheUpdatedFood() {
        var id = UUID.randomUUID();
        var foodRecord = new FoodRecord("food1", 1, 2, 3, "image1", 2);
        var foodFoundById = new Food(id, "oldFood", 4, 5, 6, "image2");
        Mockito.when(foodRepository.findById(id)).thenReturn(Optional.of(foodFoundById));
        Mockito.when(foodRepository.save(Mockito.any(Food.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = foodService.update(foodRecord, id);

        Assertions.assertEquals("food1", result.getName());
        Assertions.assertEquals(0.5f, result.getCarb());
        Assertions.assertEquals(1.0f, result.getProt());
        Assertions.assertEquals(1.5f, result.getFat());
        Assertions.assertEquals("image1", result.getImage());
    }
}