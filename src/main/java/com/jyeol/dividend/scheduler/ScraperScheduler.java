package com.jyeol.dividend.scheduler;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.ScrapedResult;
import com.jyeol.dividend.model.constants.CacheKey;
import com.jyeol.dividend.persist.CompanyRepository;
import com.jyeol.dividend.persist.DividendRepository;
import com.jyeol.dividend.persist.entity.CompanyEntity;
import com.jyeol.dividend.persist.entity.DividendEntity;
import com.jyeol.dividend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@EnableCaching
@RequiredArgsConstructor
@Component
public class ScraperScheduler {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper scraper;

    /*  주기마다 캐시 삭제
    * */
    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}") //변경 요지가 있으니 config로 관리
    public void scraperScheduling() {
        log.info("scraping scheduler is started.");
        List<CompanyEntity> companyEntities = companyRepository.findAll();

        for (CompanyEntity companyEntity : companyEntities) {
            log.info("scraping scheduler is started {}",companyEntity.getName());
            ScrapedResult scrapedResult = scraper.scrapDividend(Company.from(companyEntity));

            scrapedResult.getDividendList()
                    .forEach(e -> {
                        if (!dividendRepository.existsByCompanyIdAndDate(companyEntity.getId(), e.getDate())) {
                            dividendRepository.save(DividendEntity.from(companyEntity.getId(), e.getDate(), e.getDividend()));
                        }
                    });

            // 스크래핑 사이트에 가하는 트래픽을 줄이기 위해 sleep
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("Interrupt occurred during scraping schedule");

                // interrupt 요청이 들어오면 무시하지 않고 처리함
                Thread.currentThread().interrupt();
            }

        }


    }

}
