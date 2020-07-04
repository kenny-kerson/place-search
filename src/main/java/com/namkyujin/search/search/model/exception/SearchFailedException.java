package com.namkyujin.search.search.model.exception;

import java.util.Map;

public class SearchFailedException extends RuntimeException {

    public SearchFailedException(String message) {
        super(message);
    }

    public static SearchFailedException createMultiSearchFailedException(Map<String, Throwable> classNameToThrowable) {
        return new SearchFailedException(classNameToThrowable.toString());
    }

}