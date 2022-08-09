package com.jyeol.dividend.model;

import com.jyeol.dividend.persist.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    private String ticker;
    private String name;

    public static Company from(CompanyEntity companyEntity) {
        return Company.builder()
                .name(companyEntity.getName())
                .ticker(companyEntity.getTicker())
                .build();
    }

}
