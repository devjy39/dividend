package com.jyeol.dividend.persist;

import com.jyeol.dividend.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

    List<DividendEntity> findAllByCompanyId(Long companyId);

    void deleteAllByCompanyId(Long companyId);

    boolean existsByCompanyIdAndDate(Long companyId, LocalDate date);
}
