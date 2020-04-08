package com.test.elasticsearch.actuator;


import com.test.elasticsearch.ESClient;
import org.elasticsearch.client.core.MainResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * Author: inggg
 * Date: 2019/10/29 8:38
 */
public class ElasticsearchHealthIndicator extends AbstractHealthIndicator {
    private final static Logger logger = LoggerFactory.getLogger(ElasticsearchHealthIndicator.class);

    private ESClient esClient;

    public ElasticsearchHealthIndicator(ESClient esClient) {
        this.esClient = esClient;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        MainResponse response = esClient.info();
        builder.withDetail("clusterName", response.getClusterName());
        builder.withDetail("clusterUuid", response.getClusterUuid());
        builder.withDetail("nodeName", response.getNodeName());
        builder.withDetail("tagLine", response.getTagline());
        builder.withDetail("version", response.getVersion());
        builder.up();
    }
}
