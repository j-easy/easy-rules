package org.easyrules.samples.web;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.easyrules.api.RulesEngine;

@WebFilter("/*")
public class SuspiciousRequestFilter implements Filter {

    private RulesEngine rulesEngine;

    private SuspiciousRequestRule suspiciousRequestRule;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        suspiciousRequestRule = new SuspiciousRequestRule();
        rulesEngine = aNewRulesEngine().withSilentMode(true).build();
        rulesEngine.registerRule(suspiciousRequestRule);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        suspiciousRequestRule.setRequest((HttpServletRequest)request);
        rulesEngine.fireRules();
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
}
