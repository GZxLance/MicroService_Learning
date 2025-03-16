package top.lance.contentservice.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author： Lance
 * @create： 2025/3/14
 * @Description SentinelRequestOriginParser
 **/
public class SentinelRequestOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getParameter("serviceName");
    }

}
