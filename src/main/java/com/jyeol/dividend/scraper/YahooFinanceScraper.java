package com.jyeol.dividend.scraper;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.Dividend;
import com.jyeol.dividend.model.constants.Month;
import com.jyeol.dividend.model.ScrapedResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class YahooFinanceScraper implements Scraper{

    private static final String URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400; // 60 * 60 * 24

    @Override
    public ScrapedResult scrapDividend(Company company) {
        var scrapResult = new ScrapedResult();
        scrapResult.setCompany(company);

        try {
            long now = System.currentTimeMillis() / 1000;
            String url = String.format(URL, company.getTicker(), START_TIME, now);
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);

            List<Dividend> dividends = new ArrayList<>();
            for (Element child : tbody.children()) {
                String txt = child.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] split = txt.split(" ");
                int month = Month.strToNumber(split[0]);
                int day = Integer.parseInt(split[1].replace(",", ""));
                int year = Integer.parseInt(split[2]);
                String dividend = split[3];
                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + split[0]);
                }

                dividends.add(Dividend.builder()
                        .date(LocalDate.of(year, month, day))
                        .dividend(dividend)
                        .build());
            }
            scrapResult.setDividendList(dividends);
        } catch (Exception e) {
            log.error("yahoo scraping error {}", e.getMessage());
            e.printStackTrace();
        }

        return scrapResult;
    }

    @Override
    public Optional<Company> scrapCompanyByTicker(String ticker) {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String text = titleEle.text();
            String title = text.substring(0, text.indexOf("(")).trim();

            return Optional.ofNullable(Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build());
        } catch (IOException e) {
            log.error("yahoo scraping error {}", e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
