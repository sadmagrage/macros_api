package com.sadmag.macros_v2.macros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacroDto {
    private float carb;
    private float prot;
    private float fat;
    private float totalSpent;
}