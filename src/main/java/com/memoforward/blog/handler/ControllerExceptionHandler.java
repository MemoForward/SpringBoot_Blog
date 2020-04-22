package com.memoforward.blog.handler;

import com.memoforward.blog.exception.CustomizedBlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,
                                         Exception e,
                                         Model model){
        logger.error("Request URL: {}, Exception : {}", request.getRequestURL(), e);
        ModelAndView mv = new ModelAndView();
        model.addAttribute("error", e.getMessage());
        if(e instanceof CustomizedBlogException){
            if(((CustomizedBlogException) e).getCode() == 2000)
                return new ModelAndView("error/404");
            else return new ModelAndView("error/error");
        }
        return new ModelAndView("error/500");
    }
}
