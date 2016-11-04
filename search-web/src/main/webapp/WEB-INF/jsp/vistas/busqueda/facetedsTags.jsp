<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@page import="es.gobcan.istac.idxmanager.domain.mvc.Busqueda" %>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.BusquedaWrapper"%>
<%@page import="org.apache.commons.lang.StringUtils" %>

<c:if test="${busqueda.filterFacetSelected}">
    <div id="containerTags">
        <c:if test="${!empty busqueda.subCodFF}">
            <%
                BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
                busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                busquedaWrapper.getBusqueda().setSubCodFF(StringUtils.EMPTY);
            %>
           <div class="btn-tag">
            <span class="btn-label"> <a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>" role="button"> <i class="glyphicon glyphicon-minus-sign"></i></a></span>
            <span class="btn-labeltext">${subAreaFilterSelectTitle}</span>
           </div>
        </c:if>
        <c:if test="${!empty busqueda.svyCodFF}">
            <%
                BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
                busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                busquedaWrapper.getBusqueda().setSvyCodFF(StringUtils.EMPTY);
            %>
           <div class="btn-tag">
            <span class="btn-label"> <a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>" role="button"> <i class="glyphicon glyphicon-minus-sign"></i></a></span>
            <span class="btn-labeltext">${surveyFilterSelectTitle}</span>
           </div>
        </c:if>
        <c:if test="${!empty busqueda.cvgSCodFF}">
            <%
                BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
                busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                busquedaWrapper.getBusqueda().setCvgSCodFF(StringUtils.EMPTY);
            %>
           <div class="btn-tag">
            <span class="btn-label"> <a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>" role="button"> <i class="glyphicon glyphicon-minus-sign"></i></a></span>
            <span class="btn-labeltext">${cvgSFilterSelectTitle}</span>
           </div>
        </c:if>
        <c:if test="${!empty busqueda.cvgTCodFF}">
            <%
                BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
                busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                busquedaWrapper.getBusqueda().setCvgTCodFF(StringUtils.EMPTY);   
            %>
           <div class="btn-tag">
            <span class="btn-label"> <a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>" role="button"> <i class="glyphicon glyphicon-minus-sign"></i></a></span>
            <span class="btn-labeltext">${cvgTFilterSelectTitle}</span>
           </div>
        </c:if>
        <c:if test="${!empty busqueda.formato}">
            <%
                BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
                busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                busquedaWrapper.getBusqueda().setFormato(StringUtils.EMPTY);    
            %>
           <div class="btn-tag">
            <span class="btn-label"> <a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>" role="button"> <i class="glyphicon glyphicon-minus-sign"></i></a></span>
            <span class="btn-labeltext"><fmt:message key="pagina.busqueda.facet.formato.${busqueda.formato}" /></span>
           </div>
        </c:if>
    </div>
    <div class="row"></div>
</c:if>