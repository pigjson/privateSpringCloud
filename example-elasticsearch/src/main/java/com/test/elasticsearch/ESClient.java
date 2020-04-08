package com.test.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.test.elasticsearch.annotation.Document;
import com.test.elasticsearch.annotation.Id;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Author: inggg
 * Date: 2019/8/12 9:42
 */
public class ESClient implements Closeable {

    private RestHighLevelClient client;
    private RequestOptions options;

    public ESClient(RestHighLevelClient client, RequestOptions options) {
        this.client = client;
        this.options = options;
    }

    public final MainResponse info() throws IOException {
        return client.info(options);
    }

    public final<T> T saveOrUpdate(T t) throws Exception {
        Class clazz = t.getClass();
        Document annotation = (Document) clazz.getAnnotation(Document.class);
        if (null == annotation) {
            throw new RuntimeException("无法使用ES save方法");
        }
        String index = annotation.indexName();
        if (StringUtils.isEmpty(index)) {
            throw new RuntimeException("索引不能为空");
        }
        Field[] fields = clazz.getDeclaredFields();
        int idCount = 0;
        Field idField = null;
        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (null != idAnnotation) {
                idCount++;
                idField = field;
            }
        }
        /*if (idCount == 0) {
            throw new RuntimeException(clazz.getName() + "没有id字段");
        }*/
        if (idCount > 1) {
            throw new RuntimeException(clazz.getName() + "有两个id");
        }
        String s = JSON.toJSONString(t);

        IndexRequest indexRequest = new IndexRequest(index);
        //是否有id，没有id使用默认
        if (null != idField) {
            idField.setAccessible(true);
            Object id = idField.get(t);
            if (null != id) {
                indexRequest.id(id.toString());
            }
        }
        indexRequest.source(s, XContentType.JSON);
        IndexResponse indexResponse = this.index(indexRequest);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED
                || indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return t;
        }
        return null;
    }

    public final<T> BulkResponse batchSaveOrUpdate(List<T> tList) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        for(T t:tList){
            Class clazz = t.getClass();
            Document annotation = (Document) clazz.getAnnotation(Document.class);
            if (null == annotation) {
                throw new RuntimeException("无妨使用ES save方法");
            }
            String index = annotation.indexName();
            if (StringUtils.isEmpty(index)) {
                throw new RuntimeException("索引不能为空");
            }
            Field[] fields = clazz.getDeclaredFields();
            int idCount = 0;
            Field idField = null;
            for (Field field : fields) {
                Id idAnnotation = field.getAnnotation(Id.class);
                if (null != idAnnotation) {
                    idCount++;
                    idField = field;
                }
            }
            /*if (idCount == 0) {
                throw new RuntimeException(clazz.getName() + "没有id字段");
            }*/
            if (idCount > 1) {
                throw new RuntimeException(clazz.getName() + "有两个id");
            }

            String s = JSON.toJSONString(t);

            IndexRequest indexRequest = new IndexRequest(index);
            //是否有id，没有id使用默认
            if (null != idField) {
                idField.setAccessible(true);
                Object id = idField.get(t);
                if (null != id) {
                    indexRequest.id(id.toString());
                }
            }
            indexRequest.source(s, XContentType.JSON);

            bulkRequest.add(indexRequest);

        }

        BulkResponse bulk = this.bulk(bulkRequest);
        return bulk;
    }

    public final IndexResponse index(IndexRequest indexRequest) throws IOException {
        return client.index(indexRequest, options);
    }

    public final BulkResponse bulk(BulkRequest bulkRequest) throws IOException {
        return client.bulk(bulkRequest, options);
    }

    public final DeleteResponse delete(DeleteRequest deleteRequest) throws IOException {
        return client.delete(deleteRequest, options);
    }

    public final SearchResponse search(SearchRequest searchRequest) throws IOException {
        return client.search(searchRequest, options);
    }

    public final IndicesClient indices() {
        return client.indices();
    }

    public final boolean exists(GetIndexRequest getIndexRequest) throws IOException {
        return client.indices().exists(getIndexRequest, options);
    }

    public final AcknowledgedResponse create(CreateIndexRequest createIndexRequest) throws IOException {
        return client.indices().create(createIndexRequest, options);
    }

    public final AcknowledgedResponse putMapping(PutMappingRequest putMappingRequest) throws IOException {
        return client.indices().putMapping(putMappingRequest, options);
    }



    @Override
    public void close() throws IOException {
        client.close();
    }
}
