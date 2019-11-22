<%@ page pageEncoding="UTF-8"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!-- PIE DE PÁGINA -->
    <div id="pie_istac">
        <div class="izda">© Gobierno de Canarias</div>
        <div class="dcha">
            <a href="http://opendefinition.org/od/2.0/es/" height="13px" target="_blank"><img alt="Open Data"
                src="theme/images/img/od_80x15_black.png"></a> |<a href="http://www.gobiernodecanarias.org/istac/aviso_legal.html"
                rel="license">Aviso Legal</a> |<a href="http://www.gobiernodecanarias.org/istac/politica-privacidad.html"
                rel="license">Política de privacidad</a> |<a href="http://www.gobiernodecanarias.org/principal/sugrec/">Sugerencias y Reclamaciones</a>
        </div>
    </div>


<!--JavaScript-->
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/js/jquery.qtip-2.2.1.min.js"></script>
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/js/jquery.cookie.min.js"></script>
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/istac-bs/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/js/select2-4.0.0.min.js"></script>
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/js/i18n/select2-4.0.0.es.min.js"></script>
<script type="text/javascript" src="<%=WebUtils.getFullURL(request, false)%>theme/js/custom.js"></script>

