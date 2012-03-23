<%-- 
    Document   : DocumentEdit
    Created on : 23-ene-2010, 18:21:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if testá"control.runAction == 'edit'" >
    <h3 align="center"><s:text name="DocumentEdition"/></h3>
</s:if>

<s:if testá"control.runAction == 'new'" >
    <h3 align="center"><s:text name="DocumentMailBox"/></h3>
    <ul>
        <li><s:text name="DocumentEditFull_Msg1"/></li>
        <li><s:text name="DocumentEditFull_Msg2"/></li>
    </ul>
</s:if>
<br/>

<s:actionmessage/>
<s:actionerror/>

<s:form id="document_form" name="document_form" enctype="multipart/form-data"
        method="post" cssClass="form_data" cssStyle="width: 80%;"
        onsubmit="return onSubmitFunction();" >
    <s:if testá"control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
    </s:if>
    <s:if testá"control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveEdit"/>
        <s:hidden name="control.idKey" id="idKey" value="%{dataEdit.id}"/>
        <s:hidden key="dataEdit.repositoryReference" id="repositoryReference"/>
    </s:if>
    <s:if testá"control.runAction == 'new'" >
        <s:set var="readonly" value="true"/>
    </s:if>
    <s:if testá"control.runAction == 'edit'" >
        <s:set var="readonly" value="true"/>
    </s:if>

    <s:textfield key="dataEdit.id"
                 id="identificador"
                 size="5" maxlength="8"
                 readonly="#readonly"/>
    <s:date name="dataEdit.datetime" id="fecha" format="dd/MM/yyyy HH:mm:ss" var="fechaformat" />
    <s:textfield key="dataEdit.datetime" id="fecha"  maxlength="10"
                 value="%{#fechaformat}" onBlur="ValidarFecha(this)"
                 readonly="true"/>

    <s:textfield key="dataEdit.userReference" id="userReference"
                 size="60" maxlength="64"/>
    <s:textfield key="filenameStore" id="filenameStoreuserReference"
                 size="60" readonly="true"/>
    <s:file key="dataEdit.file" id="file"
            size="60"/>
    <s:select list="mapScopes"
              id="scope"
              cssStyle="width:120px" size="4"
              key="dataEdit.scope"/>


    <tr><td><br/><br/></td></tr>

    <tr>
        <td></td>
        <td>
            <table  class="form_data" align="right" >
                <tr>
                    <td align="center"><s:submit type="button" name="return"
                              id="return" value="%{getText('Return')}"
                              onclick="window.back()" theme="simple"/></td>
                        <s:if testá"control.runAction == 'edit'" >
                        <td align="center"><s:submit type="button" name="new"
                                  id="new" value="%{getText('New')}"
                                  onclick="orden_ejecutar('new');" theme="simple"/></td>
                        </s:if>
                    <td align="center"><s:submit type="button" name="guardar"
                              id="enviar" value="%{getText('Save')}"
                              onClick="check_document_form()" theme="simple"/></td>
                </tr>
            </table>
        </td>
    </tr>

</s:form>



<script type="text/javascript">

    var onSubmitAction=true;

    function orden_ejecutar(accion) {
        window.document.document_form.runAction.value = accion;
        window.document.document_form.action = "Document_"+accion+".action";
        window.document.document_form.submit();
    }

    function check_document_form(){
        mensaje=""
        if (document.document_form.userReference.value.length==0){
            mensaje = mensaje + "<s:text name="needcompleteTitle"/>" + "\n"
        }

        if (mensaje.length!=0){
            mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
            alert(mensaje)
            document.document_form.nombre.focus();
            return 0;
        } else {
            //el formulario se envia
            //el formulario se envia
            alert("<s:text name="thanksforform"/>");
            // se ponen selected todas las option del select

    <s:if testá"control.runAction == 'new'" >
                orden_ejecutar("saveNew");
                return;
    </s:if>
    <s:if testá"control.runAction == 'edit'" >
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

