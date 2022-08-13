package com.jyeol.dividend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jyeol.dividend.persist.entity.DividendEntity;
import com.jyeol.dividend.utils.CustomLocalDateDeserializer;
import com.jyeol.dividend.utils.CustomLocalDateSerializer;
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

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate date;

    private String dividend;

    public static Dividend from(DividendEntity dividendEntity) {
        return Dividend.builder()
                .date(dividendEntity.getDate())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
