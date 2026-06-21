package com.railflow.controller;

import com.railflow.dto.TrainDTO;
import com.railflow.service.TrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping
    public ResponseEntity<Page<TrainDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(trainService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<TrainDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trainService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TrainDTO> create(@Valid @RequestBody TrainDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<TrainDTO> update(@PathVariable Long id,
            @Valid @RequestBody TrainDTO dto) {
        return ResponseEntity.ok(trainService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
