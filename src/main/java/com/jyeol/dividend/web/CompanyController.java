package com.jyeol.dividend.web;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new RuntimeException("ticker is empty");
        }

        Company company = companyService.save(ticker);

        return ResponseEntity.ok(company);
    }

    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable) { //페이지값 임의변경 막기위해 final
        return ResponseEntity.ok(companyService.getAllCompany(pageable));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(String keyword) {
        return ResponseEntity.ok(companyService.autocomplete(keyword));
    }

}
