package com.epam.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/***
 * {@link AttributeListener} implements interface for receiving notification events about HttpSession attribute changes.
 */
@WebListener
public class AttributeListener implements HttpSessionAttributeListener {
    private static final Logger LOGGER = LogManager.getLogger(AttributeListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        Object attributeValue = event.getValue();
        LOGGER.debug("Attribute added : " + attributeName + " : " + attributeValue);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        Object attributeValue = event.getValue();
        LOGGER.debug("Attribute removed : " + attributeName + " : " + attributeValue);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        Object attributeValue = event.getValue();
        LOGGER.debug("Attribute replaced : " + attributeName + " : " + attributeValue);
    }
}
