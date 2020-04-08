package com.test.elasticsearch;


import com.test.elasticsearch.annotation.Document;
import org.elasticsearch.action.search.SearchRequest;

/**
 * Author: inggg
 * Date: 2019/8/22 14:58
 */
public class ESRequestFactory {

    public static SearchRequest getSearchRequest(Class clazz) {
        Document annotation = (Document) clazz.getAnnotation(Document.class);
        if (null == annotation) {
            throw new IllegalArgumentException(clazz.getName() + "没有Document注解");
        }
        SearchRequest searchRequest = new SearchRequest(annotation.indexName());
        return searchRequest;
    }
}
