<%-- 
    Document   : BugsEdit
    Created on : 23-ene-2010, 18:21:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="control.runAction == 'edit'" >
    <h3 align="center"><s:text name="BugEdition"/></h3>
</s:if>

<s:if test="control.runAction == 'new'" >
    <h3 align="center"><s:text name="BugMailBox"/></h3>
    <ul>
        <li><s:text name="BugEditFull_Msg1"/></li>
        <li><s:text name="BugEditFull_Msg2"/></li>
    </ul>
</s:if>
<br/>

<s:actionmessage/>
<s:actionerror/>

<s:form id="bug_form" name="bug_form" method="post" cssClass="form_data" cssStyle="width: 80%;" onsubmit="return onSubmitFunction();" >
    <s:if test="control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveEdit"/>
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
                 size="5" maxlength="8"
                 readonly="#readonly"/>
    <s:date name="dataEdit.datetime" id="fecha" format="dd/MM/yyyy hh:mm:ss" var="fechaformat" />
    <s:textfield key="dataEdit.datetime" id="fecha"  maxlength="10"
                 value="%{#fechaformat}" onBlur="ValidarFecha(this)"
                 readonly="true"/>

    <s:select list="mapStatus"
              id="status"
              cssStyle="width:120px" size="4"
              key="dataEdit.status"/>

    <s:select list="{1,2,3,4,5,6,7,8,9,10}"
              id="priority"
              cssStyle="width:120px" size="4"
              key="dataEdit.priority"/>

    <s:select list="listTypes"
              id="type"
              cssStyle="width:120px" size="4"
              key="dataEdit.type"/>

    <s:textfield key="dataEdit.application" id="application"
                 size="20" maxlength="20"/>

    <s:textfield key="dataEdit.header" id="header"
                 size="60" maxlength="128"/>
    <s:textarea key="dataEdit.body" id="body"
                cols="50" rows="10"/>

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
                        <td align="center"><s:submit type="button" name="new"
                                  id="new" value="%{getText('New')}"
                                  onclick="orden_ejecutar('new');" theme="simple"/></td>
                        </s:if>
                    <td align="center"><s:submit type="button" name="guardar"
                              id="enviar" value="%{getText('Save')}"
                              onClick="check_bug_form()" theme="simple"/></td>
                </tr>
            </table>
        </td>
    </tr>

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

    function orden_ejecutar(accion) {
        window.document.bug_form.runAction.value = accion;
        window.document.bug_form.action = "Bug_"+accion+".action";
        window.document.bug_form.submit();
    }

    function check_bug_form(){
        mensaje=""
        if (document.bug_form.header.value.length==0){
            mensaje = mensaje + "<s:text name="needcompleteTitle"/>" + "\n"
        }
        if (document.bug_form.body.value.length==0){
            mensaje = mensaje + "<s:text name="needcompleteBody"/>" + "\n"
        }

        if (mensaje.length!=0){
            mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
            alert(mensaje)
            document.bug_form.nombre.focus();
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

</script>


