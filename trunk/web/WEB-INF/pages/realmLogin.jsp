<%-- 
    Document   : RealmLogin
    Created on : 24-feb-2010, 7:58:04
    Author     : RAFAEL FERRUZ
--%>

<br />
<br />
<h3 align="center"><fmt:message key="userLogin" bundle="${bundle}" /></h3>

<table width="400"  border="1" cellspacing="0" cellpadding="10" class="form_user">
    <tr>
        <td width="60%" valign="top">
            <p align="center"><fmt:message key="tryLoginFull_Msg1"  bundle="${bundle}"/></p>
        </td>
        <td width="40%">
            <form id="form_login" name="form_login" method="post" action="j_security_check">
                <br />
                <label><fmt:message key="user" bundle="${bundle}" />
                    <br />
                    <input name="j_username" type="text" id="j_username" size="16" maxlength="16" />
                </label><br /><br />
                <label><fmt:message key="password" bundle="${bundle}" />
                    <br />
                    <input name="j_password" type="password" id="j_password" size="16" maxlength="16" />
                </label>
                <br />
                <br />
                <input name="enviarusuario" type="submit" value="<fmt:message key="send" bundle="${bundle}" />" class="form_user_button"/>
            </form>
        </td>
    </tr>
</table>
<br />
<br />

<script type="text/javascript">
    document.form_login.j_username.focus()
    document.form_login.j_username.select()
</script>




