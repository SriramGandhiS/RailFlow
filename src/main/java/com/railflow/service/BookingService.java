package com.railflow.service;

import com.railflow.dto.BookingDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Booking;
import com.railflow.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public Page<BookingDTO> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public BookingDTO findById(Long id) {
        return toDTO(bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id)));
    }

    @Transactional
    public BookingDTO create(BookingDTO dto) {
        log.info("Creating booking: {}", dto);
        return toDTO(bookingRepository.save(toEntity(dto)));
    }

    @Transactional
    public BookingDTO update(Long id, BookingDTO dto) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
        return toDTO(bookingRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!bookingRepository.existsById(id))
            throw new ResourceNotFoundException("Booking", id);
        bookingRepository.deleteById(id);
    }

    private BookingDTO toDTO(Booking e) { return new BookingDTO(); }
    private Booking toEntity(BookingDTO dto) { return new Booking(); }
}
