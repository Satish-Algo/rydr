package com.rydr.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul Pre-filter for early request inspection, header logging, and route pre-processing.
 * Runs before request forwarding to downstream microservices.
 *
 * @author Rydr Team
 */
@Component
public class PreFilter extends ZuulFilter {

    /**
     * Determines whether this filter should execute for the current request context.
     *
     * @return boolean true if filter should execute, false to skip
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        // Log request source URI for debugging
        return false;
    }

    /**
     * Main execution logic for the pre-filter.
     *
     * @return execution result payload (null for standard Zuul flow)
     */
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("token");
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }
}

