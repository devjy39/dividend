package com.jyeol.dividend.scraper;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.ScrapedResult;

import java.util.Optional;

public interface Scraper {
    Optional<Company> scrapCompanyByTicker(String ticker);

    ScrapedResult scrapDividend(Company company);
}
