package com.jyeol.dividend.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"companyId", "date"}
        )
})
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
