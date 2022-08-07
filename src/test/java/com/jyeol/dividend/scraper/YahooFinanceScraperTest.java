package com.jyeol.dividend.scraper;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.ScrapedResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class YahooFinanceScraperTest {
    YahooFinanceScraper yahooFinanceScraper = new YahooFinanceScraper();

    @Test
    @DisplayName("야후 파이낸셜 스크랩핑 테스트")
    void scrapDividend() {
        //given
        Company company = Company.builder()
                .ticker("MSFT")
                .name("Microsoft Corporation")
                .build();
        //when
        ScrapedResult scrapedResult = yahooFinanceScraper.scrapDividend(company);

        //then
        assertEquals(company.getName(), scrapedResult.getCompany().getName());
        assertEquals(company.getTicker(), scrapedResult.getCompany().getTicker());
        assertTrue(scrapedResult.getDividendList().size() > 0);
    }

    @Test
    @DisplayName("회사명 스크랩 테스트")
    void scrapCompany() {
        //given
        String ticker = "MSFT";
        //when
        Optional<Company> company = yahooFinanceScraper.scrapCompanyByTicker(ticker);
        //then
        assertTrue(company.isPresent());
        assertEquals("Microsoft Corporation",company.get().getName());
    }
}