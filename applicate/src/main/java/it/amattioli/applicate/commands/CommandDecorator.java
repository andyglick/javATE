package it.amattioli.applicate.commands;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.WrapDynaBean;

public abstract class CommandDecorator extends WrapDynaBean implements DynaCommand {
    private Command decorated;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        decorated.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        decorated.removePropertyChangeListener(listener);
    }

    public CommandDecorator(Command decorated) {
        super(decorated);
        this.decorated = decorated;
    }

    public Command getDecorated() {
        return decorated;
    }

    public void doCommand() throws ApplicationException {
        decorated.doCommand();
    }

    public void cancelCommand() {
    	decorated.cancelCommand();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        try {
            get((String)key);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public Set entrySet() {
        DynaProperty[] props = getDynaClass().getDynaProperties();
        Set result = new HashSet(props.length);
        for (int i = 0; i < props.length; i++) {
            String name = props[i].getName();
            Map.Entry val = new MyEntry(name, get(name));
            result.add(val);
        }
        return result;
    }

    public Object get(Object key) {
        return get((String)key);
    }

    public boolean isEmpty() {
        return false;
    }

    public Set keySet() {
        DynaProperty[] props = getDynaClass().getDynaProperties();
        Set result = new HashSet(props.length);
        for (int i = 0; i < props.length; i++) {
            result.add(props[i].getName());
        }
        return result;
    }

    public Object put(Object key, Object value) {
        Object old = get((String)key);
        set((String)key, value);
        return old;
    }

    public void putAll(Map t) {
        Set entries = t.entrySet();
        for (Iterator iter = entries.iterator(); iter.hasNext();) {
            Map.Entry curr = (Map.Entry)iter.next();
            put(curr.getKey(), curr.getValue());
        }
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return getDynaClass().getDynaProperties().length;
    }

    public Collection values() {
        DynaProperty[] props = getDynaClass().getDynaProperties();
        Collection result = new ArrayList(props.length);
        for (int i = 0; i < props.length; i++) {
            result.add(get(props[i].getName()));
        }
        return result;
    }

    private class MyEntry implements Map.Entry {
        private Object key;
        private Object value;

        public MyEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public Object setValue(Object value) {
            Object old = getValue();
            this.value = value;
            put(getKey(), value);
            return old;
        }

    }

}
