package com.sadmag.macros_v2.macros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/macro")
public class MacroController {

    @Autowired
    private MacroService macroService;

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestHeader("Authorization") String token) {
        var macros = macroService.calculate(token);

        return ResponseEntity.ok(macros);
    }
}