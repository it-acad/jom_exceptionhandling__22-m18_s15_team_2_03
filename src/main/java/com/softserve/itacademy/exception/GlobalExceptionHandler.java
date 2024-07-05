package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
{
        "timestamp": "2019-01-17T16:12:45.977+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "Error processing the request!",
        "path": "/my-endpoint-with-exceptions"
        }*/


    @ExceptionHandler(Exception.class)
    public ModelAndView handleServerException (Exception  e, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("500-page");
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        mav.addObject("message", "Message: " + e.getMessage());
        mav.addObject("timestamp", "Date: " +LocalDateTime.now());
        mav.addObject("path", "Path: " + request.getRequestURI());
        return mav;
    }

    @ExceptionHandler({EntityNotFoundException.class, NullEntityReferenceException.class})
    public ModelAndView handleEntityNotFoundException(RuntimeException e, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("404-page");
        mav.setStatus(e instanceof EntityNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST);
        mav.addObject("message", "Message: " + e.getMessage());
        mav.addObject("error", "Error: " + (e instanceof EntityNotFoundException ? "Not Found" :"Bad Request"));
        mav.addObject("timestamp", "Date: " +LocalDateTime.now());
        mav.addObject("path", "Path: " + request.getRequestURI());
        return mav;
    }

}
