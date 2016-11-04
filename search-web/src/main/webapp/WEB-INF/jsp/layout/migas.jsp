<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/taglibs.jsp"%>


<c:set var="enlaces" value='${fn:split(param.enlace, "|")}'/>
 

<!-- MIGAS DE PAN-->
<div id="migas">
	<p class="txt">
	    <fmt:message key="migas.general.estaen" />:
	</p>

	<ul>
 <c:forTokens items="${param.texto}"
                 delims="|"
                 var="currentName"
                 varStatus="status">
      <c:choose>
      	<c:when test="${status.last}">      
      		<li>
			<strong><c:out value="${currentName}" /></strong>
		</li>
      	</c:when>     	
      	<c:otherwise>
	      	<li>
				<a href="${enlaces[status.index]}"><c:out value="${currentName}" /></a>
			</li>
      	</c:otherwise>
      </c:choose>
 	</c:forTokens>
	
	</ul>
</div>