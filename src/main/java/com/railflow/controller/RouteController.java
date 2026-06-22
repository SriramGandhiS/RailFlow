package com.railflow.controller;

import com.railflow.dto.RouteDTO;
import com.railflow.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<Page<RouteDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(routeService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<RouteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RouteDTO> create(@Valid @RequestBody RouteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<RouteDTO> update(@PathVariable Long id,
            @Valid @RequestBody RouteDTO dto) {
        return ResponseEntity.ok(routeService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
