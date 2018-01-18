<%@ page pageEncoding="UTF-8"%>
<%@page import="es.gobcan.istac.idxmanager.web.buscador.util.WebUtils" %>
<%@include file="../util/taglibs.jsp"%>
	<head>
		<title><fmt:message key="welcome.title" /></title>
		<meta http-equiv="Content-Type"	content="text/html; charset=UTF-8" />
		<meta name="robots" content="all" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta name="keywords" content="gobierno canarias" />
		<meta name="description" content="Instituto Canario de Estadística" />
		<meta http-equiv="imagetoolbar" content="no" />
  		
        
        <!-- Gobierno de Canarias -->
        <link rel="shortcut icon" href="//www.gobiernodecanarias.org/gcc/img/favicon.ico"/>
                
        <link href="//www.gobiernodecanarias.org/gcc/css/estilos.css" rel="stylesheet" type="text/css" media="screen"/>
        <link href="//www.gobiernodecanarias.org/gcc/css/imprime.css" rel="stylesheet" type="text/css" media="print"/>
        <link href="//www.gobiernodecanarias.org/gcc/css/voz.css" rel="stylesheet" type="text/css" media="aural"/>
        
	 	<!-- Hojas de estilo -->
        <link rel="stylesheet" href="//www.gobiernodecanarias.org/gc/css/buscador.css"/>
        <link rel="stylesheet" href="//www.gobiernodecanarias.org/gc/css/google.css"/>

        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/gobcanoverwrite.css"/>
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/gobcanistacoverwrite.css"/>
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/paginadorsimple.css"/>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/istac-bs/css/istac-bs.css"/>
        
        <!-- Optional theme -->
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/istac-bs/css/istac-bstheme.css"/>
        
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/jquery.qtip-2.2.1.min.css"/>
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/select2-4.0.0.min.css" />
        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/select2-bootstrap.min.css" />

        <link rel="stylesheet" href="<%=WebUtils.getFullURL(request, false)%>theme/css/main.css"/>
        
	</head>