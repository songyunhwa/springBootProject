package com.example.yhwasongtest.user.service.repository;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Iterator;

public interface SearchService {
    String getInputQuery() throws IOException;
    void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query);
}
