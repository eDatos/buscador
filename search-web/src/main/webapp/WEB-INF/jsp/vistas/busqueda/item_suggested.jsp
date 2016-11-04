<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@page import="es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain" %>
<%@page import="org.apache.solr.common.SolrDocument" %>


<div class="resultado_busqueda_suggested">
	<h3 class="resultado_titulo">
	<c:choose>
		<c:when test="${!empty item['cus_tittle']}"><a href="${item['cus_url']}">${item['cus_tittle']}</a></c:when>
	</c:choose>
	</h3>
	
	<c:if test="${!empty item['cus_desc']}">
 	<div class="resultado_item">	
 		<c:out escapeXml="false" value="${item['cus_desc']}"/>
 	</div>
	</c:if>
</div>