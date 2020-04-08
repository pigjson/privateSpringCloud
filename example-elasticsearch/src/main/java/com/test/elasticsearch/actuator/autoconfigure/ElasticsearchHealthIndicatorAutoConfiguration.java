package com.test.elasticsearch.actuator.autoconfigure;

import com.test.elasticsearch.ESClient;
import com.test.elasticsearch.actuator.ElasticsearchHealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: inggg
 * Date: 2019/10/29 8:44
 */
@Configuration
@ConditionalOnEnabledHealthIndicator("elasticsearch")
@AutoConfigureBefore(HealthIndicatorAutoConfiguration.class)
@AutoConfigureAfter({ ESClient.class })
public class ElasticsearchHealthIndicatorAutoConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(ElasticsearchHealthIndicatorAutoConfiguration.class);

    @Bean
    public HealthIndicator elasticsearchHealthIndicator(ESClient esClient) {
        return new ElasticsearchHealthIndicator(esClient);
    }

}
