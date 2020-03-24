package com.fms.event.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Kesavalu
 *
 */
@RestControllerAdvice	
public class GlobalExceptionHandlerController {
    /**
     * 
     */
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    
    
    /**
     * @param res
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(HttpServletResponse res, CustomException e) throws IOException {
        LOG.error("ERROR", e);
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getHttpStatus(),e.getMessage()),e.getHttpStatus());
    }

    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(HttpServletResponse res, CustomException e) throws IOException {
        LOG.error("ERROR", e);
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getHttpStatus(),e.getMessage()),HttpStatus.ACCEPTED);
    }


    /**
     * @param res
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res, AccessDeniedException e) throws IOException {
        LOG.error("ERROR", e);
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    /**
     * @param res
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(HttpServletResponse res, IllegalArgumentException e) throws IOException {
        LOG.error("ERROR", e);
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }

    /**
     * @param res
     * @param e
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res, Exception e) throws IOException {
        LOG.error("ERROR", e);
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }


}
