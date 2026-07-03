package com.railflow.service;

import com.railflow.dto.TicketDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Ticket;
import com.railflow.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional(readOnly = true)
    public Page<TicketDTO> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public TicketDTO findById(Long id) {
        return toDTO(ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", id)));
    }

    @Transactional
    public TicketDTO create(TicketDTO dto) {
        log.info("Creating ticket: {}", dto);
        return toDTO(ticketRepository.save(toEntity(dto)));
    }

    @Transactional
    public TicketDTO update(Long id, TicketDTO dto) {
        Ticket existing = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
        return toDTO(ticketRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!ticketRepository.existsById(id))
            throw new ResourceNotFoundException("Ticket", id);
        ticketRepository.deleteById(id);
    }

    private TicketDTO toDTO(Ticket e) { return new TicketDTO(); }
    private Ticket toEntity(TicketDTO dto) { return new Ticket(); }
}
