package com.railflow.controller;

import com.railflow.dto.RouteStopDTO;
import com.railflow.service.RouteStopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routeStops")
@RequiredArgsConstructor
public class RouteStopController {

    private final RouteStopService routeStopService;

    @GetMapping
    public ResponseEntity<Page<RouteStopDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(routeStopService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<RouteStopDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(routeStopService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RouteStopDTO> create(@Valid @RequestBody RouteStopDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(routeStopService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<RouteStopDTO> update(@PathVariable Long id,
            @Valid @RequestBody RouteStopDTO dto) {
        return ResponseEntity.ok(routeStopService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeStopService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
