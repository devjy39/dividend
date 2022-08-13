package com.jyeol.dividend.exception.impl;

import com.jyeol.dividend.exception.AbstractException;
import com.jyeol.dividend.exception.type.CompanyError;

public class CompanyException extends AbstractException {
    private final CompanyError companyError;
    @Override
    public int getStatusCode() {
        return this.companyError.getStatusCode();
    }

    @Override
    public String getMessage() {
        return this.companyError.getMessage();
    }

    public CompanyException(CompanyError companyError) {
        this.companyError = companyError;
    }
}
