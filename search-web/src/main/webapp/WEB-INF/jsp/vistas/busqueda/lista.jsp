<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page import="es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain" %>
<%@page import="org.apache.solr.common.SolrDocument" %>
<compress:html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<c:import url="/WEB-INF/jsp/layout/head.jsp">
	<c:param name="titulo">
		<fmt:message key="welcome.title" />
	</c:param>
	<c:param name="metakeywords" value="Buscador"></c:param>
	<c:param name="metadescription" value="Instituto Canario de Estadística"></c:param>
</c:import>

  <body>
    <div id="contenido">
    	<c:import url="/WEB-INF/jsp/layout/cabecera.jsp"></c:import> 
    	
    	<!-- MIGAS DE PAN-->
		<div id="migas">
			<p class="txt"><fmt:message key="migas.general.estaen" />:</p>		
			<ul>
				<li><strong>Búsqueda</strong></li>
			</ul>
		</div>
        
		<div id="bloq_interior">
            <div class="contenido">
                <div id="centercontainer">
    			<div class="bloq_izq_grande">
    	            <div class="conten">
    	              
                      <%@ include file="/WEB-INF/jsp/vistas/busqueda/form.jsp" %>
    	 			  
                      <div class="pestanias">
    	 			    <% 
    	 			        BusquedaWrapper busquedaPestaniaWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
    	 			        busquedaPestaniaWrapper.getBusqueda().setPestania("ALL");
                        %>
                        <c:choose>
                            <c:when test="${busqueda.pestania eq 'ALL'}">
                                <span class="pestSel">
                                    <input type="button" class="bt_pst" id="busca_todo" value="Todo" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;">
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="pest">
                                    <input type="button" class="bt_pst" id="busca_todo" value="Todo" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
                                </span>
                            </c:otherwise>
                        </c:choose>
                        <span class="entreSel"></span>
                        <% 
                            busquedaPestaniaWrapper.getBusqueda().setPestania("DSC");
                        %>
    					<c:choose>
    						<c:when test="${busqueda.pestania eq 'DSC'}">
    							<span class="pestSel">
    			 			  		<input type="button" class="bt_pst" id="busca_datos" value="Datos" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
    			 			  	</span>
    						</c:when>
    						<c:otherwise>
    							<span class="pest">
    			 			  		<input type="button" class="bt_pst" id="busca_datos" value="Datos" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
    			 			  	</span>
    						</c:otherwise>
    					</c:choose>
    					<span class="entreSel"></span>
                        <% 
                            busquedaPestaniaWrapper.getBusqueda().setPestania("DSP");
                        %>
    					<c:choose>
    						<c:when test="${busqueda.pestania eq 'DSP'}">
    							<span class="pestSel">
    			 			  		<input type="button" class="bt_pst" id="busca_publicaciones" value="Publicaciones" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
    			 			  	</span>
    						</c:when>
    						<c:otherwise>
    							<span class="pest">
    			 			  		<input type="button" class="bt_pst" id="busca_publicaciones" value="Publicaciones" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
    			 			  	</span>
    						</c:otherwise>
    					</c:choose>
                        <c:if test="${1 < 0}">
        					<span class="entreSel"></span>
                            <% 
                                busquedaPestaniaWrapper.getBusqueda().setPestania("COMP"); //poner pestania de complementari
                            %>
        					<c:choose>
        						<c:when test="${busqueda.pestania eq 'COMP'}">
        							<span class="pestSel">
        			 			  		<input type="button" class="bt_pst" id="busca_complementaria" value="Complementaria" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
        			 			  	</span>
        						</c:when>
        						<c:otherwise>
        							<span class="pest">
        			 			  		<input type="button" class="bt_pst" id="busca_complementaria" value="Complementaria" tabindex="12" accesskey="d" title="Alt+d" onclick="window.location.href='<%=WebUtils.generarUrl("busca", busquedaPestaniaWrapper)%>'; return false;"></input>
        			 			  	</span>
        						</c:otherwise>
        					</c:choose>
                        </c:if>
    	              </div>
    				  
    				  <div class="conten">
                        <div style="float:right">
                            <span class="istac-bs input-group-btn">
                              <button id="btnExpandInfo" type="button" class="btn-link"><i id="iExpandInfo" class="glyphicon glyphicon-eye-close"></i>
                                <span>Menos detalles</span><span style="display: none">Más detalles</span>
                              </button>
                            </span>
                        </div>
    					  <%@ include file="/WEB-INF/jsp/vistas/busqueda/ordenacion.jsp" %>
    		 			  <%@ include file="/WEB-INF/jsp/vistas/busqueda/paginacion.jsp" %>
    		 			  
    		 			  <c:forEach var="item" items="${suggestedDocumentList}">
    		              		<%@ include file="/WEB-INF/jsp/vistas/busqueda/item_suggested.jsp" %>
    		              </c:forEach>
    		
    		              <c:forEach var="item" items="${documentList}" begin="0" end="${resultByPage}">
    		              		<c:if test="${item['origen_recurso'] eq 'GPE'}">
    		              			<%@ include file="/WEB-INF/jsp/vistas/busqueda/item_gpe.jsp" %>
    		              		</c:if>
    		              		<c:if test="${item['origen_recurso'] eq 'WEB'}">
    		              			<%@ include file="/WEB-INF/jsp/vistas/busqueda/item_web.jsp" %>
    		              		</c:if>
                                <c:if test="${item['origen_recurso'] eq 'MTM_STAT_RESOURCES'}">
                                    <%@ include file="/WEB-INF/jsp/vistas/busqueda/item_metamac.jsp" %>
                                </c:if>
    		              </c:forEach>
    					  <%@ include file="/WEB-INF/jsp/vistas/busqueda/paginacion.jsp" %>
    				 
    				  </div>
                      
    			    </div>
    			</div>			
        			<div class="columna_pequ">
        			   <%@ include file="/WEB-INF/jsp/vistas/busqueda/faceteds.jsp"%>
        			</div>
                </div>
            </div>
		</div>
		<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
    </div>	
  </body>
</html>
</compress:html>
