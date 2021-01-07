package com.example.demo.test;

import java.util.Collection;
import java.util.List;

public class Header {

    private final List<String> headerValues;

    public Header(List<String> headerValues) {
        this.headerValues = headerValues;
    }

    public List<String> getHeaderValues() {
        return headerValues;
    }
}
