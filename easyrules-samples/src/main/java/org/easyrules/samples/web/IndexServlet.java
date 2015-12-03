package org.easyrules.samples.web;

import static org.easyrules.samples.web.SuspiciousRequestRule.SUSPICIOUS;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        if (isSuspicious(request)) {
            out.print("bam, you got banned!");
        } else {
            out.print("welcome!");
        }
    }
    
    private boolean isSuspicious(HttpServletRequest request) {
        return request.getAttribute(SUSPICIOUS) != null;
    }
}
