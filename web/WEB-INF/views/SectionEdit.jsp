<%--
    Document   : SectionEdit
    Created on : 07-nov-2010, 21:09:39
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="control.runAction == 'edit'" >
    <h3 align="center"><s:text name="SectionEdition"/></h3>
</s:if>

<s:if test="control.runAction == 'new'" >
    <h3 align="center"><s:text name="SectionMailBox"/></h3>
    <ul>
        <li><s:text name="SectionEditFull_Msg1"/></li>
        <li><s:text name="SectionEditFull_Msg2"/></li>
        <li><s:text name="SectionEditFull_Msg3"/></li>
    </ul>
</s:if>
<br/>

<s:actionmessage/>
<s:actionerror/>

<s:form id="sections_form" name="sections_form" method="post" cssClass="form_data" cssStyle="width: 80%;" onsubmit="return onSubmitFunction();" >
    <s:if test="control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveEdit"/>
        <s:hidden name="control.idKey" id="idKey" value="%{dataEdit.id}"/>
    </s:if>
    <s:if test="control.runAction == 'new'" >
        <s:set var="readonly" value="false"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:set var="readonly" value="true"/>
    </s:if>
    <s:textfield key="dataEdit.id"
                 id="identificador"
                 size="12" maxlength="16"
                 readonly="#readonly"/>
    <s:textfield key="dataEdit.name" id="name"
                 size="30" maxlength="40"/>
    <%-- Se muestáan dos select con los roles autorizados y todos los roles disponobles            --%>
    <tr>
        <td>
            <s:label value="%{getText('RolesAuthorization')}" theme="simple"/>
        </td>
        <td>
            <table  class="form_data">
                <tr>
                    <td>
                        <s:label for="ownedRoles" value="%{getText('AuthorizedRoles')}" theme="simple"/>
                        <s:select labelposition="top" list="authRolesList"
                                  id="ownedRoles"
                                  cssStyle="width:120px" size="4" multiple="true"
                                  name="authRolesSel" theme="simple"/>
                    </td>
                    <td align="center">
                        <table border="0" class="form_data">
                            <tr><td width="120px">
                                    <s:submit type="button" value=" < " name="nav_prev"
                                              cssStyle="text-align: center;"
                                              onclick="pass_available_owned();"
                                              theme="simple"/>
                                </td></tr>
                            <tr><td>
                                    <s:submit type="button"  value=" > " name="nav_next"
                                              cssStyle="text-align: center;"
                                              onclick="pass_owned_available();"
                                              theme="simple"/>
                                </td></tr>
                        </table>
                    </td>
                    <td>
                        <s:label for="availableRoles" value="%{getText('AvailableRoles')}" theme="simple"/>
                        <s:select labelposition="top"
                                  list="availableRolesList"
                                  listKey="role" listValue="role"
                                  id="availableRoles"
                                  cssStyle="width:120px" size="4" multiple="true"
                                  name="availableRolesSel" theme="simple"/>
                    </td>
                </tr>
            </table>
        </td>
    <s:textfield key="dataEdit.group" id="group"
                 size="8" maxlength="8"/>
    </tr>
    <tr><td><br/><br/></td></tr>

    <tr>
        <td></td>
        <td>
            <table  class="form_data" align="right" >
                <tr>
                    <td align="center"><s:submit type="button" name="return"
                              id="return" value="%{getText('Return')}"
                              onclick="window.back()" theme="simple"/></td>
                        <s:if test="control.runAction == 'edit'" >
                        <td align="center"><s:submit type="button" name="newButton"
                                  id="newButton" value="%{getText('New')}"
                                  onclick="orden_ejecutar('new');" theme="simple"/></td>
                        </s:if>
                    <td align="center"><s:submit type="button" name="guardar"
                              id="enviar" value="%{getText('Save')}"
                              onClick="check_sections_form()" theme="simple"/></td>
                </tr>
            </table>
        </td>
    </tr>
</s:form>

<script type="text/javascript">
    var onSubmitAction=true;
    function orden_ejecutar(accion) {
        window.document.sections_form.runAction.value = accion;
        window.document.sections_form.action = "Section_"+accion+".action";
        window.document.sections_form.submit();
    }
    function pass_available_owned(){
        for (i=0;i<window.document.sections_form.availableRoles.length;i++){
            if (window.document.sections_form.availableRoles.options[i].selected==true) {
                window.document.sections_form.ownedRoles.options[window.document.sections_form.ownedRoles.length] = new Option(window.document.sections_form.availableRoles.options[i].value, window.document.sections_form.availableRoles.options[i].value, false,false)
                window.document.sections_form.availableRoles.options[i]=null;
                i--;
            }
        }
        onSubmitAction=false;
        return 0;
    }
    function pass_owned_available(){
        for (i=0;i<window.document.sections_form.ownedRoles.length;i++){
            if (window.document.sections_form.ownedRoles.options[i].selected==true) {
                window.document.sections_form.availableRoles.options[window.document.sections_form.availableRoles.length] = new Option(window.document.sections_form.ownedRoles.options[i].value, window.document.sections_form.ownedRoles.options[i].value, false,false)
                window.document.sections_form.ownedRoles.options[i]=null;
                i--;
            }
        }
        onSubmitAction=false;
        return 0;
    }

    function check_sections_form(){
        mensaje=""
        if (document.sections_form.identificador.value.length<3){
            mensaje = mensaje + "<s:text name="needSectionLongMin"/>" + "\n"
        }
        if (document.sections_form.name.value.length<1){
            mensaje = mensaje + "<s:text name="needNameSectionLongMin"/>" + "\n"
        }

        if (mensaje.length!=0){
            mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
            alert(mensaje);
            document.sections_form.name.focus();
            return 0;
        } else {
            //el formulario se envia
            //el formulario se envia
            alert("<s:text name="thanksforform"/>");
            // se ponen selected todas las option del select
            for (i=0;i<window.document.sections_form.ownedRoles.length;i++){
                window.document.sections_form.ownedRoles.options[i].selected=true;
            }

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
        function onSubmitFunction(){
            if (onSubmitAction==false){
                onSubmitAction=true;
                return false;
            }
            onSubmitAction=true;
            return false;
        }
</script>



