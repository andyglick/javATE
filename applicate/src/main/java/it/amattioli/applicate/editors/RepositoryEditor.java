package it.amattioli.applicate.editors;

import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandEventSupport;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

public class RepositoryEditor <I extends Serializable, E extends Entity<I>> extends ListEditorImpl<E> implements Command {
	private CommandEventSupport cmdEvtSupport = new CommandEventSupport();
	private Repository<I, E> editingRepository;

	public RepositoryEditor() {
		
	}
	
	public RepositoryEditor(Class<E> entityClass) {
		this(RepositoryRegistry.instance().getRepository(entityClass), entityClass);
	}
	
	public RepositoryEditor(Repository<I, E> editingRepository, Class<E> entityClass) {
		super(new RepositoryList(editingRepository), entityClass);
		this.editingRepository = editingRepository;
	}

	@Override
	public void doCommand() throws ApplicationException {
		/*
		for (E curr : getEditingList()) {
			editingRepository.put(curr);
		}
		*/
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
	
	private static class RepositoryList<I extends Serializable, E extends Entity<I>> extends AbstractList<E> {
		private Repository<I,E> repository;
		private List<E> list;
		
		public RepositoryList(Repository<I,E> repository) {
			this.repository = repository;			
		}
		
		@Override
		public void add(int index, E element) {
			getList().add(index, element);
			repository.put(element);
		}

		@Override
		public E get(int index) {
			return getList().get(index);
		}

		@Override
		public E remove(int index) {
			E removed = getList().remove(index);
			repository.remove(removed);
			return removed;
		}

		@Override
		public E set(int index, E element) {
			repository.put(element);
			return getList().set(index, element);
		}

		@Override
		public int size() {
			return getList().size();
		}

		private List<E> getList() {
			if (list == null) {
				this.setList(repository.list());
			}
			return list;
		}

		private void setList(List<E> list) {
			this.list = list;
		}
		
	}
}
