<%-- 
    Document   : NewsCompositionEdit
    Created on : 23-ene-2010, 18:21:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>


<c:if test="${resquestScope.runaction == 'edit'}" >
    <h3 align="center"><fmt:message key="newsCompositionsEdition"  bundle="${bundle}"/></h3>
</c:if>

<c:if test="${resquestScope.runaction == 'new'}" >
    <h3 align="center"><fmt:message key="newscompositionsMailBox"  bundle="${bundle}"/></h3>
    <p align="center"><fmt:message key="newscompositionsEditFull_Msg1"  bundle="${bundle}"/></p>
    <ul>
        <li><fmt:message key="newscompositionsEditFull_Msg2"  bundle="${bundle}"/></li>
        <li><fmt:message key="newscompositionsEditFull_Msg3"  bundle="${bundle}"/></li>
    </ul>
</c:if>

<c:if test="${resquestScope.NewsCompositionsEditErrorMsg!=null}">
    <label style="color: red">
        <fmt:message key="${NewsCompositionsEditErrorMsg}"  bundle="${bundle}"/>
        <br/>
    </label>
</c:if>
<c:if test="${resquestScope.NewsCompositionsEditSaveOkMsg!=null}">
    <label style="color: black">
        <fmt:message key="${NewsCompositionsEditSaveOkMsg}"  bundle="${bundle}"/>
        <br/>
    </label>
</c:if>

<form id="newscomposition_form" name="newscomposition_form" method="post" action="NewsPiecesEdit">
    <c:if test="${resquestScope.runaction == 'new'}" >
        <input name="runaction" type="hidden" id="runaction" value="save_new">
    </c:if>
    <c:if test="${resquestScope.runaction == 'edit'}" >
        <input name="runaction" type="hidden" id="runaction" value="save_edit">
        <input name="id_disabled" type="hidden" id="id_disabled" value="${newsCompositionBean.idNpi}">
        <input name="id_disabled_composition" type="hidden" id="id_disabled_composition" value="${newsCompositionBean.id}">
    </c:if>
    <input name="recStart" type="hidden" id="recStart" value="${resquestScope.recStart}">
    <input name="recChunk" type="hidden" id="recChunk" value="${resquestScope.recChunk}">
    <input name="recCount" type="hidden" id="recCount" value="${resquestScope.recCount}">
    <table width="90%" border="1" align="center" cellpadding="0" cellspacing="10"  class="form_data">
        <c:if test="${resquestScope.runaction == 'edit'}" >
            <tr>
                <td><fmt:message key="newscompositionidentifier"  bundle="${bundle}"/>:</td>
                <td >
                    <input  name="idComposition" type="text" id="idComposition" disabled  size="5" value="${newsCompositionBean.id}"/>
                    <input name="idNpi" type="hidden" id="idNpi" value="${newsCompositionBean.idNpi}">
                    <input name="orden" type="hidden" id="orden" value="${newsCompositionBean.order}">
            </tr>
            <tr>
                <td><fmt:message key="ComponentType"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <select  disabled id="componentType" name="componentType">
                        <option value="InfoBlock">InfoBlock</option>
                        <option value="Image">Image</option>
                        <option value="Document">Document</option>
                        <option value="News">News</option>
                    </select>
                </td>
            </tr>
        </c:if>
        <c:choose>
            <c:when test="${newsCompositionBean.componentType=='InfoBlock'}" >
                <tr>
                    <td><fmt:message key="HeaderStyle"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <select name="headerStyle" size="1">
                            <option selected ><fmt:message key="title"  bundle="${bundle}"/></option>
                            <option ><fmt:message key="subtitle"  bundle="${bundle}"/></option>
                            <option ><fmt:message key="remarked"  bundle="${bundle}"/></option>
                        </select></td>
                </tr>

                <tr>
                    <td><fmt:message key="HeaderAlt"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <c:if test="${runaction == 'new'}" >
                            <input name="headerAlt" type="text" id="headerAlt" size="60" maxlength="128" value=""/>
                        </c:if>
                        <c:if test="${runaction == 'edit'}" >
                            <input name="headerAlt" type="text" id="headerAlt" size="60" maxlength="128" value="${newsCompositionBean.headerAlt}"/>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td><fmt:message key="BodyAbstract"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <c:if test="${runaction == 'new'}" >
                            <textarea name="bodyAbstract" id="bodyAbstract" cols="50" rows="4" ></textarea>
                        </c:if>
                        <c:if test="${runaction == 'edit'}" >
                            <textarea name="bodyAbstract" id="bodyAbstract" cols="50" rows="4"  >${newsCompositionBean.bodyAbstract}</textarea>
                        </c:if>
                    </td>
                </tr>
            </c:when>
            <c:when test="${newsCompositionBean.componentType=='Image'}" >
                <tr>
                    <td><fmt:message key="ImagePosition"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <select name="headerStyle" size="1">
                            <option selected value="left">left</option>
                            <option value="center">center</option>
                            <option value="right">right</option>
                        </select></td>
                </tr>

                <tr>
                    <td><fmt:message key="userReference"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <c:if test="${runaction == 'new'}" >
                            <input name="headerAlt" type="text" id="headerAlt" size="60" maxlength="128" value=""/>
                        </c:if>
                        <c:if test="${runaction == 'edit'}" >
                            <input name="headerAlt" type="text" id="headerAlt" id="headerAlt" size="60" maxlength="128" value="${newsCompositionBean.headerAlt}"/>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td colspan="5">
                        <input name="bodyAbstract" type="hidden" id="bodyAbstract" value="${newsCompositionBean.bodyAbstract}">
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="ImageHigh"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <c:if test="${runaction == 'new'}" >
                            <input name="imageHigh" type="text" id="imageHigh" size="5" maxlength="3" value="0"/>
                        </c:if>
                        <c:if test="${runaction == 'edit'}" >
                            <input name="imageHigh" type="text" id="imageHigh" size="5" maxlength="3" value="${newsCompositionBean.imageHigh}"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="ImageWidth"  bundle="${bundle}"/>:</td>
                    <td colspan="4">
                        <c:if test="${runaction == 'new'}" >
                            <input name="imageWidth" type="text" id="imageWidth" size="5" maxlength="3" value="0"/>
                        </c:if>
                        <c:if test="${runaction == 'edit'}" >
                            <input name="imageWidth" type="text" id="imageWidth" size="5" maxlength="3" value="${newsCompositionBean.imageWidth}"/>
                        </c:if>
                    </td>
                </tr>
            </c:when>

        </c:choose>

        <tr>
            <td><fmt:message key="Link"  bundle="${bundle}"/>:</td>
            <td colspan="4">
                <c:if test="${runaction == 'new'}" >
                    <input name="linkedElement" type="text" id="linkedElement" size="60" maxlength="128" value=""/>
                </c:if>
                <c:if test="${runaction == 'edit'}" >
                    <input name="linkedElement" type="text" id="linkedElement" size="60" maxlength="256" value="${newsCompositionBean.linkedElement}"/>
                </c:if>
            </td>
        </tr>



        <tr>
            <c:if test="${runaction == 'new'}" >
                <td colspan="3">&nbsp;</td>
                <td align="center"><input type="button" name="cancelar" id="cancelar" value="<fmt:message key="cancel"  bundle="${bundle}"/>"  onclick="window.back();"/></td>
                <td align="center"><input type="button" name="guardar" id="enviar" value="<fmt:message key="save"  bundle="${bundle}"/>" onClick="valida_newscomposition();" /></td>
                </c:if>
                <c:if test="${runaction == 'edit'}" >
                <td colspan="3">&nbsp;</td>
                <td align="center"><input type="button" name="return" id="return" value="<fmt:message key="return"  bundle="${bundle}"/>"  onclick="window.back()"/></td>
                <td align="center"><input type="button" name="guardar" id="enviar" value="<fmt:message key="save"  bundle="${bundle}"/>" onClick="valida_newscomposition()" /></td>
                </c:if>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td colspan="4">&nbsp;</td>
        </tr>

    </table>
</form>

<c:if test="${runaction == 'edit'}" >
    <script type="text/javascript">
        <fmt:message var="msg" key="title"  bundle="${bundle}" />
            if ("${newsCompositionBean.headerStyle}" == "${msg}")        {
                window.document.newscomposition_form.headerStyle.selectedIndex=0;
            } else {
        <fmt:message var="msg" key="subtitle"  bundle="${bundle}"/>
                if ("${newsCompositionBean.headerStyle}" == "${msg}")        {
                    window.document.newscomposition_form.headerStyle.selectedIndex=1;
                } else {
        <fmt:message var="msg" key="remarked"  bundle="${bundle}"/>
                    if ("${newsCompositionBean.headerStyle}" == "${msg}")        {
                        window.document.newscomposition_form.headerStyle.selectedIndex=2;
                    }else {
                        if ("${newsCompositionBean.headerStyle}" == "left")        {
                            window.document.newscomposition_form.headerStyle.selectedIndex=0;
                        }else {
                            if ("${newsCompositionBean.headerStyle}" == "center")        {
                                window.document.newscomposition_form.headerStyle.selectedIndex=1;
                            }else {
                                if ("${newsCompositionBean.headerStyle}" == "right")        {
                                    window.document.newscomposition_form.headerStyle.selectedIndex=2;
                                }
                            }
                        }
                    }
                }
            }
            if ("${ newsCompositionBean.componentType}"=="InfoBlock")        {
                window.document.newscomposition_form.componentType.selectedIndex=0;
            } else if ("${ newsCompositionBean.componentType}"=="Image")        {
                window.document.newscomposition_form.componentType.selectedIndex=1;
            } else if ("${ newsCompositionBean.componentType}"=="Document")        {
                window.document.newscomposition_form.componentType.selectedIndex=2;
            } else if ("${ newsCompositionBean.componentType}"=="News")        {
                window.document.newscomposition_form.componentType.selectedIndex=3;
            }
    </script>
</c:if>

<script type="text/javascript">
    function valida_newscomposition(){
        mensaje="";
        document.newscomposition_form.headerAlt.focus();

        if (document.newscomposition_form.headerAlt.value.length==0){
            mensaje = mensaje + "<fmt:message key="needcompleteTitle"  bundle="${bundle}"/>\n";
        }
        if (document.newscomposition_form.bodyAbstract.value.length==0){
            mensaje = mensaje + "<fmt:message key="needcompleteBody"  bundle="${bundle}"/>\n";
        }
        if (document.newscomposition_form.bodyAbstract.value.length>256){
            mensaje = mensaje + "<fmt:message key="BodyTooLong"  bundle="${bundle}"/>\n";
            document.newscomposition_form.bodyAbstract.focus();
        }
        if (mensaje.length!=0){
            mensaje = "<fmt:message key="founderrors"  bundle="${bundle}"/>:\n\n" + mensaje;
            alert(mensaje);
            return 0;
        } else {
            //el newscomposition se envia
    <fmt:message var="msg" key="thanksforform"  bundle="${bundle}"/>
                alert("${msg}");

    <c:if test="${runaction == 'new'}" >
                orden_ejecutar("save_new");
                return;
    </c:if>
    <c:if test="${runaction == 'edit'}" >
                orden_ejecutar("save_composition");
                return;
    </c:if>
            }

        }



        function orden_ejecutar(accion) {
            window.document.newscomposition_form.runaction.value = accion;
            window.document.newscomposition_form.submit();
        }

        function newscompositionsNew(accion){
            orden_ejecutar(accion);
            return 0;
        }
</script>

