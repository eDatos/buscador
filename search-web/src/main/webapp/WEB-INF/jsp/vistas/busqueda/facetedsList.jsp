<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@page import="es.gobcan.istac.idxmanager.domain.mvc.Busqueda" %>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.BusquedaWrapper"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.FacetValueWeb" %>
<%@page import="org.apache.commons.lang.StringUtils" %>
<%@page import="org.apache.solr.client.solrj.response.FacetField" %>
<%@page import="org.apache.solr.client.solrj.response.FacetField.Count" %>

	<div id="gutter"></div>
	
	<div id="col1">
		<c:forEach var="fieldFaceted" items="${facetedList}">
			<ul class="nav">
				<c:set var="xIter" value="${fn:substringBefore(fn:length(fieldFaceted.values) div 3, '.')}"></c:set>
				<c:set var="xIterAnt" value="${xIter - 1}"></c:set>
				<c:if test="${xIterAnt < 0}">
					<c:set var="xIterAnt" value="0"></c:set>
				</c:if>
			    <c:forEach var="groupFaceted" items="${fieldFaceted.values}" end="${xIterAnt + (fn:length(fieldFaceted.values) mod 3)}">
				<%
				    BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
				    busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
				    busquedaWrapper.applySelectFacetForDetails(null);
			        busquedaWrapper.setFacetValueWeb((FacetValueWeb)pageContext.getAttribute("groupFaceted"));
				%>
				<c:choose>
					<c:when test="${fieldFaceted.name eq 'formato'}">
	         				<li><a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>"><fmt:message key="pagina.busqueda.facet.formato.${groupFaceted.name}" /> (${groupFaceted.count})</a></li>
					</c:when>
					<c:otherwise>
         					<li><a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>">${groupFaceted.name} (${groupFaceted.count})</a></li>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
		
	<div id="col2">
		<c:forEach var="fieldFaceted" items="${facetedList}">
			<ul class="nav">
				<c:set var="xIter" value="${fn:substringBefore(fn:length(fieldFaceted.values) div 3, '.')}"></c:set>
				<c:set var="xIterAnt" value="${xIter - 1}"></c:set>
				<c:if test="${xIterAnt > 0}">
					<c:set var="xIterAnt" value="${xIterAnt - 1}"></c:set>
				</c:if>
				<c:forEach var="groupFaceted" items="${fieldFaceted.values}" begin="${xIter + (fn:length(fieldFaceted.values) mod 3)}" end="${xIter + xIterAnt + (fn:length(fieldFaceted.values) mod 3)}">
				<%
				    BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
				    busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                    busquedaWrapper.applySelectFacetForDetails(null);
                    busquedaWrapper.setFacetValueWeb((FacetValueWeb)pageContext.getAttribute("groupFaceted"));		
				%>
                <c:choose>
                    <c:when test="${fieldFaceted.name eq 'formato'}">
                            <li><a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>"><fmt:message key="pagina.busqueda.facet.formato.${groupFaceted.name}" /> (${groupFaceted.count})</a></li>
                    </c:when>
                    <c:otherwise>
                            <li><a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>">${groupFaceted.name} (${groupFaceted.count})</a></li>
                    </c:otherwise>
                </c:choose>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
	
	<div id="col3">
		<c:forEach var="fieldFaceted" items="${facetedList}">
			<ul class="nav">
				<c:set var="xIter" value="${fn:substringBefore(fn:length(fieldFaceted.values) div 3, '.')}"></c:set>
				<c:set var="xIterAnt" value="${xIter - 1}"></c:set>
				<c:if test="${xIterAnt > 0}">
					<c:set var="xIterAnt" value="${xIterAnt - 1}"></c:set>
				</c:if>
			    <c:forEach var="groupFaceted" items="${fieldFaceted.values}" begin="${xIter * 2 + (fn:length(fieldFaceted.values) mod 3)}">
				<%
				    BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
			        busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
                    busquedaWrapper.applySelectFacetForDetails(null);
                    busquedaWrapper.setFacetValueWeb((FacetValueWeb)pageContext.getAttribute("groupFaceted"));  		
				%>
                <c:choose>
                    <c:when test="${fieldFaceted.name eq 'formato'}">
                            <li><a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>"><fmt:message key="pagina.busqueda.facet.formato.${groupFaceted.name}" /> (${groupFaceted.count})</a></li>
                    </c:when>
                    <c:otherwise>
                            <li><a href="<%=WebUtils.generarUrl("busca", busquedaWrapper)%>">${groupFaceted.name} (${groupFaceted.count})</a></li>
                    </c:otherwise>
                </c:choose>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
