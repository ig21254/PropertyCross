package com.lasalle.second.part.propertycross.repositories;

import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.model.SearchHistory;

import java.util.List;

public interface SearchHistoryRepo {

    public boolean addSearchResult(PropertySearch propertySearch, int rentResults, int sellResults,
                                   String rawResults);
    public SearchHistory getSearchHistoryFromSearch(PropertySearch propertySearch);

    public List<SearchHistory> getRecentSearches();
}
