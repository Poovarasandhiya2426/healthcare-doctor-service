
package com.healthcare.doctor.repository;

import com.healthcare.doctor.entity.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository
        extends JpaRepository<Specialization, Long> {

    Optional<Specialization> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Page<Specialization> findByActiveTrue(Pageable pageable);

    Page<Specialization> findByNameContainingIgnoreCaseAndActiveTrue(
            String name, Pageable pageable
    );
}
