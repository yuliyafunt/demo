package com.example.demo.parser;

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
