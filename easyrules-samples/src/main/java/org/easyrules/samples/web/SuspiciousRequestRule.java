package org.easyrules.samples.web;

import javax.servlet.http.HttpServletRequest;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule
public class SuspiciousRequestRule {

    public static final String SUSPICIOUS = "suspicious";
    
    private HttpServletRequest request;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Condition
    public boolean isSuspicious() {
        return request.getParameter(SUSPICIOUS) != null;
    }
    
    @Action
    public void setSuspicious() {
        request.setAttribute(SUSPICIOUS, true);
    }
}
