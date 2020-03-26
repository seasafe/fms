package com.fms.authorization.zuul;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
 
public class RouteFilter extends ZuulFilter {
 
  @Override
  public String filterType() {
    return "route";
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
   System.out.println("Inside Route Filter");

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