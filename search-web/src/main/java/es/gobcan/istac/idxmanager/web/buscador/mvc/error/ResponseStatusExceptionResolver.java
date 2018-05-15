package es.gobcan.istac.idxmanager.web.buscador.mvc.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

@Component
public class ResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

    protected Log LOGGER = LogFactory.getLog(ResponseStatusExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LOGGER.error(String.format("Error: Se ha producido un error"), ex);
        return new ModelAndView("forward:/app/errors");
    }

}