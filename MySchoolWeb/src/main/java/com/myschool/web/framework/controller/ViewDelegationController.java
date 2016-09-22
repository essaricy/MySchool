package com.myschool.web.framework.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * The Class ViewDelegationController.
 */
public class ViewDelegationController {

    /**
     * Delegate whole page view.
     *
     * @param request the request
     * @param viewName the view name
     * @param map the map
     * @return the model and view
     */
    public static ModelAndView delegateWholePageView(
            HttpServletRequest request, String viewName, Map<String, Object> map) {
        return delegateView(request, viewName, map, "wholepage");
    }

    /**
     * Delegate whole page view.
     *
     * @param request the request
     * @param viewName the view name
     * @return the model and view
     */
    public static ModelAndView delegateWholePageView(
            HttpServletRequest request, String viewName) {
        return delegateWholePageView(request, viewName, null);
    }

    /**
     * Delegate model page view.
     *
     * @param request the request
     * @param viewName the view name
     * @param map the map
     * @return the model and view
     */
    public static ModelAndView delegateModelPageView(
            HttpServletRequest request, String viewName, Map<String, Object> map) {
        return delegateView(request, viewName, map, "model");
    }

    /**
     * Delegate model page view.
     *
     * @param request the request
     * @param viewName the view name
     * @return the model and view
     */
    public static ModelAndView delegateModelPageView(
            HttpServletRequest request, String viewName) {
        return delegateModelPageView(request, viewName, null);
    }

    /**
     * Delegate tile view.
     *
     * @param request the request
     * @param viewName the export data
     * @param map the map
     * @return the model and view
     */
    public static ModelAndView delegateTileView(HttpServletRequest request,
            String viewName, Map<String, Object> map) {
        return delegateView(request, viewName, map, "tile");
    }

    /**
     * Delegate base view.
     *
     * @param request the request
     * @param viewName the view name
     * @param map the map
     * @return the model and view
     */
    public static ModelAndView delegateBaseView(HttpServletRequest request,
            String viewName, Map<String, Object> map) {
        return delegateView(request, viewName, map, null);
    }

    /**
     * Delegate multipart view.
     *
     * @param request the request
     * @param viewName the view name
     * @param map the map
     * @return the model and view
     */
    public static ModelAndView delegateMultipartView(
            HttpServletRequest request, String viewName,
            Map<String, Object> map) {
        return delegateView(request, viewName, map, "multipart");
    }

    /**
     * Delegate view.
     *
     * @param request the request
     * @param viewName the view name
     * @param map the map
     * @param viewType the view type
     * @return the model and view
     */
    private static ModelAndView delegateView(HttpServletRequest request,
            String viewName, Map<String, Object> map, String viewType) {
        ModelAndView modelAndView = new ModelAndView();
        if (map != null) {
            Set<String> keySet = map.keySet();
            for (String keyName : keySet) {
                modelAndView.addObject(keyName, map.get(keyName));
            }
        }
        if (viewType == null) {
            modelAndView.setViewName(viewName);
        } else {
            modelAndView.setViewName(viewName + "_" + viewType);
        }
        return modelAndView;
    }

}
