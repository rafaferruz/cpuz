<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="control.runAction == 'edit'" >
    <h3 align="center"><s:text name="RoleEdition"/></h3>
</s:if>

<s:if test="control.runAction == 'new'" >
    <h3 align="center"><s:text name="RoleMailBox"/></h3>
    <ul>
        <li><s:text name="RoleEditFull_Msg1"/></li>
    </ul>
</s:if>
<br/>

<s:actionmessage/>
<s:actionerror cssStyle="color: red;"/>


<s:form id="roles_form" name="roles_form" method="post" cssClass="form_data" cssStyle="width: 80%;" onsubmit="return onSubmitFunction();" >
    <s:if test="control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
        <s:hidden name="control.idKey" id="idKey" value="%{dataEdit.id}"/>
    </s:if>
    <s:if test="control.runAction == 'new'" >
        <s:set var="readonly" value="true"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:set var="readonly" value="true"/>
    </s:if>
    <s:textfield key="dataEdit.id"
                 id="identificador"
                 size="5" maxlength="5"
                 readonly="#readonly"/>
    <s:textfield key="dataEdit.role" id="role"
                 size="30" maxlength="40"/>
    <s:textfield key="dataEdit.description" id="description"
                 size="60" maxlength="80"/>
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
                              onClick="check_roles_form()" theme="simple"/></td>
                </tr>
            </table>
        </td>
    </tr>
</s:form>

<script type="text/javascript">
    var onSubmitAction=true;
    function orden_ejecutar(accion) {
        window.document.roles_form.runAction.value = accion;
        window.document.roles_form.action = "Role_"+accion+".action";
        window.document.roles_form.submit();
    }

    function check_roles_form(){
        mensaje=""
        if (document.roles_form.role.value.length<1){
            mensaje = mensaje + "<s:text name="needRoleLongMin"/>" + "\n"
        }

        if (mensaje.length!=0){
            mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
            alert(mensaje)
            document.roles_form.name.focus();
            return 0;
        } else {
            //el formulario se envia
            //el formulario se envia
            alert("<s:text name="thanksforform"/>");

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


