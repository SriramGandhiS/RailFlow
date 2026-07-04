package com.railflow.controller;

import com.railflow.dto.BookingDTO;
import com.railflow.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<Page<BookingDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(bookingService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookingDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(@Valid @RequestBody BookingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable Long id,
            @Valid @RequestBody BookingDTO dto) {
        return ResponseEntity.ok(bookingService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
