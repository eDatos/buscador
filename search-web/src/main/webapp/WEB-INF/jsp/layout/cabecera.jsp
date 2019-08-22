<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- CABECERA -->

<div id="cabecera">
    <div id="cab_superior">
        <!-- MENU DE AYUDA -->
        <ul>
            <li>
                <a href="https://sede.gobcan.es/hacienda/" target="" accesskey="e" title="Sede electrónica">Sede electrónica</a>
            </li>
            <li>|</li>
            <li>
                <a href="http://www.gobiernodecanarias.org/istac/servicios/atencion.html" accesskey="o" title="Contacte con nosotros (tecla de acceso: o)">Contacto</a>
            </li>
        </ul>
    </div>
    
    <!-- IMAGEN ISTAC -->
    <h1>
        <a href="http://www.gobiernodecanarias.org/istac" title="Página principal del Instituto Canario de Estadística (ISTAC) - Opciones de accesibilidad (tecla de acceso: i)" accesskey="i">Instituto Canario de Estadística</a>
    </h1>

    <!-- begin: #menu -->
    <div id="menu_contextual">
        <ul class="menu">
            <li><a href="http://www.gobiernodecanarias.org/istac/temas_estadisticos/" class="select" accesskey="1" title="Estadísticas (tecla de acceso: 1)">Estadísticas</a></li>
            <li><a href="http://www.gobiernodecanarias.org/istac/istac/" class="inactive" accesskey="2" title="El ISTAC (tecla de acceso: 2)">El ISTAC</a></li>
            <li><a href="http://www.gobiernodecanarias.org/istac/webescolar/" class="inactive" accesskey="3" title="Web escolar (tecla de acceso: 3)">Web escolar</a></li>
            <li><a href="http://www.gobiernodecanarias.org/istac/noticias/" class="inactive" accesskey="4" title="Noticias (tecla de acceso: 4)">Noticias</a></li>
            <li><a href="https://sede.gobcan.es/istac/datos-abiertos/" class="inactive" accesskey="5" title="Datos abiertos (tecla de acceso: 5)">Datos abiertos</a></li>
        </ul>
        
        <!-- Búsqueda -->
        <spring:url value="busca" var="formCabeceraUrl"/>  
        <form name="gs" method="GET" action="${fn:escapeXml(formCabeceraUrl)}">
            <div id="formulario_google" style="padding-top: 1px;">
                <input type="text" id="userQuery" name="userQuery" placeholder="texto de búsqueda" class="buscar" value="">
                <input type="submit" name="Buscar" value="Buscar" class="boton">
            </div>
        </form>
    </div>
    <!-- end: #menu -->
</div>