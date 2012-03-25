<%-- 
    Document   : NewsPiecesEdit
    Created on : 23-ene-2010, 18:21:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="control.runAction == 'edit'" >
    <h3 align="center"><s:text name="NewsPieceEdition"/></h3>
</s:if>

<s:if test="control.runAction == 'new'" >
    <h3 align="center"><s:text name="NewsPieceMailBox"/></h3>
    <ul>
        <li><s:text name="NewsPieceEditFull_Msg1"/></li>
        <li><s:text name="NewsPieceEditFull_Msg2"/></li>
    </ul>
</s:if>
<br/>

<s:actionmessage/>
<s:actionerror/>

<s:form id="newspiece_form" name="newspiece_form" method="post" 
        cssClass="form_data" cssStyle="width: 95%;"
        onsubmit="return onSubmitFunction();" >
    <tr>
        <td style="width: 20%;" ><br/><br/></td>
        <td style="width: 80%;" ><br/><br/></td>
    </tr>

    <s:if test="control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveEdit"/>
        <s:hidden name="control.idKey" id="idKey" value="%{dataEdit.id}"/>
        <s:hidden name="addComponetType" id="addComponetType" value="%{addComponentType}"/>
    </s:if>

    <s:if test="control.runAction == 'new'" >
        <s:set var="readonly" value="true"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:set var="readonly" value="true"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:textfield key="dataEdit.id"
                     id="identificador"
                     size="5" maxlength="8"
                     readonly="#readonly"/>
        <s:date name="dataEdit.datetime" id="fecha" format="dd/MM/yyyy hh:mm:ss" var="fechaformat" />
        <s:textfield key="dataEdit.datetime" id="fecha"  maxlength="10"
                     value="%{#fechaformat}" onBlur="Validardate(this)"
                     readonly="true"/>

        <s:radio key="dataEdit.status" list="mapStatus" id="status"/>
    </s:if>

    <s:combobox list="sectionList"
                listKey="id" listValue="name"
                id="section"
                size="8"
                key="dataEdit.section"  required="true" disabled="disabled" />
    <s:textfield key="dataEdit.description" id="description"
                 size="60" maxlength="128"/>
    <s:textfield key="dataEdit.showParameters" id="showParameters"
                 size="60" maxlength="128"/>
    <s:radio key="dataEdit.scope" list="mapScopes" id="scope"/>
    <s:radio key="dataEdit.access" list="mapAccess" id="access"/>
    <tr><td colspan="2">
            <%@include  file="/WEB-INF/views/EditTableFootButtons.jspf" %>
        </td>
    </tr>


    <s:if test="control.runAction == 'edit' && controlCompList.runAction=='list'" >
        <%-- Se incluye una tabla con los componentes asociados a la noticia    --%>
        <s:i18n name="com.cpuz.st2.actions.NewsCompositionAction">
            <tr><td colspan="2">
                    <table style="width: 100%; ">
                        <tr>
                            <td>
                                <h3 align="center"><s:text name="NewsCompositionList"/></h3>
                            </td>
                        </tr>
                    </table>
                    <table style="width: 100%; ">
                        <s:hidden name="controlCompList.runAction" id="controlCompList_runAction"/>
                        <s:hidden name="controlCompList.idKey" id="controlCompList_idKey" />
                        <s:hidden name="controlCompList.recStart" id="recStart"/>
                        <s:hidden name="controlCompList.recChunk" id="recChunk"/>
                        <s:hidden name="controlCompList.recCount" id="recCount"/>

                        <%-- Requirement codes: E5-2 --%>
                        <s:if test="#session.userCategory == 2">
                            <tr>
                                <th><s:text name="ID" /></th>
                                <th><s:text name="ComponentType" /></th>
                                <th><s:text name="HeaderAlt" /></th>
                                <th><s:text name="HeaderStyle" /></th>
                                <th><s:text name="BodyAbstract" /></th>
                                <th><s:text name="Link" /></th>
                                <th><s:text name="action"/></th>
                                <th><s:text name="sel"/></th>
                            </tr>
                        </s:if>
                        <!-- column data -->
                        <s:if test="controlCompList.recCount>0">

                            <s:iterator var="row" value="dataCompList" status="rowStatus">
                                <s:if test="#rowStatus.odd == true">
                                    <s:set var="trClass" value="getText('trClassOddRows')" />
                                </s:if>
                                <s:else>
                                    <s:set var="trClass" value="getText('trClassEvenRows')"/>
                                </s:else>
                                <tr class="<s:property value="#trClass"/>">
                                    <%-- Requirement codes: E5-2 --%>
                                    <s:if test="#session.userCategory == 2">
                                        <td align="center"><s:property value="#row.id"/></td>
                                        <td align="center"><s:property value="#row.componentType"/></td>
                                        <td align="left"><s:property value="#row.headerAlt"/></td>
                                        <td align="center"><s:property value="#row.headerStyle"/></td>
                                        <s:if test="#row.componentType =='InfoBlock'" >
                                            <td align="left"><s:property value="#row.bodyAbstract"/></td>
                                        </s:if>
                                        <s:if test="#row.componentType =='Image'" >
                                            <td align="#row.headerStyle">
                                                <img src="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/images/<s:property value="%{#row.bodyAbstract}"/>"
                                                     alt="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/images/<s:property value="%{#row.bodyAbstract}"/>"
                                                     height="90" width="120"/>
                                            </td>
                                        </s:if>
                                        <s:if test="#row.componentType =='Document'" >
                                            <td>
                                                <a href="<s:property value="%{#application.dirHomeResources}"/>/../CPUZ/documents/<s:property value="%{#row.bodyAbstract}"/>" target="_blank" >
                                                    See document</a>
                                            </td>
                                        </s:if>
                                        <td align="left"><s:property value="#row.linkedElement"/></td>
                                    </s:if>
                                    <%-- Requirement codes: E5-2 --%>
                                    <s:if test="#session.userCategory == 2">
                                        <td align="center"><s:submit theme="simple" type="button" name="edit" id="edit" value="%{getText('Edit')}"   onclick="newsComposition_edit('%{#row.id}')"/></td>
                                        <td align="center"><s:checkbox theme="simple" name="selec1Comp" fieldValue="%{#row.id}"/></td>
                                    </s:if>
                                </tr>
                            </s:iterator>

                        </s:if>
                    </table>
                    <table style="width: 100%; ">
                        <tr><td>
                                <table style="width: 100%; ">
                                    <tr><td>
                                            <%--@include  file="/WEB-INF/views/OrderTableFootButtons.jspf" --%>
                                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="5" class="data_table">
                                                <tr>
                                                    <td align="left">
                                                        <s:submit theme="simple" type="submit" value="%{getText('Top')}"
                                                                  name="nav_top" onclick="DoNavigation('nav_top')"/>
                                                        <s:submit theme="simple" type="submit" value="%{getText('Up')}"
                                                                  name="nav_up" onclick="DoNavigation('nav_up')"/>
                                                        <s:submit theme="simple" type="submit" value="%{getText('Down')}"
                                                                  name="nav_down" onclick="DoNavigation('nav_down')"/>
                                                        <s:submit theme="simple" type="submit" value="%{getText('Bottom')}"
                                                                  name="nav_bottom" onclick="DoNavigation('nav_bottom')"/>
                                                    </td>
                                                    <%-- Requirement codes: E5-2 --%>
                                                    <s:set var="confirm" value="getText('ConfirmDeleteQuestáon')"/>
                                                    <td align="right">
                                                        <s:submit theme="simple" type="button" name="delete" id="delete"
                                                                  value="%{getText('Delete')}"
                                                                  onclick="if (confirm('%{#confirm}')) DoNavigation('delete')"/>
                                                    </td>
                                                </tr>
                                            </table>

                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                    <%--
                    </s:form>
                    --%>


                    <script type="text/javascript">
                        function newsComposition_orden_ejecutar(accion) {
                            window.document.newspiece_form.runAction.value = accion;
                            window.document.newspiece_form.action = "NewsPiece_"+accion+".action";
                            window.document.newspiece_form.submit();
                            return 0;
                        }

                        function newsComposition_edit(id) {
                            window.document.newspiece_form.controlCompList_idKey.value = id;
                            newsComposition_orden_ejecutar('editComposition');
                            return 0;
                        }
                    </script>

                </td>
            </tr>
        </s:i18n>
        <%-- Al pie de la tabla de componentes, se presentan las acciones a realizar
        para incluir nuevas líneas de composición
        --%>
        <tr>
            <td colspan="2">
                <table align="right"><tr>
                        <td>
                            <s:label key="AddComponentType" theme="simple"/>
                        </td>
                        <td>
                            <s:select list="mapComponentTypes"
                                      id="AddComponentType"
                                      size="1"
                                      key="addComponentType"
                                      theme="simple"/>
                        </td><td>
                            <s:submit theme="simple" type="button" name="addcomponent"
                                      id="addcomponent" value="%{getText('AddComponent')}"
                                      onclick="orden_ejecutar('addComponent')"/>
                        </td></tr></table>
            </td>
        </tr>
    </s:if>

    <%-- Se muestáa una lista de componentes disponibles del tipo seleccionado --%>
    <s:hidden name="controlComponentType.runAction" id="controlComponentType_runAction"/>
    <s:hidden name="controlComponentType.idKey" id="controlComponentType_idKey" />
    <s:hidden name="controlComponentType.recStart" id="recStart"/>
    <s:hidden name="controlComponentType.recChunk" id="recChunk"/>
    <s:hidden name="controlComponentType.recCount" id="recCount"/>
    <tr>
        <td colspan="2">
            <s:if test="controlComponentType.runAction == 'list'">
                <s:if test="addComponentType=='InfoBlock'">
                    <%-- Se incluye una tabla con los componentes de tipo InfoBlock --%>
                    <s:i18n name="com.cpuz.st2.actions.InfoBlockAction">
                        <%@include  file="/WEB-INF/views/InfoBlocksIncludeList.jsp" %>
                    </s:i18n>
                </s:if>
                <s:if test="addComponentType=='Image'">
                    <%-- Se incluye una tabla con los componentes de tipo Image --%>
                    <s:i18n name="com.cpuz.st2.actions.ImageAction">
                        <%@include  file="/WEB-INF/views/ImagesIncludeList.jsp" %>
                    </s:i18n>
                </s:if>
                <s:if test="addComponentType=='Document'">
                    <%-- Se incluye una tabla con los componentes de tipo Document --%>
                    <s:i18n name="com.cpuz.st2.actions.DocumentsAction">
                        <%@include  file="/WEB-INF/views/DocumentsIncludeList.jsp" %>
                    </s:i18n>
                </s:if>
            </s:if>

        </td>
    </tr>

    <%-- Se muestáa una ventana de edición de Línea de Composición --%>
    <s:if test="control.runAction == 'edit' && controlCompList.runAction=='edit'" >
        <%-- Se incluye una tabla con los componentes asociados a la noticia    --%>
        <s:i18n name="com.cpuz.st2.actions.NewsCompositionAction">

            <tr><td colspan="2">
                    <table style="width: 100%; ">
                        <tr>
                            <td>
                                <h3 align="center"><s:text name="NewsCompositionEdit"/></h3>
                            </td>
                        </tr>
                    </table>
                    <table style="width: 100%; ">
                        <tr><td>
                                <%--
                                <s:actionmessage/>
                                <s:actionerror/>

<s:form id="newspiece_form" name="newspiece_form" method="post" cssClass="data_table">
                                --%>
                                <s:hidden name="controlCompList.runAction" id="runAction"/>
                                <s:hidden name="controlCompList.idKey" id="idKey" />
                                <s:hidden name="controlCompList.recStart" id="recStart"/>
                                <s:hidden name="controlCompList.recChunk" id="recChunk"/>
                                <s:hidden name="controlCompList.recCount" id="recCount"/>

                                <s:textfield key="dataCompEdit.id"
                                             id="dataCompEdit_id"
                                             size="5" maxlength="8"
                                             readonly="true"/>
                                <s:hidden name="dataCompEdit.idNpi" id="dataCompEdit_idNpi"/>
                                <s:hidden name="dataCompEdit.idComponent" id="dataCompEdit_idComponent"/>
                                <s:hidden name="dataCompEdit.order" id="dataCompEdit.order" />
                                <s:select list="mapComponentTypes"
                                          id="AddComponentType"
                                          size="1"
                                          label="dataCompEdit.componentType"
                                          value="dataCompEdit.componentType"
                                          disabled="true"/>
                                <s:hidden name="dataCompEdit.componentType" id="dataCompEdit.componentType" />


                                <s:if test="dataCompEdit.componentType=='InfoBlock'" >
                                    <s:select list="listTypes"
                                              id="dataCompEdit_listTypes"
                                              size="1"
                                              label="dataCompEdit.headerStyle"
                                              name="dataCompEdit.headerStyle"
                                              value="dataCompEdit.headerStyle"/>
                                    <s:textfield key="dataCompEdit.headerAlt"
                                                 id="dataCompEdit_headerAlt"
                                                 size="60" maxlength="128"/>
                                    <s:textarea key="dataCompEdit.bodyAbstract"
                                                id="dataCompEdit_bodyAbstract" cols="50"
                                                rows="4"/>

                                </s:if>
                                <s:elseif test="dataCompEdit.componentType=='Image'" >
                                    <s:select list="listImagePositions"
                                              id="dataCompEdit_listImagePositionsTypes"
                                              size="1"
                                              name="dataCompEdit.headerStyle"
                                              label="dataCompEdit.imagePosition"
                                              />
                                    <s:textfield name="dataCompEdit.headerAlt"
                                                 label="dataCompEdit.userReference"
                                                 id="dataCompEdit_headerAlt"
                                                 size="60" maxlength="128"/>
                                    <s:hidden name="dataCompEdit.bodyAbstract" id="dataCompEdit.bodyAbstract" />
                                    <s:textfield name="dataCompEdit.imageHigh"
                                                 label="dataCompEdit.imageHigh"
                                                 id="dataCompEdit_imageHigh"
                                                 size="5" maxlength="3"/>
                                    <s:textfield name="dataCompEdit.imageWidth"
                                                 label="dataCompEdit.imageWidth"
                                                 id="dataCompEdit_imageWidth"
                                                 size="5" maxlength="3"/>

                                </s:elseif>
                                <s:elseif test="dataCompEdit.componentType=='Document'" >
                                    <s:select list="listTypes"
                                              id="dataCompEdit_listTypes"
                                              size="1"
                                              label="dataCompEdit.headerStyle"
                                              name="dataCompEdit.headerStyle"
                                              value="dataCompEdit.headerStyle"/>
                                    <s:textfield name="dataCompEdit.headerAlt"
                                                 label="dataCompEdit.userReference"
                                                 id="dataCompEdit_headerAlt"
                                                 size="60" maxlength="128"/>
                                    <s:textarea key="dataCompEdit.bodyAbstract"
                                                id="dataCompEdit_bodyAbstract" cols="50"
                                                rows="4"/>
                                </s:elseif>

                                <s:textfield name="dataCompEdit.linkedElement"
                                             label="dataCompEdit.linkedElement"
                                             id="dataCompEdit_linkedElement"
                                             size="60" maxlength="128"/>
                            </td>
                        </tr>
                    </table>

                    <table  class="form_data" align="right" >
                        <tr>
                            <td align="center"><s:submit type="button" name="return"
                                      id="return" value="%{getText('Return')}"
                                      onclick="window.back()" theme="simple"/></td>
                            <td align="center"><s:submit type="button" name="guardar"
                                      id="enviar" value="%{getText('Save')}"
                                      onClick="checkCompEdit_form()" theme="simple"/></td>
                        </tr>
                    </table>
                </td>
            </tr>

        </s:i18n>
    </s:if>

</s:form>

<script type="text/javascript">

    var onSubmitAction=true;
    function onSubmitFunction(){
        if (onSubmitAction==false){
            onSubmitAction=true;
            return false;
        }
        onSubmitAction=true;
        return false;
    }

    function check_form(){
        mensaje=""
        if (document.newspiece_form.description.value.length==0){
            mensaje = mensaje + "<s:text name="needcompleteTitle"/>" + "\n"
        }
        if (document.newspiece_form.section.value.length==0){
            mensaje = mensaje + "<s:text name="needSelectedSection"/>" + "\n"
        }
        if (mensaje.length!=0){
            mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
            alert(mensaje)
            document.newspiece_form.description.focus();
            return 0;
        } else {
            //el formulario se envia
            //el formulario se envia
            alert("<s:text name="thanksforform"/>");
            // se ponen selected todas las option del select

    <s:if test="control.runAction == 'new'" >
                orden_ejecutar("saveNew");
                return;
    </s:if>
    <s:if test="control.runAction == 'edit'" >
                orden_ejecutar("saveEdit");
                return;
    </s:if>

            }
        }

        function checkCompEdit_form(){
            mensaje=""
            orden_ejecutar("saveEditComposition");
            return;
        }

        function orden_ejecutar(accion) {
            window.document.newspiece_form.runAction.value = accion;
            window.document.newspiece_form.action = "NewsPiece_"+accion+".action";
            window.document.newspiece_form.submit();
        }

        function DoNavigation(nav_rule) {
            if (nav_rule=='nav_top'){
                orden_ejecutar('CompNavTop');
            } else if (nav_rule=='nav_up'){
                orden_ejecutar('CompNavUp');
            } else if (nav_rule=='nav_down'){
                orden_ejecutar('CompNavDown');
            } else if (nav_rule=='nav_bottom'){
                orden_ejecutar('CompNavBottom');
            } else if (nav_rule=='delete'){
                orden_ejecutar('CompDelete');
            }
            return 0;
        }

        function addComponent(accion){
            alert(window.document.addcomponenttype_form.AddComponentTypeSel.value);
            window.document.newspiece_form.addComponentType.value = window.document.addcomponenttype_form.AddComponentTypeSel.value;
            orden_ejecutar(accion);
            return 0;
        }
        function orden_ejecutarComponentType(accion) {
            window.document.newspiece_form.controlComponentType_runAction.value = accion;
            window.document.newspiece_form.action = "NewsPiece_NavigationComponentType.action";
            window.document.newspiece_form.submit();
        }

        function DoNavigationComponentType(nav_rule) {
            orden_ejecutarComponentType(nav_rule);
            return 0;
        }

</script>

