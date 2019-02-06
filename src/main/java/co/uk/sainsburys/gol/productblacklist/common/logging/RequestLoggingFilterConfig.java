package co.uk.sainsburys.gol.productblacklist.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@Slf4j
public class RequestLoggingFilterConfig {

    /**
     * Bean responsible for logging every REST call.
     * Requires logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter to be set in application.properties.
     * @return
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        log.info("Creating commonsRequestLoggingFilter");
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        return filter;
    }
}