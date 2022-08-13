package com.jyeol.dividend.persist.entity;

import com.jyeol.dividend.model.Company;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Entity
@Table(indexes = @Index(name = "idx_ticker", columnList = "ticker", unique = true))
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String ticker;

    public static CompanyEntity from(Company company) {
        return CompanyEntity.builder()
                .name(company.getName())
                .ticker(company.getTicker())
                .build();
    }
}
