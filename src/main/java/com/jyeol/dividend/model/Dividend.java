package com.jyeol.dividend.model;

import com.jyeol.dividend.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dividend {
    private LocalDate date;
    private String dividend;

    public static Dividend from(DividendEntity dividendEntity) {
        return Dividend.builder()
                .date(dividendEntity.getDate())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
