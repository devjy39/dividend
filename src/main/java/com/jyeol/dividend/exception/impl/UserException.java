package com.jyeol.dividend.exception.impl;

import com.jyeol.dividend.exception.AbstractException;
import com.jyeol.dividend.exception.type.UserError;

public class UserException extends AbstractException {
    private final UserError userError;
    @Override
    public int getStatusCode() {
        return this.userError.getStatusCode();
    }

    @Override
    public String getMessage() {
        return this.userError.getMessage();
    }

    public UserException(UserError userError) {
        this.userError = userError;
    }
}
