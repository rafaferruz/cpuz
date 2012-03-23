/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.tools;

import java.security.Principal;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.realm.DataSourceRealm;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.startup.Tomcat;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class RealmTool {

    private String containerName = "Catalina";
    private String contextName = "/CPUZ";
    private String user = "";
    private String credentials = "";
    private Principal principal;

    public RealmTool() {
    }

    public RealmTool(String param_user, String param_credentials) {
        user = param_user;
        credentials = param_credentials;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String param_containerName) {
        containerName = param_containerName;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String param_contextName) {
        contextName = param_contextName;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String param_credentials) {
        credentials = param_credentials;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String param_user) {
        user = param_user;
    }

    

    /**
     * Proporciona una referencia a un objeto de tipo Principal obtenido para
     * el usuario y password activos en el objeto RealmTool creado.
     * Hay que tener en cuenta que el usuario y la password podr�an ser
     * modificados por otro hilo en ejecución desde que se asignan al RealmTool
     * y se obtiene la referencia al objeto Principal.
     *
     * @return Una referencia al objeto Principal proporcionado por Realm
     */
    public Principal getPrincipal() {

        principal = getAuxPrincipal(user, credentials);
        return principal;
    }

    /**
     * Proporciona una referencia a un objeto de tipo Principal obtenido para
     * el usuario y password que se pasan como argumentos en la llamada a está
     * m�todo.
     * El usuario y password proporcionados se almacenan como el usuario y
     * la password activos en el objeto RealmTool creado hasta que sean modificados.
     *
     * @param user String indicando el nombre del usuario que se desea identificar
     * @param credentials String indocando la contrase�a de usuario a verificar
     * @return Una referencia al objeto Principal proporcionado por Realm.
     */
    public synchronized Principal getPrincipal(String param_user, String param_credentials) {
        user = param_user;
        credentials = param_credentials;
        if (user == null || user.equals("") || credentials == null || credentials.equals("")) {
            principal = null;
            return null;
        }
        principal = getAuxPrincipal(user, credentials);
        return principal;
    }

    /**
     * Proporciona una referencia a un objeto de tipo Principal obtenido para
     * el usuario y password que se pasan como argumentos en la llamada a está
     * m�todo.
     * El usuario y password proporcionados son temporales y exclusivos para
     * la verificación en curso, as� como el objeto de tipo Principal obtenido.
     *
     * @param user
     * @param credentials
     * @return
     */
    public synchronized Principal getAuxPrincipal(String param_user, String param_credentials) {
        Tomcat tomcat=new Tomcat();
		Server server = tomcat.getServer();
        Service service = server.findService(containerName);
        Engine engine = (Engine) service.getContainer();
        Host host = (Host) engine.findChild(engine.getDefaultHost());
        Context context = (Context) host.findChild(contextName);
        DataSourceRealm realm = (DataSourceRealm) context.getRealm();

        Principal principalAux = realm.authenticate(param_user, param_credentials);
        context.setRealm(realm);

        return principalAux;
    }
}
