<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="control.runAction == 'edit'" >
    <h3 align="center"><s:text name="UserEdition"/></h3>
</s:if>

<s:if test="control.runAction == 'new'" >
    <h3 align="center"><s:text name="UserMailBox"/></h3>
    <ul>
        <li><s:text name="UserEditFull_Msg1"/></li>
        <li><s:text name="UserEditFull_Msg2"/></li>
        <li><s:text name="UserEditFull_Msg3"/></li>
    </ul>
</s:if>
<br/>

<s:actionmessage/>
<s:actionerror/>

<s:form id="users_form" name="users_form" method="post" cssClass="form_data" cssStyle="width: 80%;" onsubmit="return onSubmitFunction();" >
    <s:if test="control.runAction == 'new'" >
        <s:hidden name="control.runAction" id="runAction" value="saveNew"/>
        <s:date name="dataEdit.fecha" id="fecha" format="dd/MM/yyyy" var="fechaformat" />
        <s:hidden name="dataEdit.fecha" id="fecha" value="%{#fechaformat}"/>
    </s:if>
    <s:if test="control.runAction == 'edit'" >
        <s:hidden name="control.runAction" id="runAction" value="saveEdit"/>
        <s:hidden name="control.idKey" id="idKey" value="%{dataEdit.id}"/>
        <s:set var="readonly" value="true"/>
        <s:textfield key="dataEdit.id"
                     id="identificador"
                     size="5" maxlength="8"
                     readonly="true"/>
        <s:date name="dataEdit.fecha" id="fecha" format="dd/MM/yyyy" var="fechaformat" />
        <s:textfield key="dataEdit.fecha" id="fecha"  maxlength="10"
                     value="%{#fechaformat}" onBlur="ValidarFecha(this)"
                     readonly="true"/>
    </s:if>
    <s:if test="control.runAction == 'new'" >
        <s:set var="readonly" value="false"/>
        <s:hidden name="dataEdit.fecha" id="fecha" value="%{dataEdit.fecha}"/>
    </s:if>

    <s:textfield key="dataEdit.user" id="user"
                 size="8" maxlength="8"  readonly="#readonly"/>
    <s:textfield key="dataEdit.nombre" id="nombre"
                 size="30" maxlength="40"/>

    <s:radio key="dataEdit.estado" list="mapStatus" id="status"/>

    <s:radio key="dataEdit.category" list="mapCategories" id="category"/>

    <%-- Se muest�an dos select con los roles autorizados y todos los roles disponobles            --%>
    <tr>
        <td>
            <s:label value="%{getText('RolesAuthorization')}" theme="simple"/>
        </td>
        <td>
            <table  class="form_data">
                <tr>
                    <td>
                        <s:label for="ownedRoles" value="%{getText('AuthorizedRoles')}" theme="simple"/>
                        <s:select labelposition="top" list="userRolesList"
                                  id="ownedRoles"
                                  listKey="role" listValue="role"
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
                                  list="rolesList"
                                  listKey="role" listValue="role"
                                  id="availableRoles"
                                  cssStyle="width:120px" size="4" multiple="true"
                                  name="availableRolesSel" theme="simple"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <s:password key="dataEdit.password" id="password"
                size="8" maxlength="8"/>
    <s:password key="password_again" id="password_again"
                size="8" maxlength="8"/>
    <s:textfield key="dataEdit.email" id="email"
                 size="30" maxlength="60"/>

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
                              onClick="check_users_form()" theme="simple"/></td>
                </tr>
            </table>
        </td>
    </tr>

</s:form>




<script type="text/javascript">
    var onSubmitAction=true;
    function orden_ejecutar(accion) {
        window.document.users_form.runAction.value = accion;
        window.document.users_form.action = "User_"+accion+".action";
        window.document.users_form.submit();
    }
    function pass_available_owned(){
        for (i=0;i<window.document.users_form.availableRoles.length;i++){
            if (window.document.users_form.availableRoles.options[i].selected==true) {
                window.document.users_form.ownedRoles.options[window.document.users_form.ownedRoles.length] = new Option(window.document.users_form.availableRoles.options[i].value, window.document.users_form.availableRoles.options[i].value, false,false)
                window.document.users_form.availableRoles.options[i]=null;
                i--;
            }
        }
        onSubmitAction=false;
        return 0;
    }
    function pass_owned_available(){
        for (i=0;i<window.document.users_form.ownedRoles.length;i++){
            if (window.document.users_form.ownedRoles.options[i].selected==true) {
                window.document.users_form.availableRoles.options[window.document.users_form.availableRoles.length] = new Option(window.document.users_form.ownedRoles.options[i].value, window.document.users_form.ownedRoles.options[i].value, false,false)
                window.document.users_form.ownedRoles.options[i]=null;
                i--;
            }
        }
        onSubmitAction=false;
        return 0;
    }

    function check_users_form(){
        mensaje=""
        if (document.users_form.user.value.length<5){
            mensaje = mensaje + "<s:text name="needUserLongMin"/>" + "\n"
        }
        if (document.users_form.nombre.value.length<5){
            mensaje = mensaje + "<s:text name="needNameUserLongMin"/>" + "\n"
        }
    <s:if test="control.runAction == 'edit'" >
            if (document.users_form.password.value.length!=0 || document.users_form.password.value.length!=0){
    </s:if>
                if (document.users_form.password.value.length<6){
                    mensaje = mensaje + "<s:text name="needPassWordLongMin"/>" + "\n"
                }
                if (document.users_form.password.value != document.users_form.password_again.value){
                    mensaje = mensaje + "<s:text name="needPassWordEquals"/>"+"\n"
                }
    <s:if test="control.runAction == 'edit'" >
            }
    </s:if>
            if (document.users_form.email.value.length<6){
                mensaje = mensaje + "<s:text name="needEmailLongMin"/>" + "\n"
            }

            if (mensaje.length!=0){
                mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
                alert(mensaje)
                document.users_form.nombre.focus();
                return 0;
            } else {
                //el formulario se envia
                //el formulario se envia
                alert("<s:text name="thanksforform"/>");
                // se ponen selected todas las option del select
                for (i=0;i<window.document.users_form.ownedRoles.length;i++){
                    window.document.users_form.ownedRoles.options[i].selected=true;
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


