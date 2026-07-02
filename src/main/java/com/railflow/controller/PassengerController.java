package com.railflow.controller;

import com.railflow.dto.PassengerDTO;
import com.railflow.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<Page<PassengerDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(passengerService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<PassengerDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> create(@Valid @RequestBody PassengerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<PassengerDTO> update(@PathVariable Long id,
            @Valid @RequestBody PassengerDTO dto) {
        return ResponseEntity.ok(passengerService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
