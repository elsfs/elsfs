package org.elsfs.web;


import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Steve Riesenberg
 * @since 1.1
 */
@Controller
public class DeviceErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        String message = getErrorMessage(request);
        if (message.startsWith("[access_denied]")) {
            return new ModelAndView("access-denied");
        }
        return new ModelAndView("error", Map.of("message", message));
    }

    private String getErrorMessage(HttpServletRequest request) {
        return (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    }

}
