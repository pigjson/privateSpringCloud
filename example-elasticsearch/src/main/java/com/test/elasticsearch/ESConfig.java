package com.test.elasticsearch;


import com.test.elasticsearch.annotation.Document;
import com.test.elasticsearch.util.ClassHelper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Author: inggg
 * Date: 2019/8/12 9:42
 */
@Configuration
public class ESConfig implements FactoryBean<ESClient>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ESConfig.class);

    private ESClient client;


    private final static String uris = "http://10.9.4.210:9200";
    private final static String username = "elastic";
    private final static String password = "123456";

//    private final static String uris = "http://127.0.0.1:9200";
//    private final static String username = "";
//    private final static String password = "";

    private final static String basePackage = "com.test";

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    @Override
    public ESClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<ESClient> getObjectType() {
        return ESClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private void buildClient() throws Exception{
        if (StringUtils.isEmpty(uris.replaceAll(" ", ""))) {
            throw new NullPointerException("spring.elasticsearch.jest.uris must not null");
        }
        String[] _uris = uris.replaceAll(" ", "").split(",");
        HttpHost[] httpHosts = new HttpHost[_uris.length];
        for (int i = 0; i < _uris.length; i++) {
            HttpHost httpHost = HttpHost.create(_uris[i]);
            httpHosts[i] = httpHost;
        }
        RestClientBuilder clientBuilder = RestClient.builder(httpHosts);

        clientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

            } else {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
                credentialsProvider.setCredentials(AuthScope.ANY, credentials);
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }

            try {
                ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
                PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
                httpClientBuilder.setConnectionManager(connectionManager);
                //HealthIndicator
            } catch (IOReactorException e) {
                logger.error("{}", e.getMessage());
                e.printStackTrace();
            }
            return httpClientBuilder;
        });
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(clientBuilder);
        RequestOptions options = RequestOptions.DEFAULT;
        client = new ESClient(restHighLevelClient, options);

        if (StringUtils.isEmpty(basePackage)) {
            throw new NullPointerException("spring.elasticsearch.base.package property is not set");
        }
        //初始化索引
        Set<Class<?>> clzFromPkg = ClassHelper.getClzFromPkg(basePackage);
        beansToIndices(clzFromPkg);

    }

    private void beansToIndices(Set<Class<?>> classes) throws IOException {
        for (Class clazz : classes) {
            Document document = (Document) clazz.getAnnotation(Document.class);
            if (null == document) {
                continue;
            }
            logger.info("{}", clazz.getName());
            if (!document.createIndex()) {
                //如果不创建索引，则跳过
                continue;
            }
            String index = document.indexName();
            if (StringUtils.isEmpty(index)) {
                throw new NullPointerException("indexName not null");
            }

            GetIndexRequest getIndexRequest = new GetIndexRequest(index);
            boolean exists = client.exists(getIndexRequest);

            XContentBuilder builder = clazzToMapping(clazz);

            if (exists) {
                logger.info("{}存在,不创建", index);
                putMapping(index, builder);
            } else {
                createMapping(index, document.shards(), document.replicas(), builder);
            }
        }
    }

    private XContentBuilder clazzToMapping(Class clazz) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .startObject("properties");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            com.test.elasticsearch.annotation.Field fieldAnnotation = field.getAnnotation(com.test.elasticsearch.annotation.Field.class);
            if (null == fieldAnnotation) {
                continue;
            }
            String type = fieldAnnotation.type().toString().toLowerCase();
            builder.startObject(field.getName()).field("type", type).endObject();
        }
        builder.endObject()
                .endObject();
        return builder;
    }

    private void putMapping(String index, XContentBuilder builder) throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest(index);
        putMappingRequest.source(builder);
        AcknowledgedResponse acknowledgedResponse = client.putMapping(putMappingRequest);
        if (acknowledgedResponse.isAcknowledged()) {
            logger.info("{}存在,更新成功", index);
        }
    }

    private void createMapping(String index, short shards, short replicas, XContentBuilder builder) throws IOException {
        CreateIndexRequest createRequest = new CreateIndexRequest(index);
        createRequest.settings(Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
        );

        createRequest.mapping(builder);
        AcknowledgedResponse response = client.create(createRequest);
        boolean acknowledged = response.isAcknowledged();
        if (acknowledged) {
            logger.info("{}创建成功", index);
        } else {
            throw new RuntimeException(index + "创建失败");
        }
    }

}

