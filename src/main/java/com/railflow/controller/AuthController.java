package com.railflow.controller;

import com.railflow.dto.AuthDTO;
import com.railflow.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Page<AuthDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(authService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AuthDTO> create(@Valid @RequestBody AuthDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthDTO> update(@PathVariable Long id,
            @Valid @RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
