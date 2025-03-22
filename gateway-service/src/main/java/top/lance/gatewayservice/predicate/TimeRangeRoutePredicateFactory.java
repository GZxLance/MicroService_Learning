package top.lance.gatewayservice.predicate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TimeRangeRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeRangeRoutePredicateFactory.Config> {
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TimeRangeRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(START_TIME, END_TIME);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                LocalTime now = LocalTime.now();
                LocalTime start = LocalTime.parse(config.getStartTime(), TIME_FORMATTER);
                LocalTime end = LocalTime.parse(config.getEndTime(), TIME_FORMATTER);
                
                return !now.isBefore(start) && !now.isAfter(end);
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("Time Range: %s - %s", config.getStartTime(), config.getEndTime());
            }
        };
    }

    @Data
    @NoArgsConstructor
    public static class Config {
        @NotNull
        private String startTime;
        @NotNull
        private String endTime;
    }
}