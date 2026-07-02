package com.railflow.service;

import com.railflow.dto.PassengerDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Passenger;
import com.railflow.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Transactional(readOnly = true)
    public Page<PassengerDTO> findAll(Pageable pageable) {
        return passengerRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public PassengerDTO findById(Long id) {
        return toDTO(passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", id)));
    }

    @Transactional
    public PassengerDTO create(PassengerDTO dto) {
        log.info("Creating passenger: {}", dto);
        return toDTO(passengerRepository.save(toEntity(dto)));
    }

    @Transactional
    public PassengerDTO update(Long id, PassengerDTO dto) {
        Passenger existing = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", id));
        return toDTO(passengerRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!passengerRepository.existsById(id))
            throw new ResourceNotFoundException("Passenger", id);
        passengerRepository.deleteById(id);
    }

    private PassengerDTO toDTO(Passenger e) { return new PassengerDTO(); }
    private Passenger toEntity(PassengerDTO dto) { return new Passenger(); }
}
