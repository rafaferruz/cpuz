<%-- 
    Document   : ComentariosEdit
    Created on : 07-feb-2010, 21:09:39
    Author     : RAFAEL FERRUZ
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<td>

    <fmt:setBundle basename="com.cpuz.multilang.cpuz" var="bundle" scope="page"/>


    <c:if testá"${requestácope.runaction == 'edit'}" >
        <h3 align="center"><fmt:message key="commentsEdition"  bundle="${bundle}"/></h3>
    </c:if>

    <c:if testá"${requestácope.runaction == 'new'}" >
        <h3 align="center"><fmt:message key="commentsMailBox"  bundle="${bundle}"/></h3>
        <p align="center"><fmt:message key="commentsEditFull_Msg1"  bundle="${bundle}"/></p>
        <ul>
            <li><fmt:message key="commentsEditFull_Msg2"  bundle="${bundle}"/></li>
            <li><fmt:message key="commentsEditFull_Msg3"  bundle="${bundle}"/></li>
            <li><fmt:message key="commentsEditFull_Msg4"  bundle="${bundle}"/></li>
            <li><fmt:message key="commentsEditFull_Msg5"  bundle="${bundle}"/></li>
        </ul>
    </c:if>

    <c:if testá"${requestácope.CommentsEditErrorMsg!=null}">
        <label style="color: red">
            <fmt:message key="${CommentsEditErrorMsg}"  bundle="${bundle}"/>
            <br/>
        </label>
    </c:if>
    <c:if testá"${requestácope.CommentsEditSaveOkMsg!=null}">
        <label style="color: black">
            <fmt:message key="${CommentsEditSaveOkMsg}"  bundle="${bundle}"/>
            <br/>
        </label>
    </c:if>


    <form id="comments_form" enctype="multipart/form-data" name="comments_form" method="post" action="ComentariosEdit"   class="zorongo">
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
        <table width="90%" border="1" align="center" cellpadding="0" cellspacing="10" bordercolor="#000000">
            <c:if testá"${runaction == 'edit'}" >
                <tr>
                    <td><fmt:message key="commentIdentifier"  bundle="${bundle}"/>:</td>
                    <td colspan="4"><input name="identificador" type="text" id="identificador" size="5" maxlength="5" disabled value="${recBean.id}">
                </tr>
                <tr>
                    <td><fmt:message key="date"  bundle="${bundle}"/>:</td>
                    <td colspan="4"><label>
                            <fmt:formatDate pattern="dd/MM/yyyy"  var="fechaformat" value="${recBean.fecha}"  />
                            <input name="fecha" type="text" id="fecha" size="10" maxlength="10" value="${fechaformat}" onBlur="ValidarFecha(this)">
                        <fmt:message key="dateformathelp"  bundle="${bundle}"/></label></td>
                </tr>
                <tr>
                    <td><fmt:message key="status"  bundle="${bundle}"/>:</td>
                    <td colspan="4"><table>
                            <tr>
                                <td><input type="radio" name="estádo" value="0"/>
                                <fmt:message key="received"  bundle="${bundle}"/></td>
                                <td><input type="radio" name="estádo"  value="1"/>
                                <fmt:message key="waiting"  bundle="${bundle}"/></td>
                                <td><input type="radio" name="estádo" value="2"/>
                                <fmt:message key="authorized"  bundle="${bundle}"/></td>
                            </tr>

                        </table>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td width="25%"><fmt:message key="scope"  bundle="${bundle}"/>:</td>
                <td colspan="4"><p>
                        <label>
                            <input name="ambito" type="radio" value="1" checked />
                        <fmt:message key="Private"  bundle="${bundle}"/></label>
                        <br />
                        <label>
                            <input name="ambito" type="radio" value="2" />
                        <fmt:message key="Public"  bundle="${bundle}"/></label>
                        <br />
                    </p></td>
            </tr>
            <tr>
                <td><fmt:message key="nameAuthor"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <c:if testá"${runaction == 'new'}" >
                        <input name="nombre" type="text" id="nombre" value="" size="30" maxlength="40" value=""/>
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                        <input name="nombre" type="text" id="nombre" value="${recBean.nombre}" size="30" maxlength="40"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="houseNumber"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <c:if testá"${runaction == 'new'}" >
                        <input name="parcela" type="text" id="parcela" size="8" maxlength="4" value="" />
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                        <input name="parcela" type="text" id="parcela" size="8" maxlength="4" value="${recBean.parcela}" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="email"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <c:if testá"${runaction == 'new'}" >
                        <input name="email" type="text" id="email" size="30" maxlength="60" value="" />
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                        <input name="email" type="text" id="email" size="30" maxlength="60" value="${recBean.email}" />
                    </c:if>
            </tr>

            <tr>
                <td><fmt:message key="publishName"  bundle="${bundle}"/></td>
                <td colspan="4">
                    <label>
                        <input id="publicar" name="publicar" type="radio" value="publicarno" checked />
                    <fmt:message key="publishNameNo"  bundle="${bundle}"/></label>
                    <br>
                    <label>
                        <input id="publicar" name="publicar" type="radio" value="publicarsi" />
                    <fmt:message key="publishNameOk"  bundle="${bundle}"/></label>
                    <br>
                </td>
            </tr>
            <tr>
                <td><fmt:message key="title"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <c:if testá"${runaction == 'new'}" >
                        <input name="titulo" type="text" id="titulo" size="60" maxlength="60" value=""/>
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                        <input name="titulo" type="text" id="titulo" size="60" maxlength="60" value="${recBean.titulo}"/>
                    </c:if>
                </td>
            </tr>

            <tr>
                <td><fmt:message key="comment"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <c:if testá"${runaction == 'new'}" >
                        <textarea name="comentario" id="comentario" cols="60" rows="10" ></textarea>
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                        <textarea name="comentario" id="comentario" cols="60" rows="10" >${recBean.comentario}</textarea>
                    </c:if>
                </td>
            </tr>

            <tr>
                <td><fmt:message key="imagefile"  bundle="${bundle}"/>:</td>
                <td colspan="4">
                    <c:if testá"${runaction == 'new'}" >
                        <input name="ficheroimagen" type="file" id="ficheroimagen" size="30"
                               value=""/>
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                        <input name="ficheroimagen" type="text" id="ficheroimagen" size="30"  maxlength="60"
                               value="${recBean.ficheroImagen}"/>
                    </c:if>
                </td>
            </tr>

            <tr>
                <c:if testá"${runaction == 'new'}" >
                    <td colspan="3">&nbsp;</td>
                    <td align="center"><input type="button" name="cancelar" id="cancelar" value="<fmt:message key="cancel"  bundle="${bundle}"/>"  onclick="window.back();"/></td>
                    <td align="center"><input type="button" name="guardar" id="enviar" value="<fmt:message key="save"  bundle="${bundle}"/>" onClick="valida_formulario();" /></td>
                    </c:if>
                    <c:if testá"${runaction == 'edit'}" >
                    <td colspan="1">&nbsp;</td>
                    <td align="center"><input type="button" name="return" id="return" value="<fmt:message key="return"  bundle="${bundle}"/>"  onclick="window.back()"/></td>
                    <td align="center"><input type="button" name="new" id="new" value="<fmt:message key="new"  bundle="${bundle}"/>"  onclick="valida_formulario();"/></td>
                    <td align="center">
                        <%--
<input type="button" name="eliminar" id="eliminar" value="<fmt:message key="delete"  bundle="${bundle}"/>"  onclick="orden_ejecutar('deleterecord')"/>
                        --%>
                    </td>
                    <td align="center"><input type="button" name="guardar" id="enviar" value="<fmt:message key="save"  bundle="${bundle}"/>" onClick="valida_formulario()" /></td>
                    </c:if>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="4">&nbsp;</td>
            </tr>

        </table>
    </form>
</td>

<c:if testá"${runaction == 'edit'}" >
    <script type="text/javascript">
        window.document.comments_form.estádo[${recBean.estádo}].checked=true;
        window.document.comments_form.ambito[${ recBean.ambito -1}].checked=true;
        if (${ recBean.publicar == 'publicarsi'}) {
            window.document.comments_form.publicar[1].checked=true;
        } else {
            window.document.comments_form.publicar[0].checked=true;
        }
    </script>
</c:if>

<script type="text/javascript">
    function valida_formulario(){
        mensaje=""
        // comprueba si el comentario es publico
        if (document.comments_form.ambito[1].checked==true) {
            //valido nombre, parcela y email
            if (document.comments_form.nombre.value.length==0){
                mensaje = mensaje + "<fmt:message key="needNameAuthorScopePublic"  bundle="${bundle}"/>\n"
            }
            if (document.comments_form.parcela.value.length==0){
                mensaje = mensaje + "<fmt:message key="needHouseNumberAuthorScopePublic"  bundle="${bundle}"/>\n"
            }
            if (document.comments_form.email.value.length==0){
                mensaje = mensaje + "<fmt:message key="needEmailScopePublic"  bundle="${bundle}"/>\n"
            }
        }

        if (document.comments_form.titulo.value.length==0){
            mensaje = mensaje + "<fmt:message key="needcompleteTitle"  bundle="${bundle}"/>\n"
        }
        if (document.comments_form.comentario.value.length==0){
            mensaje = mensaje + "<fmt:message key="needcompleteComment"  bundle="${bundle}"/>\n"
        }
        if (mensaje.length!=0){
            mensaje = "<fmt:message key="founderrors"  bundle="${bundle}"/>\n\n" + mensaje
            alert(mensaje)
            document.comments_form.nombre.focus()
            return 0;
        } else {
            //el formulario se envia
            alert("<fmt:message key="thanksforform"  bundle="${bundle}"/>");


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
            window.document.comments_form.runaction.value = accion;
            window.document.comments_form.submit();
        }

</script>

