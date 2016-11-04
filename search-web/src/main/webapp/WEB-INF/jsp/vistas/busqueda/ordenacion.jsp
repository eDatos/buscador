<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.BusquedaWrapper"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@page import="es.gobcan.istac.idxmanager.domain.mvc.Busqueda" %>
<%@page import="org.apache.commons.lang.StringUtils" %>

				    <div id="opcion_ordenacion">
                        <c:if test="${not empty numResult}">
                            <p><fmt:message key="pagina.busqueda.numResultados" />: <b>${numResult}</b></p>
                        </c:if>
                        <% 
                           BusquedaWrapper busquedaOverWriteOrd = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
                        %>
				    	<p>Ordenar por:
				    		<c:choose>				    		
				    			<c:when test="${empty busqueda.sort}">
				    				<% busquedaOverWriteOrd.getBusqueda().setSort("1");%>
									<span><a href="<%=WebUtils.generarUrl("busca", busquedaOverWriteOrd)%>">Por fecha de actualización</a></span><span style="padding: 0 5px;">|</span><span><b>Por importancia</b></span>
								</c:when>
				    			<c:otherwise>
				    				<% busquedaOverWriteOrd.getBusqueda().setSort(StringUtils.EMPTY);%>
				    				<span><b>Por fecha de actualización</b></span><span style="padding: 0 5px;">|</span><span><a href="<%=WebUtils.generarUrl("busca", busquedaOverWriteOrd)%>">Por importancia</a></span>
				    			</c:otherwise>
				    		</c:choose>
				    	</p>
				    </div>