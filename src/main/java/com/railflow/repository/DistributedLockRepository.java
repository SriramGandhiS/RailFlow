package com.railflow.repository;

import com.railflow.model.DistributedLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributedLockRepository extends JpaRepository<DistributedLock, Long> {
}
