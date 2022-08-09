package com.jyeol.dividend.persist;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.persist.entity.CompanyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);

    Optional<CompanyEntity> findByName(String name);

    List<CompanyEntity> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

}
