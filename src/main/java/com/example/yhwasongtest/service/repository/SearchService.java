package com.example.yhwasongtest.service.repository;

import com.example.yhwasongtest.service.impl.SearchServiceImpl;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Iterator;

public interface SearchService {
    String getInputQuery() throws IOException;
    void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query);
}
