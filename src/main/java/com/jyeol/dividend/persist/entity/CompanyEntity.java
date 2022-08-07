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
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String ticker;

    public static CompanyEntity from(Company company) {
        return CompanyEntity.builder()
                .name(company.getName())
                .ticker(company.getTicker())
                .build();
    }
}
