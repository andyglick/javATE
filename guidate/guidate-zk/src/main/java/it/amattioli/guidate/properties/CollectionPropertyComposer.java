package it.amattioli.guidate.properties;

import java.util.ArrayList;
import java.util.Collection;

import it.amattioli.guidate.collections.CollectionPropertyModel;
import it.amattioli.guidate.collections.GroupingPropertyModel;
import it.amattioli.guidate.collections.PrototypeComposer;

import org.zkoss.zul.Grid;
import org.zkoss.zul.Group;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class CollectionPropertyComposer extends AbstractMultiplePropertyComposer {
	public static final String GROUP_BY_ATTRIBUTE = "groupBy";

	@Override
	protected void setModel(Grid grid, String propertyName) {
		String groupingProperty = (String)grid.getAttribute(GROUP_BY_ATTRIBUTE);
		if (groupingProperty != null) {
			grid.setModel(new GroupingPropertyModel(getBackBean(grid), propertyName, groupingProperty));
		} else {
			grid.setModel(new CollectionPropertyModel(getBackBean(grid), propertyName));
		}
	}

	@Override
	protected Collection<? extends Row> implicitPrototypes(Grid grid) throws Exception {
		String groupingProperty = (String)grid.getAttribute(GROUP_BY_ATTRIBUTE);
		if (groupingProperty != null) {
			ArrayList<Group> result = new ArrayList<Group>();
			Group prototype = new Group();
			Label label = new Label();
			label.setAttribute(PropertyNameRetriever.PROPERTY_NAME, "description");
			LabelPropertyComposer composer = new LabelPropertyComposer();
			composer.doAfterCompose(label);
			label.setParent(prototype);
			prototype.setAttribute(PrototypeComposer.SPECIFICATION_ATTRIBUTE, new GroupSpecification());
			result.add(prototype);
			return result;
		} else {
			return super.implicitPrototypes(grid);
		}
	}
	
}
