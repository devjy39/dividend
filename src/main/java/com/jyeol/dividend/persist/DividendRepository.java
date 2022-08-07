package com.jyeol.dividend.persist;

import com.jyeol.dividend.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

}
