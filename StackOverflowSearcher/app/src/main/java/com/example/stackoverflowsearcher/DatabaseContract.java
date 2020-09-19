package com.example.stackoverflowsearcher;

import android.provider.BaseColumns;

public class DatabaseContract {
    public DatabaseContract() {}

    public static final String DB_NAME = "Searcher.db";

    public static class PastSearchesTable implements BaseColumns {
        public final static String TABLE_NAME = "PastSearches";
        public final static String SEARCH_ID_COLUMN_NAME = "searchId";
        public final static String SEARCH_TEXT_COLUMN_NAME = "searchText";
        public final static String SEARCH_LOCATION_COLUMN_NAME = "searchLocation";

    }
}
