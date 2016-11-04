<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
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
    <div id="principal_interior">
    	<c:import url="/WEB-INF/jsp/layout/cabecera.jsp"></c:import> 
    	
  		<c:import url="/WEB-INF/jsp/layout/migas.jsp">
			<c:param name="enlace" value="about:blank"></c:param>	
			<c:param name="texto">texto</c:param>
		</c:import>
		
		<div id="bloq_interior">
            <div class="conten">
		    </div>
		</div>
    </div>
	
  </body>

</html>