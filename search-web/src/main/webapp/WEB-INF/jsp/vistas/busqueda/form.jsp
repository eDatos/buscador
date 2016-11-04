<%@include file="/WEB-INF/jsp/util/taglibs.jsp"%>

<div class="istac-bs">

    <spring:url value="busca" var="formUrl"/>  
    <form:form modelAttribute="busqueda" method="get" action="${fn:escapeXml(formUrl)}" class="form-horizontal" role="form">
        <div class="custom-input-group" id="search-control">
            <form:input path="userQuery" type="text" class="form-control" placeholder="texto de búsqueda..." />
            <div class="custom-input-group-btn">
                <div class="btn-group" role="group">
                    <div class="dropdown dropdown-lg">
                        <button id="openAdvanceSearch" type="button" class="btn btn-default dropdown-toggle" aria-expanded="false" style="border-radius: 0px;">
                            <span class="caret"></span>
                        </button>
                        <div id="search-adv" class="dropdown-menu dropdown-menu-right" role="menu">
                            <div class="page-header">
                                <h3 class="resultado_titulo">Filtros de texto</h3>
                            </div>
                            <div class="row">
                                <div class="col-md-5" style="margin-bottom: 3px;">
                                    <form:select class="form-control" path="filtroTexto" items="${filtroTextoMap}" />
                                </div>
                                <div class="col-md-7">
                                    <form:input type="text" class="form-control" path="filtroTextoQuery" placeholder="Ejemplo: Tasa de paro" />
                                </div>
                            </div>
                            <div class="page-header">
                                <h3 class="resultado_titulo">De la sección</h3>
                            </div>
                            <div class="row">
                                <div class="col-md-5"  style="margin-bottom: 3px;">
                                    <form:select class="form-control" path="filtroSeccion" items="${filtroSeccionMap}" />
                                </div>
                                <div class="col-md-7">
                                    <div id="filtroSeccionAreaContainer"><form:select class="form-control" path="filtroSeccionArea" items="${filtroSeccionAreaMap}"/></div>
                                    <div id="filtroSeccionOperacionContainer" style="display: none"><form:select class="form-control" path="filtroSeccionOperacion" items="${filtroSeccionOperacionMap}"/></div>
                                </div>
                            </div>
                            <div class="row" style="margin-top: 1em; float:right">
                               <button id="advanceSearch" type="submit" class="btn btn-primary">Buscar</button>
                            </div>
                        </div>
                    </div>

                    <button id="normalSearch" class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </div>
        <p><form:errors path="userQuery"/></p>
    </form:form>
                
    <%@ include file="/WEB-INF/jsp/vistas/busqueda/facetedsTags.jsp" %>

    <p></p>
</div>
