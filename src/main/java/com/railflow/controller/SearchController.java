package com.railflow.controller;

import com.railflow.dto.SearchDTO;
import com.railflow.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/searchs")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Page<SearchDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(searchService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<SearchDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(searchService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SearchDTO> create(@Valid @RequestBody SearchDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(searchService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<SearchDTO> update(@PathVariable Long id,
            @Valid @RequestBody SearchDTO dto) {
        return ResponseEntity.ok(searchService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        searchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
