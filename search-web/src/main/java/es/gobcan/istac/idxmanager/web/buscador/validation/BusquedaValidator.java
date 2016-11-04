package es.gobcan.istac.idxmanager.web.buscador.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;

public final class BusquedaValidator {

    private BusquedaValidator() {
    }

    public static void validate(Busqueda busqueda, Errors errors) {
        if (StringUtils.isEmpty(busqueda.getUserQuery()) && StringUtils.isEmpty(busqueda.getFiltroTextoQuery())) {
            errors.rejectValue("userQuery", "error.vacio");
        }
    }

}
