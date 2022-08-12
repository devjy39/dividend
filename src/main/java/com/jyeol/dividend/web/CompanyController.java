package com.jyeol.dividend.web;

import com.jyeol.dividend.model.Company;
import com.jyeol.dividend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasRole('WRITE')")
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        return ResponseEntity.ok(companyService.save(request.getTicker().trim()));
    }

    @GetMapping
    @PreAuthorize("hasRole('READ')")
    public ResponseEntity<?> searchCompany(final Pageable pageable) { //페이지값 임의변경 막기위해 final
        return ResponseEntity.ok(companyService.getAllCompany(pageable));
    }


    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(String keyword) {
        return ResponseEntity.ok(companyService.autocomplete(keyword));
    }

}
