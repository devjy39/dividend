package com.jyeol.dividend.service;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.ScrapedResult;
import com.jyeol.dividend.persist.CompanyRepository;
import com.jyeol.dividend.persist.DividendRepository;
import com.jyeol.dividend.persist.entity.CompanyEntity;
import com.jyeol.dividend.persist.entity.DividendEntity;
import com.jyeol.dividend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final Trie<String, String> trie;
    private final Scraper scraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {
        if (companyRepository.existsByTicker(ticker)) {
            throw new RuntimeException("Already exists ticker -> " + ticker);
        }

        return storeCompanyAndDividend(ticker);
    }

    public Page<Company> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(Company::from);
    }


    /*
     *   db에 company 정보가 없을 때 scrap 해서 영속화
     * */
    private Company storeCompanyAndDividend(String ticker) {
        Company company = scraper.scrapCompanyByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("Failed to scrap ticker -> " + ticker));
        ScrapedResult scrapedResult = scraper.scrapDividend(company);

        CompanyEntity companyEntity = companyRepository.save(CompanyEntity.from(company));

        List<DividendEntity> dividendEntities = scrapedResult.getDividendList().stream()
                .map(e -> DividendEntity.from(companyEntity.getId(), e.getDate(), e.getDividend()))
                .collect(Collectors.toList());
        dividendRepository.saveAll(dividendEntities);


        addAutocompleteKeyword(company.getName());

        return company;
    }

    private void addAutocompleteKeyword(String keyword) {
        trie.put(keyword.toLowerCase(), null);
    }

    public List<String> autocomplete(String keyword) {
        return new ArrayList<>(trie.prefixMap(keyword.toLowerCase()).keySet());
    }

    public void deleteAutocompleteKeyword(String keyword) {
        trie.remove(keyword.toLowerCase());
    }

}
