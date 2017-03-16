<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- CABECERA -->

<div id="cabecera">
    <div id="cab_superior">
        <!-- MENU DE AYUDA -->
        <ul>
            <li style="padding-right: 2px; margin-right: 1px;">Síguenos:
                <a href="https://www.gobiernodecanarias.org/istac/herramientas/rss.html" style="padding-right: 0px; margin-right: 0px;" accesskey="r" title="Canales RSS (tecla de acceso: r)">
                    <img style="vertical-align: text-bottom; float: none;" src="theme/images/icons/atom.png" title="Canales RSS (tecla de acceso: r)"
                        alt="Canales RSS (tecla de acceso: r)" height="14" width="14">
                </a>
                <a href="https://www.gobiernodecanarias.org/istac/twitter" style="padding-right: 0px; margin-right: 0px;" accesskey="t" title="Seguir a istac_es en Twitter (tecla de acceso: t)">
                    <img src="theme/images/icons/t_mini-a.png" style="vertical-align: text-bottom;"
                    title="Seguir a istac_es en Twitter (tecla de acceso: t)" alt="Seguir a istac_es en Twitter (tecla de acceso: t)" height="14"
                    width="14">
                </a>
                <a href="https://www.slideshare.net/ISTAC" style="padding-right: 0px; margin-right: 0px;" accesskey="s" title="Seguir a ISTAC en Slideshare (tecla de acceso: s)">
                    <img
                    src="theme/images/icons/slideshare.jpg" style="vertical-align: text-bottom;"
                    title="Seguir a ISTAC en Slideshare (tecla de acceso: s)" alt="Seguir a ISTAC en Slideshare (tecla de acceso: s)" height="14"
                    width="14">
                </a>
                <a href="https://www.youtube.com/user/istacES" style="padding-right: 2px; margin-right: 1px;" accesskey="y" title="Seguir a ISTAC en Youtube (tecla de acceso: y)">
                    <img src="theme/images/icons/youtube.png" style="vertical-align: text-bottom;"
                    title="Seguir a ISTAC en Youtube (tecla de acceso: y)" alt="Seguir a ISTAC en Youtube (tecla de acceso: y)" height="14" width="14">
                </a>
                <a href="https://public.tableau.com/profile/istac#!/" style="padding-right: 2px; margin-right: 1px;" accesskey="s" title="Seguir a ISTAC en Tableau (tecla de acceso: u)">
                    <img src="theme/images/icons/tableau.png" style="vertical-align: text-bottom;"
                    title="Seguir a ISTAC en Tableau (tecla de acceso: u)" alt="Seguir a ISTAC en Tableau (tecla de acceso: u)" height="14" width="14">
                </a>
            </li>
            <li style="padding-right: 2px; margin-right: 1px;">|</li>
            <li style="padding-right: 2px; margin-right: 1px;">
                <a href="http://www.gobiernodecanarias.org/istac/servicios/atencion.html" style="padding-right: 2px; margin-right: 1px;" accesskey="o" title="Contacte con nosotros (tecla de acceso: o)">Contacto</a>
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