<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.mvc.domain.BusquedaWrapper"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@page import="es.gobcan.istac.idxmanager.domain.mvc.Busqueda" %>
            	<div class="paginadorWrapper">
	            	<div class="paginador">
			           <%
			            if ((Long)request.getAttribute("numResult") != null && (Integer)request.getAttribute("resultByPage") != null & (Long)request.getAttribute("numResult") > (Integer)request.getAttribute("resultByPage")) {	
			                BusquedaWrapper busquedaPaginacionWrapper;
			                // Pagina anterior
			                if ((Long)request.getAttribute("startPage") != (Long)request.getAttribute("currentPage")) {
			                	StringBuilder anchor_open = new StringBuilder("<a class=\"anterior\" href=\"");
			                	busquedaPaginacionWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
			                	busquedaPaginacionWrapper.getBusqueda().setStartResult(String.valueOf((Long)request.getAttribute("currentPage")-1));
			                	anchor_open.append(WebUtils.generarUrl((String)pageContext.getAttribute("formUrl"), busquedaPaginacionWrapper));
			                	anchor_open.append("\">");
			                	out.print(anchor_open);
			                	out.print("<<");
			                	out.println("</a>");
			                }		
			                busquedaPaginacionWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
			                for (long j = (Long)request.getAttribute("startPage"); j <= (Long)request.getAttribute("finalPage"); j++) {		                	
			                	if (j == (Long)request.getAttribute("currentPage")) {
				                	out.print("<span class=\"this-page\">");
				                	out.print(j+1);
				                	out.println("</span>");
			                	}
			                	else {
			                		busquedaPaginacionWrapper.getBusqueda().setStartResult(String.valueOf(j));
				                	StringBuilder anchor_open = new StringBuilder("<a class=\"search\" href=\"");      
				                	anchor_open.append(WebUtils.generarUrl((String)pageContext.getAttribute("formUrl"), busquedaPaginacionWrapper));
			                		out.print(anchor_open.append("\">"));
				                	out.print(j+1);
				                	out.println("</a>");
			                	}
                                if (j != (Long)request.getAttribute("finalPage")) {
                                    out.println("|");
                                }
			                }
			                // Pagina siguiente
			                if ((Long)request.getAttribute("finalPage") !=(Long)request.getAttribute("currentPage")) {
			                	StringBuilder anchor_open = new StringBuilder("<a class=\"siguiente\" href=\"");
			                	busquedaPaginacionWrapper = new BusquedaWrapper((Busqueda)request.getAttribute("busqueda"));
			                	busquedaPaginacionWrapper.getBusqueda().setStartResult(String.valueOf((Long)request.getAttribute("currentPage")+1));
			                	anchor_open.append(WebUtils.generarUrl((String)pageContext.getAttribute("formUrl"), busquedaPaginacionWrapper));
			                	anchor_open.append("\">");
			                	out.print(anchor_open);
			                	out.print(">>");
			                	out.println("</a>");
			                }	    
			            }
		              %>
	            	</div>
            	</div>