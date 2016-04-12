package de.andre.web.controller.util;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
    @RequestMapping("/error")
    public String error(final HttpServletRequest request, final Model model) {
        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        final String errorMessage = (null == throwable) ? null : throwable.getMessage();
        model.addAttribute("errorMessage", errorMessage);

        return "error";
    }

    @RequestMapping("/simulateError")
    public void simulateError() {
        throw new RuntimeException("This is a simulated error message");
    }
}
