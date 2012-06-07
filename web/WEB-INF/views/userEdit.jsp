<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="control.runAction == 'Edit'" >
    <h1 align="center"><s:text name="UserEdition"/></h1>
</s:if>

<s:if test="control.runAction == 'New'" >
    <h1 align="center"><s:text name="UserMailBox"/></h1>
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
    <s:if test="control.runAction == 'New'" >
        <s:hidden name="control.runAction" id="runAction" value="SaveNew"/>
        <s:date name="dataEdit.date" id="dateVar" format="dd/MM/yyyy" var="dateFormat" />
        <s:hidden name="dataEdit.date" id="date" value="%{#dateFormat}"/>
    </s:if>
    <s:if test="control.runAction == 'Edit'" >
        <s:hidden name="control.runAction" id="runAction" value="SaveEdit"/>
        <s:hidden name="control.id" id="id" value="%{dataEdit.id}"/>
        <s:set var="readonly" value="true"/>
        <s:textfield key="dataEdit.id"
                     id="identifier"
                     size="5" maxlength="8"
                     readonly="true"/>
        <s:date name="dataEdit.date" id="dateVar" format="dd/MM/yyyy" var="dateFormat" />
        <s:textfield key="dataEdit.date" id="date"  maxlength="10"
                     value="%{#dateFormat}" onBlur="validateDate(this)"
                     readonly="true"/>
    </s:if>
    <s:if test="control.runAction == 'New'" >
        <s:set var="readonly" value="false"/>
        <s:hidden name="dataEdit.date" id="date" value="%{dataEdit.date}"/>
    </s:if>

    <s:textfield key="dataEdit.user" id="user"
                 size="8" maxlength="8"  readonly="#readonly"/>
    <s:textfield key="dataEdit.name" id="name"
                 size="30" maxlength="40"/>

    <s:radio key="dataEdit.status" list="mapStatus" id="status"/>

    <s:radio key="dataEdit.category" list="mapCategory" id="category"/>

    <%-- Se muestran dos select con los roles autorizados y todos los roles disponobles            --%>
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
                        <s:if test="control.runAction == 'Edit'" >
                        <td align="center"><s:submit type="button" name="newButton"
                                  id="newButton" value="%{getText('New')}"
                                  onclick="actionExecute('New');" theme="simple"/></td>
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
    function actionExecute(accion) {
        window.document.users_form.runAction.value = accion;
        window.document.users_form.action = "User"+accion+".action";
        window.document.users_form.submit();
    }
    function pass_available_owned(){
        for (i=0;i<window.document.users_form.availableRoles.length;i++){
            if (window.document.users_form.availableRoles.options[i].selected==true) {
                window.document.users_form.ownedRoles.options[window.document.users_form.ownedRoles.length] = new Option(window.document.users_form.availableRoles.options[i].text, window.document.users_form.availableRoles.options[i].value, false,false)
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
                window.document.users_form.availableRoles.options[window.document.users_form.availableRoles.length] = new Option(window.document.users_form.ownedRoles.options[i].text, window.document.users_form.ownedRoles.options[i].value, false,false)
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
        if (document.users_form.name.value.length<5){
            mensaje = mensaje + "<s:text name="needNameUserLongMin"/>" + "\n"
        }
    <s:if test="control.runAction == 'Edit'" >
            if (document.users_form.password.value.length!=0 || document.users_form.password_again.value.length!=0){
    </s:if>
                if (document.users_form.password.value.length<6){
                    mensaje = mensaje + "<s:text name="needPassWordLongMin"/>" + "\n"
                }
                if (document.users_form.password.value != document.users_form.password_again.value){
                    mensaje = mensaje + "<s:text name="needPassWordEquals"/>"+"\n"
                }
    <s:if test="control.runAction == 'Edit'" >
            }
    </s:if>
            if (document.users_form.email.value.length<6){
                mensaje = mensaje + "<s:text name="needEmailLongMin"/>" + "\n"
            }

            if (mensaje.length!=0){
                mensaje = "<s:text name="founderrors"/>" + ":\n\n" + mensaje
                alert(mensaje)
                document.users_form.name.focus();
                return 0;
            } else {
                //el formulario se envia
                //el formulario se envia
                alert("<s:text name="thanksforform"/>");
                // se ponen selected todas las option del select
                for (i=0;i<window.document.users_form.ownedRoles.length;i++){
                    window.document.users_form.ownedRoles.options[i].selected=true;
                }

    <s:if test="control.runAction == 'New'" >
                actionExecute("SaveNew");
                return;
    </s:if>
    <s:if test="control.runAction == 'Edit'" >
                actionExecute("SaveEdit");
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


