/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.server.monitor.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.server.client.HdfsZooInstance;
import org.apache.accumulo.server.monitor.LogService;
import org.apache.accumulo.server.monitor.Monitor;
import org.apache.log4j.Logger;

abstract public class BasicServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  protected static final Logger log = Logger.getLogger(BasicServlet.class);
  
  abstract protected String getTitle(HttpServletRequest req);
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    StringBuilder sb = new StringBuilder();
    try {
      Monitor.fetchData();
      pageStart(req, resp, sb);
      pageBody(req, resp, sb);
      pageEnd(req, resp, sb);
    } catch (Throwable t) {
      log.error("Error building page " + req.getRequestURI(), t);
      sb.append("\n<pre>\n");
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      sb.append(sanitize(sw.getBuffer().toString()));
      sb.append("</pre>\n");
    } finally {
      resp.getWriter().print(sb);
      resp.getWriter().flush();
    }
  }
  
  protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
  
  private static final String DEFAULT_CONTENT_TYPE = "text/html";
  
  public static final Cookie getCookie(HttpServletRequest req, String name) {
    if (req.getCookies() != null) for (Cookie c : req.getCookies())
      if (c.getName().equals(name)) return c;
    return null;
  }
  
  protected void pageStart(HttpServletRequest req, HttpServletResponse resp, StringBuilder sb) throws Exception {
    resp.setContentType(DEFAULT_CONTENT_TYPE);
    int refresh = -1;
    Cookie c = getCookie(req, "page.refresh.rate");
    if (c != null && c.getValue() != null) {
      try {
        refresh = Integer.parseInt(c.getValue());
      } catch (NumberFormatException e) {
        // ignore improperly formatted user cookie
      }
    }
    
    // BEGIN PAGE
    sb.append("<html>\n");
    
    // BEGIN HEADER
    sb.append("<head>\n");
    sb.append("<title>").append(getTitle(req)).append(" - Accumulo ").append(Constants.VERSION).append("</title>\n");
    if ((refresh > 0) && (req.getRequestURI().startsWith("/docs") == false)) sb.append("<meta http-equiv='refresh' content='" + refresh + "' />\n");
    sb.append("<meta http-equiv='Content-Type' content='").append(DEFAULT_CONTENT_TYPE).append("' />\n");
    sb.append("<meta http-equiv='Content-Script-Type' content='text/javascript' />\n");
    sb.append("<meta http-equiv='Content-Style-Type' content='text/css' />\n");
    sb.append("<link rel='shortcut icon' type='image/jpg' href='/web/ac.jpg' />\n");
    sb.append("<link rel='stylesheet' type='text/css' href='/web/screen.css' media='screen' />\n");
    sb.append("<script src='/web/functions.js' type='text/javascript'></script>\n");
    
    sb.append("<!--[if lte IE 8]><script language=\"javascript\" type=\"text/javascript\" src=\"/web/flot/excanvas.min.js\"></script><![endif]-->\n");
    sb.append("<script language=\"javascript\" type=\"text/javascript\" src=\"/web/flot/jquery.js\"></script>\n");
    sb.append("<script language=\"javascript\" type=\"text/javascript\" src=\"/web/flot/jquery.flot.js\"></script>\n");
    
    sb.append("</head>\n");
    
    // BEGIN BODY OPENING
    sb.append("\n<body>\n");
    sb.append("<div id='content-wrapper'>\n");
    sb.append("<div id='content'>\n");
    sb.append("<div id='header'><h1>").append(getTitle(req)).append("</h1></div>\n");
    sb.append("<div id='subheader'>Instance&nbsp;Name:&nbsp;").append(HdfsZooInstance.getInstance().getInstanceName())
        .append("&nbsp;&nbsp;&nbsp;Version:&nbsp;").append(Constants.VERSION).append("\n");
    sb.append("<br><span class='smalltext'>Instance&nbsp;ID:&nbsp;").append(HdfsZooInstance.getInstance().getInstanceID()).append("</span>\n");
    sb.append("<br><span class='smalltext'>").append(new Date().toString().replace(" ", "&nbsp;")).append("</span></div>\n");
    
    // BEGIN LEFT SIDE
    sb.append("<div id='nav'>\n");
    sb.append("<span id='nav-title'><a href='/'>Overview</a></span><br />\n");
    sb.append("<hr />\n");
    sb.append("<a href='/master'>Master&nbsp;Server</a><br />\n");
    sb.append("<a href='/tservers'>Tablet&nbsp;Servers</a><br />\n");
    sb.append("<a href='/loggers'>Logger&nbsp;Servers</a><br />\n");
    sb.append("<a href='/gc'>Garbage&nbsp;Collector</a><br />\n");
    sb.append("<a href='/tables'>Tables</a><br />\n");
    sb.append("<a href='/trace/summary?minutes=10'>Recent&nbsp;Traces</a><br />\n");
    sb.append("<a href='/docs'>Documentation</a><br />\n");
    int numLogs = LogService.getInstance().getEvents().size();
    if (numLogs > 0) sb.append("<span class='error'><a href='/log'>Recent&nbsp;Logs&nbsp;<span class='smalltext'>(" + numLogs + ")</a></span></span><br />\n");
    int numProblems = Monitor.getProblemSummary().entrySet().size();
    if (numProblems > 0) sb.append("<span class='error'><a href='/problems'>Table&nbsp;Problems&nbsp;<span class='smalltext'>(" + numProblems
        + ")</a></span></span><br />\n");
    sb.append("<hr />\n");
    sb.append("<a href='/xml'>XML</a><hr />\n");
    sb.append("<div class='smalltext'>[<a href='").append("/op?action=refresh&value=").append(refresh < 1 ? "5" : "-1");
    sb.append("&redir=").append(currentPage(req)).append("'>");
    sb.append(refresh < 1 ? "en" : "dis").append("able&nbsp;auto-refresh</a>]</div>\n");
    sb.append("</div>\n"); // end <div id='nav'>
    
    sb.append("<div id='main'>\n");
    sb.append("<!-- BEGIN MAIN BODY CONTENT -->\n\n");
  }
  
  protected void pageBody(HttpServletRequest req, HttpServletResponse resp, StringBuilder sb) throws Exception {
    sb.append("This page intentionally left blank.");
  }
  
  protected void pageEnd(HttpServletRequest req, HttpServletResponse resp, StringBuilder sb) throws Exception {
    sb.append("\n<!-- END MAIN BODY CONTENT -->\n");
    sb.append("</div>\n"); // end <div id='main'>
    
    // BEGIN FOOTER
    sb.append("</div>\n"); // end <div id='content'>
    sb.append("</div>\n"); // end <div id='content-wrapper'>
    sb.append("</body>\n");
    sb.append("</html>\n");
  }
  
  public static String encode(String s) {
    try {
      return URLEncoder.encode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      Logger.getLogger(BasicServlet.class).fatal("UTF-8 is not a recognized encoding", e);
      throw new RuntimeException(e);
    }
  }
  
  public static String decode(String s) {
    try {
      return URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      Logger.getLogger(BasicServlet.class).fatal("UTF-8 is not a recognized encoding", e);
      throw new RuntimeException(e);
    }
  }
  
  public static String sanitize(String xml) {
    return xml.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }
  
  public static String currentPage(HttpServletRequest req) {
    String redir = req.getRequestURI();
    if (req.getQueryString() != null) redir += "?" + req.getQueryString();
    return encode(redir);
  }
  
  protected static void banner(StringBuilder sb, String klass, String text) {
    sb.append("<br />\n<h2 class='").append(klass).append("'>").append(text).append("</h2>\n");
  }
  
}
