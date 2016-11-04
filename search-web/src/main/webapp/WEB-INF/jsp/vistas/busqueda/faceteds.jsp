<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.BusquedaWrapper"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@page import="es.gobcan.istac.idxmanager.domain.mvc.Busqueda" %>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.FacetFieldWeb" %>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.FacetValueWeb" %>
<%@page import="org.apache.commons.lang.StringUtils" %>
<%@page import="org.apache.solr.client.solrj.response.FacetField" %>
<%@page import="org.apache.solr.client.solrj.response.FacetField.Count" %>

			<c:forEach var="fieldFaceted" items="${facetedList}">
				<c:if test="${!empty fieldFaceted.values}">
				<div class="fondo_columnas">
	        		<h2 class="tit_azul"><fmt:message key="pagina.busqueda.facet.${fieldFaceted.name}" /></h2>
	        		<div class="bordes_columnas">
		          		<ul class="menu_lat_der">
		          		<c:forEach var="groupFaceted" items="${fieldFaceted.values}" end="2">	
		          			<%
                            BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
		          			busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
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
			            <%
			                BusquedaWrapper busquedaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
			                busquedaWrapper.getBusqueda().setStartResult(StringUtils.EMPTY);
			                busquedaWrapper.applySelectFacetForDetails((FacetFieldWeb)pageContext.getAttribute("fieldFaceted"));
							%>
		          			<li class="dif"><a rel="<%=WebUtils.generarUrl("facets",  busquedaWrapper)%>" class="moreFacets" href="#">más</a></li>
		          		</ul>
					</div>			
				</div>
				</c:if>
			</c:forEach>