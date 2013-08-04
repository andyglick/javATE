package it.amattioli.guidate.properties;

import it.amattioli.guidate.collections.GroupPropertyModel;

import org.zkoss.zul.Grid;

public class GroupPropertyComposer extends AbstractMultiplePropertyComposer {

	@Override
	protected void setModel(Grid grid, String propertyName) {
		grid.setModel(new GroupPropertyModel(getBackBean(grid), propertyName));
	}

}
