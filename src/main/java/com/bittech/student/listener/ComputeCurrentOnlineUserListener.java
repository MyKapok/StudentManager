package com.bittech.student.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Author: secondriver
 * Created: 2018/9/14
 */
@WebListener
public class ComputeCurrentOnlineUserListener implements HttpSessionListener {
    
    private static Logger logger = LoggerFactory.getLogger(ComputeCurrentOnlineUserListener.class);
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Object onlineUserValue = servletContext.getAttribute("onlineUser");
        if (onlineUserValue == null) {
            onlineUserValue = 0;
        } else {
            onlineUserValue = ((Integer) onlineUserValue) + 1;
        }
        servletContext.setAttribute("onlineUser", onlineUserValue);
        
        logger.debug("Session Created onlineUser {}", onlineUserValue);
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Object onlineUserValue = servletContext.getAttribute("onlineUser");
        onlineUserValue = ((Integer) onlineUserValue) - 1;
        servletContext.setAttribute("onlineUser", onlineUserValue);
        logger.debug("Session Destroyed onlineUser {}", onlineUserValue);
    }
}
