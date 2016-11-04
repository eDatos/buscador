<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page import="es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain" %>
<%@page import="org.apache.solr.common.SolrDocument" %>
<%@page import="java.text.*, java.util.*" %>


<div class="resultado_busqueda">
<h3 class="resultado_titulo">
<c:choose>
	<c:when test="${item['formato'] eq 'ARCHIVO_PX'}"><a href="${urlPxJaxi}${item['id']}">${item['nm_title'][0]}</a></c:when>
	<c:when test="${item['formato'] eq 'ARCHIVO_PDF'}">
		<a href="${urlPxJaxiDescarga}${item['id']}">${item['nm_title'][0]}</a>
	</c:when>
	<c:when test="${item['formato'] eq 'PUBLICACION'}"><a href="${urlPublicacionJaxi}${item['id']}">${item['nm_title'][0]}</a></c:when>
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
     <c:forEach var="item2" items="${item['nm_type']}" varStatus="status">
     	<fmt:message key="dominio.nucleo.tipo.${fn:toLowerCase(item2)}" /><c:if test="${!status.last}">, </c:if>
     </c:forEach>
    </span>
    <c:if test="${item['formato'] eq 'ARCHIVO_PX'}">
        <span style="float:right;"><a href="${urlPxJaxiDescarga}${item['id']}">Descarga archivo PC-Axis</a></span>
    </c:if>
 </div>
</c:if>

 </div>
 
 