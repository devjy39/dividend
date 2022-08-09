package com.jyeol.dividend.service;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.Dividend;
import com.jyeol.dividend.model.ScrapedResult;
import com.jyeol.dividend.persist.CompanyRepository;
import com.jyeol.dividend.persist.DividendRepository;
import com.jyeol.dividend.persist.entity.CompanyEntity;
import com.jyeol.dividend.persist.entity.DividendEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public ScrapedResult getDividendByCompanyName(String companyName) {
        CompanyEntity companyEntity = companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(companyEntity.getId());

        return ScrapedResult.builder()
                .company(Company.from(companyEntity))
                .dividendList(dividendEntities
                            .stream()
                            .map(Dividend::from)
                            .collect(Collectors.toList()))
                .build();
    }

}