package it.amattioli.dominate.specifications.dflt;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import bsh.Interpreter;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.AbstractSpecification;
import it.amattioli.dominate.specifications.Assembler;

public class BeanShellSpecification<T extends Entity<?>> extends AbstractSpecification<T>{
	private String code;
	
	public BeanShellSpecification(String code) {
		this.code = code;
	}
	
	@Override
	public void assembleQuery(Assembler assembler) {
		((PredicateAssembler)assembler).addAssembledPredicate(new SpecificationPredicate<T>(this));
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		Boolean result = Boolean.FALSE;
		try {
			Interpreter i = new Interpreter();
			Map<String, ?> attrMap = PropertyUtils.describe(object);
			for (Map.Entry<String, ?> curr : attrMap.entrySet()) {
				i.set(curr.getKey(), curr.getValue());
			}
			i.eval("boolean specificationExpression = " + code);
			result = (Boolean) i.get("specificationExpression");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result.booleanValue();
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof PredicateAssembler;
	}

	@Override
	public boolean wasSet() {
		return true;
	}

}
