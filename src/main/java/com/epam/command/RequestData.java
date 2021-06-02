package com.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestData {
    private final Map<String, String[]> requestParametersValues;
    private final Map<String, Object> sessionAttributes;
    private boolean isInvalidated = false;

    public RequestData(HttpServletRequest request) {
        this.requestParametersValues = new HashMap<>();
        extractRequestParametersValues(request);
        this.sessionAttributes = new HashMap<>();
        extractSessionAttribute(request);
    }

    private void extractSessionAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Enumeration<String> enumeration = session.getAttributeNames();
            while (enumeration.hasMoreElements()) {
                String attribute = enumeration.nextElement();
                sessionAttributes.putIfAbsent(attribute, session.getAttribute(attribute));
            }
        }
    }

    private void extractRequestParametersValues(HttpServletRequest request) {
        if (request.getParameterNames() != null) {
            Enumeration<String> parametersNames = request.getParameterNames();
            while (parametersNames.hasMoreElements()) {
                String parameterName = parametersNames.nextElement();
                String[] values = request.getParameterValues(parameterName);
                requestParametersValues.put(parameterName, values);
            }
        }
    }

    public String getRequestParameter(String parameterName) {
        String[] parametersValues = requestParametersValues.get(parameterName);
        return parametersValues[0];
    }

    public Object getSessionAttribute(String attribute) {
        return sessionAttributes.get(attribute);
    }


    public void addSessionAttribute(String attribute, Object attributeValue) {
        if (sessionAttributes.containsKey(attribute)) {
            sessionAttributes.replace(attribute, attributeValue);
        } else {
            sessionAttributes.putIfAbsent(attribute, attributeValue);
        }
    }

    public void insertSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (isInvalidated) {
            session.invalidate();
        } else {
            for (Map.Entry<String, Object> pair : sessionAttributes.entrySet()) {
                session.setAttribute(pair.getKey(), pair.getValue());
            }
        }
    }

    public void setInvalidated(boolean invalidated) {
        isInvalidated = invalidated;
    }
}