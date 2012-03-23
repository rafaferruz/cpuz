<%-- 
    Document   : NewsCompositionEdit
    Created on : 23-ene-2010, 18:21:25
    Author     : RAFAEL FERRUZ
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%@page errorPage="error.jsp" %>
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if testá"control.runAction == 'edit'" >
    <h3 align="center"><s:text name="NewsCompositionEdition"/></h3>
</s:if>

<s:if testá"control.runAction == 'new'" >
    <h3 align="center"><s:text name="NewsCompositionMailBox"/></h3>
    <ul>
        <li><s:text name="NewsCompositionEditFull_Msg1"/></li>
        <li><s:text name="NewsCompositionEditFull_Msg2"/></li>
    </ul>
</s:if>
<br/>
<s:actionmessage/>
<s:actionerror/>

<s:form id="newscomposition_form" name="newscomposition_form" method="post" cssClass="form_data" cssStyle="width: 80%;" onsubmit="return onSubmitFunction();" >
    <s:if testá"control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
    </s:if>
    <s:if testá"control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveEdit"/>
        <s:hidden name="control.idKey" id="idKey" value="%{dataEdit.id}"/>
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
    <s:hidden name="dataEdit.idNpi" id="idNpi" value="%{dataEdit.idNpi}"/>
    <s:hidden name="dataEdit.orden" id="orden" value="%{dataEdit.orden}"/>
    <s:select list="mapComponentTypes"
              id="ComponentType"
              size="4"
              key="dataEdit.componentType"
              disabled="true"/>
    <s:if testá"dataEdit.componentType=='InfoBlock'" >
        <s:radio key="dataEdit.headerStyle" list="listHeaderStyle" id="headerStyle"/>

        <s:textfield key="dataEdit.headerAlt" id="headerAlt"
                     size="60" maxlength="128"/>
        <s:textarea key="dataEdit.bodyAbstract" id="bodyAbstract"
                    cols="50" rows="4"/>
    </s:if>
    <s:elseif testá"dataEdit.componentType=='Image'" >
        <s:radio name="dataEdit.headerStyle" key="ImagePosition"
                 list="listImagePosition" id="imagePosition"/>
        <s:textfield name="dataEdit.headerAlt" key="userReference"
                     id="headerAlt" size="60" maxlength="128"/>
        <s:hidden key="dataEdit.bodyAbstract"/>
        <s:textfield key="dataEdit.imageHigh" id="imageHigh"
                     size="5" maxlength="3"/>
        <s:textfield key="dataEdit.imageWidth" id="imageWidth"
                     size="60" maxlength="256"/>

    </s:elseif>

    <s:textfield key="dataEdit.linkedElement" id="linkedElement"
                 size="60" maxlength="256"/>

    <tr>
        <td></td>
        <td>
            <table  class="form_data" align="right" >
                <tr>
                    <td align="center"><s:submit type="button" name="return"
                              id="return" value="%{getText('Return')}"
                              onclick="window.back()" theme="simple"/></td>
                        <%--                        <s:if testá"control.runAction == 'edit'" >
                                                <td align="center"><s:submit type="button" name="new"
                                                          id="new" value="%{getText('New')}"
                                                          onclick="orden_ejecutar('new');" theme="simple"/></td>
                                                </s:if>
                        --%>
                    <td align="center"><s:submit type="button" name="guardar"
                              id="enviar" value="%{getText('Save')}"
                              onClick="check_form()" theme="simple"/></td>
                </tr>
            </table>
        </td>
    </tr>


</s:form>


<script type="text/javascript">
    function check_form(){
        mensaje=""
        if (document.newscomposition_form.headerAlt.value.length==0){
            mensaje = mensaje + "<s:text name="needcompleteTitle"/>" + "\n"
        }
        if (document.newscomposition_form.bodyAbstract.value.length==0){
            mensaje = mensaje + "<s:text name="needcompleteBody"/>" + "\n"
        }
        if (document.newscomposition_form.bodyAbstract.value.length>256){
            mensaje = mensaje + "<s:text name="BodyTooLong"/>" + "\n"
        }
        if (mensaje.length!=0){
            mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
            alert(mensaje)
            document.newspiece_form.headerAlt.focus();
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

        function orden_ejecutar(accion) {
            window.document.newscomposition_form.runAction.value = accion;
            window.document.newscomposition_form.submit();
        }

</script>

