package com.jyeol.dividend.persist.entity;

import com.jyeol.dividend.model.Dividend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DividendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private LocalDate date;
    private String dividend;

    public static DividendEntity from(Long companyId, LocalDate date, String dividend) {
        return DividendEntity.builder()
                .companyId(companyId)
                .date(date)
                .dividend(dividend)
                .build();
    }

}
