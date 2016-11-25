<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page import="es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain" %>
<%@page import="org.apache.solr.common.SolrDocument" %>
<%@page import="java.text.*, java.util.*" %>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>


<div class="resultado_busqueda">
<h3 class="resultado_titulo">
<c:choose>
	<c:when test="${item['nm_type'] eq 'DSC'}"><a href='<%=WebUtils.generatetUrlMetamacStatisticalVisualizer((SolrDocument)pageContext.getAttribute("item"))%>'>${item['nm_title'][0]}</a></c:when>
	<c:when test="${item['nm_type'] eq 'DSP'}"><a href='<%=WebUtils.generatetUrlMetamacStatisticalVisualizer((SolrDocument)pageContext.getAttribute("item"))%>'">${item['nm_title'][0]}</a></c:when>
	<c:otherwise>
		${item['nm_title'][0]}
	</c:otherwise>
</c:choose>
</h3>
<c:if test="${!empty item['nm_description']}">
 <div class="resultado_item">${item["nm_description"][0]}</div>
</c:if>
<c:if test="${!empty highlightingList}">
	<div class="resultado_item resultado_item_highlight">	
		<c:choose>
			<c:when test="${!empty highlightingList[item['id']]['nm_description']}">
 				<c:forEach var="item2" items="${highlightingList[item['id']]['nm_description']}" varStatus="status"><c:if test="${status.first}"><b>...</b></c:if><c:out escapeXml="false" value="${item2}"/><b> ...</b></c:forEach>
 			</c:when>
 			<c:when test="${!empty highlightingList[item['id']]['tk_contenido']}">
 				<c:forEach var="item2" items="${highlightingList[item['id']]['tk_contenido']}" varStatus="status"><c:if test="${status.first}"><b>...</b></c:if><c:out escapeXml="false" value="${item2}"/><b> ...</b></c:forEach>
			</c:when>
		</c:choose>			
	</div>
</c:if>
<c:if test="${!empty item['nm_type']}">
 <div class="resultado_item resultado_item_nm_type">
    <span>
 	 <c:if test="${!empty item['nm_last_update']}">
 		<fmt:formatDate value="${item['nm_last_update']}" pattern="dd/MM/yyyy | " />
	 </c:if>
     <fmt:message key="dominio.nucleo.tipo.${item['nm_type']}" />
    </span>
 </div>
</c:if>

 </div>
 
 