package com.jyeol.dividend.service;

import com.jyeol.dividend.exception.impl.CompanyException;
import com.jyeol.dividend.exception.type.CompanyError;
import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.Dividend;
import com.jyeol.dividend.model.ScrapedResult;
import com.jyeol.dividend.model.constants.CacheKey;
import com.jyeol.dividend.persist.CompanyRepository;
import com.jyeol.dividend.persist.DividendRepository;
import com.jyeol.dividend.persist.entity.CompanyEntity;
import com.jyeol.dividend.persist.entity.DividendEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName",value = CacheKey.KEY_FINANCE)
    @Transactional(readOnly = true)
    public ScrapedResult getDividendByCompanyName(String companyName) {
        CompanyEntity companyEntity = companyRepository.findByName(companyName)
                .orElseThrow(() -> new CompanyException(CompanyError.INVALID_COMPANY_NAME));

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