package it.amattioli.guidate.init;

import it.amattioli.authorizate.users.ldap.LdapServer;
import it.amattioli.dominate.jpa.JpaSessionManager;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventThreadCleanup;

public class JpaEventCleanup implements EventThreadCleanup {

    @Override
    public void cleanup(Component comp, Event evt, List ers) throws Exception {
        JpaSessionManager.disconnectAll();
        LdapServer.releaseContext();
    }

    @Override
    public void complete(Component comp, Event evt) throws Exception {
        // Nothing to do
    }

}
