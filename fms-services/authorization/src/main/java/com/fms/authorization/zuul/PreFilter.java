package com.fms.authorization.zuul;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
 
public class PreFilter extends ZuulFilter {
 
  @Override
  public String filterType() {
    return "pre";
  }
 
  @Override
  public int filterOrder() {
    return 1;
  }
 
  @Override
  public boolean shouldFilter() {
    return true;
  }
 
  @Override
  public Object run() {
      RequestContext ctx = RequestContext.getCurrentContext();
      HttpServletRequest request = ctx.getRequest();
      System.out.println("request content type : "+request.getContentType());
      request.getHeaderNames().asIterator().forEachRemaining(s -> System.out.println(s+" --  "+request.getHeader(s)));
      HttpServletResponse response = ctx.getResponse();
      System.out.println("response status:" + response.getStatus());
      System.out.println("response status:" + ctx.getResponseStatusCode());
      System.out.println("response content type:" + response.getContentType());
      return null;
}
}