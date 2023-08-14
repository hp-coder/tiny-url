package com.luban.tinyurl;

import com.luban.tinyurl.app.service.TinyUrlQueryApplicationService;
import com.luban.tinyurl.domain.TinyUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author hp
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RedirectController {

    private final TinyUrlQueryApplicationService tinyUrlQueryApplicationService;

    @Value("${url404:}")
    private String url404;

    @RequestMapping("/s/{code}")
    public Object redirect(@PathVariable("code") String code) {
        return tinyUrlQueryApplicationService.accessByCode(code)
                .filter(TinyUrl::accessible)
                .map(TinyUrl::getUrl)
                .map(this::createRedirect)
                .orElse(createRedirect(url404));
    }

    private Object createRedirect(String url) {
        final RedirectView redirectView = new RedirectView(url);
        return new ModelAndView(redirectView);
    }
}
