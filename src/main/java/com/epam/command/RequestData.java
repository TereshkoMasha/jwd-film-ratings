package com.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestData {
    private final Map<String, String[]> requestParametersValues;
    private final Map<String, Object> sessionAttributes;
    private final Map<String, Object> requestAttributeValues;
    private boolean isInvalidated = false;
    private final String path;

    public RequestData(HttpServletRequest request) {
        this.requestParametersValues = new HashMap<>();
        extractRequestParametersValues(request);
        this.sessionAttributes = new HashMap<>();
        this.requestAttributeValues = new HashMap<>();
        extractSessionAndRequestAttribute(request);
        this.path = request.getRequestURI();
    }

    public String getPath() {
        return path;
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

    public void addRequestAttribute(String attribute, Object attributeValue) {
        if (requestAttributeValues.containsKey(attribute)) {
            requestAttributeValues.replace(attribute, attributeValue);
        } else {
            requestAttributeValues.putIfAbsent(attribute, attributeValue);
        }
    }

    public void deleteSessionAttribute(String attribute) {
        sessionAttributes.remove(attribute);
    }

    public void insertSessionAndRequestAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (isInvalidated) {
            session.invalidate();
        } else {
            for (Map.Entry<String, Object> pair : sessionAttributes.entrySet()) {
                session.setAttribute(pair.getKey(), pair.getValue());
            }
        }
        for (Map.Entry<String, Object> pair : requestAttributeValues.entrySet()) {
            request.setAttribute(pair.getKey(), pair.getValue());
        }
    }

    public void setInvalidated(boolean invalidated) {
        isInvalidated = invalidated;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public String getRequestParameter(String parameterName) {
        String[] parametersValues = requestParametersValues.get(parameterName);
        return parametersValues[0];
    }

    public Map<String, String[]> getRequestParametersValues() {
        return requestParametersValues;
    }

    public Map<String, Object> getRequestAttributeValues() {
        return requestAttributeValues;
    }

    private void extractSessionAndRequestAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Enumeration<String> sessionEnumeration = session.getAttributeNames();
            Enumeration<String> requestEnumeration = request.getAttributeNames();
            while (sessionEnumeration.hasMoreElements()) {
                String attribute = sessionEnumeration.nextElement();
                sessionAttributes.putIfAbsent(attribute, session.getAttribute(attribute));
            }
            while (requestEnumeration.hasMoreElements()) {
                String attribute = requestEnumeration.nextElement();
                requestAttributeValues.putIfAbsent(attribute, request.getAttribute(attribute));
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
}