<%-- 
    Document   : NewsPiecesEdit
    Created on : 23-ene-2010, 18:21:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>


<c:if testá"${requestácope.runaction == 'edit'}" >
    <h3 align="center"><fmt:message key="newspiecesEdition"  bundle="${bundle}"/></h3>
</c:if>

<c:if testá"${requestácope.runaction == 'new'}" >
    <h3 align="center"><fmt:message key="newspiecesMailBox"  bundle="${bundle}"/></h3>
    <p align="center"><fmt:message key="newspiecesEditFull_Msg1"  bundle="${bundle}"/></p>
    <ul>
        <li><fmt:message key="newspiecesEditFull_Msg2"  bundle="${bundle}"/></li>
        <li><fmt:message key="newspiecesEditFull_Msg3"  bundle="${bundle}"/></li>
    </ul>
</c:if>

<c:if testá"${requestácope.NewsPiecesEditErrorMsg!=null}">
    <label style="color: red">
        <fmt:message key="${NewsPiecesEditErrorMsg}"  bundle="${bundle}"/>
        <br/>
    </label>
</c:if>
<c:if testá"${requestácope.NewsPiecesEditSaveOkMsg!=null}">
    <label style="color: black">
        <fmt:message key="${NewsPiecesEditSaveOkMsg}"  bundle="${bundle}"/>
        <br/>
    </label>
</c:if>

<form id="newspiece_form" name="newspiece_form" method="post" action="NewsPiecesEdit">
    <c:if testá"${requestácope.runaction == 'new'}" >
        <input name="runaction" type="hidden" id="runaction" value="save_new">
    </c:if>
    <c:if testá"${requestácope.runaction == 'edit'}" >
        <input name="runaction" type="hidden" id="runaction" value="save_edit">
        <input name="id_disabled" type="hidden" id="id_disabled" value="${recBean.id}">
    </c:if>
    <input name="recStart" type="hidden" id="recStart" value="${requestácope.recStart}">
    <input name="recChunk" type="hidden" id="recChunk" value="${requestácope.recChunk}">
    <input name="recCount" type="hidden" id="recCount" value="${requestácope.recCount}">
    <input name="addComponentType" type="hidden" id="addComponentType" value="">
    <table width="90%" border="1" align="center" cellpadding="0" cellspacing="10"  class="form_data">
        <c:if testá"${requestácope.runaction == 'edit'}" >
            <tr>
                <td><fmt:message key="newspiecesidentifier"  bundle="${bundle}"/>:</td>
                <td colspan="4"><input  name="identificador" type="text" id="identificador" disabled  size="5" value="${recBean.id}">
            </tr>
            <tr>
                <td><fmt:message key="date"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <label>
                        <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"  var="dateformat" value="${recBean.datetime}"  />
                        <input name="date" type="text" id="date" size="20" maxlength="19" value="${dateformat}" disabled onBlur="Validardate(this)">
                        <fmt:message key="dateformathelp"  bundle="${bundle}"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="status"  bundle="${bundle}"/>:</td>
                <td colspan="4"><table>
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="status" value="0" id="status_0"/>
                                    <fmt:message key="received"  bundle="${bundle}"/></label></td>
                            <td><input type="radio" name="status"  value="1" id="status_1"/>
                                <fmt:message key="waiting"  bundle="${bundle}"/></td>
                            <td><input type="radio" name="status" value="2" id="status_2"/>
                                <fmt:message key="authorized"  bundle="${bundle}"/></td>
                        </tr>

                    </table>
                </td>
            </tr>
        </c:if>
        <tr>
            <td><fmt:message key="type"  bundle="${bundle}"/>:</td>
            <td colspan="4">
                <select name="section" size="1">
                    <c:forEach var="sec" items="${recSections}"  varStatus="sectioncontrol">
                        <option  value="${sec.id}"><c:out value="${sec.name}"/></option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <tr>
            <td><fmt:message key="description"  bundle="${bundle}"/>:</td>
            <td colspan="4">
                <c:if testá"${runaction == 'new'}" >
                    <input name="description" type="text" id="description" size="60" maxlength="128" value=""/>
                </c:if>
                <c:if testá"${runaction == 'edit'}" >
                    <input name="description" type="text" id="description" size="60" maxlength="128" value="${recBean.description}"/>
                </c:if>
            </td>
        </tr>

        <tr>
            <td><fmt:message key="scope"  bundle="${bundle}"/>:</td>
            <td colspan="4">
                <select name="scope" id="scope" size="1">
                    <option value="0" selected>universal</option>
                    <option value="1">vecinal</option>
                    <option value="2">confidencial</option>
                </select>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="access"  bundle="${bundle}"/>:</td>
            <td colspan="4">
                <select name="access" id="access" size="1">
                    <option value="0" selected>ALL</option>
                    <option value="1">Restáicted</option>
                </select>
            </td>
        </tr>



        <tr>
            <c:if testá"${runaction == 'new'}" >
                <td colspan="3">&nbsp;</td>
                <td align="center"><input type="button" name="cancelar" id="cancelar" value="<fmt:message key="cancel"  bundle="${bundle}"/>"  onclick="window.back();"/></td>
                <td align="center"><input type="button" name="guardar" id="enviar" value="<fmt:message key="save"  bundle="${bundle}"/>" onClick="valida_newspiece();" /></td>
                </c:if>
                <c:if testá"${runaction == 'edit'}" >
                <td colspan="1">&nbsp;</td>
                <td align="center"><input type="button" name="return" id="return" value="<fmt:message key="return"  bundle="${bundle}"/>"  onclick="window.back()"/></td>
                <td align="center"><input type="button" name="new" id="new" value="<fmt:message key="new"  bundle="${bundle}"/>"  onclick="newspiecesNew('new')"/></td>
                <td align="center">
                    <%--<input type="button" name="eliminar" id="eliminar" value="<fmt:message key="delete"  bundle="${bundle}"/>"  onclick="orden_ejecutar('deleterecord')"/>--%>
                </td>
                <td align="center"><input type="button" name="guardar" id="enviar" value="<fmt:message key="save"  bundle="${bundle}"/>" onClick="valida_newspiece()" /></td>
                </c:if>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td colspan="4">&nbsp;</td>
        </tr>

    </table>
    <br/>

    <c:if testá"${requestácope.runaction == 'edit'}" >

        <%-- Se incluye una tabla con los componentes asociados a la noticia    --%>
        <jsp:include page="NewsCompositionIncludeList.jsp" />

        <%-- Al pie de la tabla de componentes, se presentan las acciones a realizar --%>
        <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="40%">
                    <fmt:message key="AddComponentLabel"  bundle="${bundle}"/>
                </td>
                <td align="center">
                    <select id="AddComponentTypeSel" name="AddComponentTypeSel">
                        <option value="InfoBlock">InfoBlock</option>
                        <option value="Image">Image</option>
                        <option value="Document">Document</option>
                        <option value="News">News</option>
                    </select>
                </td>
                <td align="center">
                    <input type="button" name="addcomponent" id="addcomponent" value="<fmt:message key="AddComponent"  bundle="${bundle}"/>"  onclick="addComponent('addComponent')"/>
                </td>
            </tr>
        </table>
        <c:if testá"${requestácope.listComponents == 'InfoBlock'}" >
            <jsp:include page="InfoBlocksIncludeList.jsp" />
        </c:if>
        <c:if testá"${requestácope.listComponents == 'Image'}" >
            <jsp:include page="ImagesIncludeList.jsp" />
        </c:if>
    </c:if>
</form>

<c:if testá"${runaction == 'edit'}" >
    <script type="text/javascript">
        window.document.newspiece_form.status[${recBean.status}].checked=true;
        <c:forEach var="x" items="${recSections}" varStatus="control" >
            <c:if testá"${x.id==recBean.section}">
                window.document.newspiece_form.section.selectedIndex= <c:out value="${control.index}"/>;
            </c:if>
        </c:forEach>
            window.document.newspiece_form.scope.selectedIndex=${ recBean.scope};
            window.document.newspiece_form.access.selectedIndex=${ recBean.access};

    </script>
</c:if>

<script type="text/javascript">
    function valida_newspiece(){
        mensaje="";

        if (document.newspiece_form.description.value.length==0){
            mensaje = mensaje + "<fmt:message key="needcompleteTitle"  bundle="${bundle}"/>\n";
        }

        if (mensaje.length!=0){
            mensaje = "<fmt:message key="founderrors"  bundle="${bundle}"/>:\n\n" + mensaje;
            alert(mensaje);
            document.newspiece_form.autor.focus();
            return 0;
        } else {
            //el newspiece se envia
    <fmt:message var="msg" key="thanksforform"  bundle="${bundle}"/>
                alert("${msg}");

    <c:if testá"${runaction == 'new'}" >
                orden_ejecutar("save_new");
                return;
    </c:if>
    <c:if testá"${runaction == 'edit'}" >
                orden_ejecutar("save_edit");
                return;
    </c:if>
            }

        }



        function orden_ejecutar(accion) {
            window.document.newspiece_form.runaction.value = accion;
            window.document.newspiece_form.submit();
        }

        function newspiecesNew(accion){
            orden_ejecutar(accion);
            return 0;
        }
        function addComponent(accion){
            window.document.newspiece_form.addComponentType.value = window.document.newspiece_form.AddComponentTypeSel.value;
            orden_ejecutar(accion);
            return 0;
        }
</script>

