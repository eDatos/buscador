<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<compress:html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<%@ include file="/WEB-INF/jsp/layout/head.jsp" %>
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
                    <div class="conten">
                      <%@ include file="/WEB-INF/jsp/vistas/busqueda/form.jsp" %>
        		    </div>
                </div>
            </div>
		</div>
		
		<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
    </div>
  </body>
</html>
</compress:html>