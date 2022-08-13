package com.jyeol.dividend.web;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.model.constants.CacheKey;
import com.jyeol.dividend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final CacheManager cacheManager;

    @PostMapping
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        return ResponseEntity.ok(companyService.save(request.getTicker().trim()));
    }

    @GetMapping
    @PreAuthorize("hasRole('READ')")
    public ResponseEntity<?> searchCompany(final Pageable pageable) { //페이지값 임의변경 방지
        return ResponseEntity.ok(companyService.getAllCompany(pageable));
    }


    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(String keyword) {
        return ResponseEntity.ok(companyService.autocomplete(keyword));
    }

    @DeleteMapping("/{ticker}")
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> deleteCompany(@PathVariable String ticker) {
        String companyName = companyService.deleteCompany(ticker);
        clearFinanceCache(companyName);

        return ResponseEntity.ok(companyName);
    }

    private void clearFinanceCache(String companyName) {
        try {
            Objects.requireNonNull(cacheManager.getCache(CacheKey.KEY_FINANCE))
                    .evict(companyName);
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
