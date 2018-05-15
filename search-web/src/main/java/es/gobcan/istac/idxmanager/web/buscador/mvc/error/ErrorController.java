package es.gobcan.istac.idxmanager.web.buscador.mvc.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class ErrorController {

    protected Log LOGGER = LogFactory.getLog(ErrorController.class);

    @RequestMapping(value = {"/errors", "/**/{path:[^\\.]*}"})
    public String customError(HttpServletRequest request, HttpServletResponse response, Model model) {
        LOGGER.error(String.format("Se ha producido un error. Path: %s ", request.getPathInfo()));
        return "redirect:https://www.gobiernodecanarias.org/error.html";
    }
}