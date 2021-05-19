package com.epam.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class AttributeListener implements HttpSessionAttributeListener {
    private static final Logger LOGGER = LogManager.getLogger(AttributeListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        LOGGER.debug("Added attribute name: %s, value:%s %n", event.getName(),
                event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOGGER.debug("Removed attribute name: %s, value:%s %n", event.getName(),
                event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        LOGGER.debug("Replaced attribute name: %s, value:%s %n", event.getName(),
                event.getValue());
    }
}
