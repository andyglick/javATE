package it.amattioli.applicate.commands;

import it.amattioli.applicate.editors.BeanEditorImpl;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;

import java.io.Serializable;

import org.apache.commons.lang.ObjectUtils;

public class EntityEditorCommand<I extends Serializable, T extends Entity<I>> extends BeanEditorImpl<T> implements Command, Resettable {
    private CommandEventSupport cmdEvtSupport = new CommandEventSupport();
    private Repository<I,T> repository;
	private Class<T> entityClass;
	private boolean toBeReset = false;

	public EntityEditorCommand() {
    }

	public EntityEditorCommand(Class<T> entityClass) {
	    setEntityClass(entityClass);
	}

	public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
    	if (!ObjectUtils.equals(this.entityClass, entityClass)) {
	        this.entityClass = entityClass;
	        this.repository = RepositoryRegistry.instance().getRepository(entityClass);
	        try {
	            setEditingBean(entityClass.newInstance());
	        } catch (InstantiationException e) {
	            // TODO handle exception
	            throw new RuntimeException(e);
	        } catch (IllegalAccessException e) {
	            // TODO handle exception
	            throw new RuntimeException(e);
	        }
    	}
    }

    @Override
    public T getEditingBean() {
	    T result = super.getEditingBean();
	    if (result == null) {
	        try {
	            setEditingBean(entityClass.newInstance());
            } catch (InstantiationException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            }
            result = super.getEditingBean();
	    }
        return result;
    }

	@Override
	public void setEditingBean(T editingBean) {
		T newBean = null;
		if (editingBean != null) {
            I subjectId = editingBean.getId();
            if (subjectId != null) {
                newBean = repository.get(subjectId);
//                if (!newBean.getVersion().equals(editingBean.getVersion())) {
//                    throw new ConcurrencyException(getSession().getEntityName(newBean));
//                }
            } else {
            	newBean = editingBean;
            }
        }
		super.setEditingBean(newBean);
	}
	
	public I getEditingBeanId() {
		return getEditingBean().getId();
	}

	public void setEditingBeanId(I id) {
	    T newBean = null;
	    if (id != null) {
	        newBean = repository.get(id);
	    } else {
	        try {
	            newBean = entityClass.newInstance();
            } catch (InstantiationException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                // TODO handle exception
                throw new RuntimeException(e);
            }
	    }
	    super.setEditingBean(newBean);
	}

	public void setId(I id) {
		setEditingBeanId(id);
	}
	
	@Override
	public void doCommand() throws ApplicationException {
		repository.put(getEditingBean());
        cmdEvtSupport.fireCommandEvent(new CommandEvent(this, CommandResult.SUCCESSFUL));
	}

	@Override
	public void cancelCommand() {
		//getSession().refresh(getEditingBean());
	}

	public void addCommandListener(CommandListener listener) {
	    cmdEvtSupport.addListener(listener);
	}

	public void addCommandListener(CommandListener listener, CommandResult... results) {
		cmdEvtSupport.addListener(listener, results);
	}

	@Override
	public void reset() {
		setEditingBeanId(getEditingBeanId());
	}

	@Override
	public boolean toBeReset() {
		return toBeReset;
	}
	
	public void setToBeReset(boolean toBeReset) {
		this.toBeReset = toBeReset;
	}

}
