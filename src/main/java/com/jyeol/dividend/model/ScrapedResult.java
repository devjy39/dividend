package com.jyeol.dividend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ScrapedResult {
    private Company company;
    private List<Dividend> dividendList;

    public ScrapedResult() {
        this.dividendList = new ArrayList<>();
    }
}
