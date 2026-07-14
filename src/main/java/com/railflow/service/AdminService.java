package com.railflow.service;

import com.railflow.dto.AdminDTO;
import com.railflow.exception.ResourceNotFoundException;
import com.railflow.model.Admin;
import com.railflow.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public Page<AdminDTO> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public AdminDTO findById(Long id) {
        return toDTO(adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", id)));
    }

    @Transactional
    public AdminDTO create(AdminDTO dto) {
        log.info("Creating admin: {}", dto);
        return toDTO(adminRepository.save(toEntity(dto)));
    }

    @Transactional
    public AdminDTO update(Long id, AdminDTO dto) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", id));
        return toDTO(adminRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!adminRepository.existsById(id))
            throw new ResourceNotFoundException("Admin", id);
        adminRepository.deleteById(id);
    }

    private AdminDTO toDTO(Admin e) { return new AdminDTO(); }
    private Admin toEntity(AdminDTO dto) { return new Admin(); }
}
