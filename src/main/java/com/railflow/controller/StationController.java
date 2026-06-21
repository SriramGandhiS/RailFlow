package com.railflow.controller;

import com.railflow.dto.StationDTO;
import com.railflow.service.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<Page<StationDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(stationService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<StationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(stationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<StationDTO> create(@Valid @RequestBody StationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stationService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<StationDTO> update(@PathVariable Long id,
            @Valid @RequestBody StationDTO dto) {
        return ResponseEntity.ok(stationService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
