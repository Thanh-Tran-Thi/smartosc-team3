package com.smartosc.training.controllers;

import com.smartosc.training.dtos.APIResponse;
import com.smartosc.training.dtos.UserRequest;
import com.smartosc.training.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * authentication
 *
 * @author Hieupv
 * @created_at 05/06/2020 - 9:40 AM
 * @created_by Hieupv
 * @since 05/06/2020
 */
@RestController
public class TestController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/")
    @ResponseBody
    public String index(@RequestParam(value = "lang", required = false) String local, HttpServletRequest request) {
        Locale locale = new Locale(local);
        System.out.println(locale);
        System.out.println(LocaleContextHolder.getLocale());
        String message = messageSource.getMessage("hello", null, LocaleContextHolder.getLocale());
        return message;
    }
}
