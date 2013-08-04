package it.amattioli.dominate.hibernate.specifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.AbstractSpecification;
import it.amattioli.dominate.specifications.Assembler;

/**
 * This specification is used for adding a custom fragment of Hql query having a single
 * parameter.
 * <p>
 * It is particularly useful for adding subqueries. For example:
 * <pre>
 * new HqlCustomSpecification<MyEntity, String>("exists( select 1 from {alias}.details d where d.my_field = :my_param)");
 * </pre>
 * Notice the "{alias}" syntax for inserting the alias of the main query entity.
 * <p>
 * Unless the parameter was not set, the value returned by {@link #isSatisfiedBy(Entity)} is always false. 
 * To return a meaningful value you have to override this method.
 * 
 * @author andrea
 *
 * @param <T> The class of the entities that can be checked by this specification 
 * @param <U> The class of the query parameter
 */
public class HqlCustomSpecification<T extends Entity<?>, U> extends AbstractSpecification<T> {
	private U value;
	private String queryFragment;
	
	public HqlCustomSpecification(String queryFragment) {
		this.queryFragment = queryFragment;
	}
	
	public U getValue() {
		return value;
	}
	
	public void setValue(U value) {
		this.value = value;
	}
	
	@Override
	public void assembleQuery(Assembler assembler) {
		if (wasSet()) {
			HqlAssembler hqlAssembler = (HqlAssembler)assembler;
			hqlAssembler.newCriteria();
			hqlAssembler.append(queryFragmentFor(hqlAssembler));
			addParameterIfAny(hqlAssembler);
		}
		
	}

	private String queryFragmentFor(HqlAssembler hqlAssembler) {
		String alias = hqlAssembler.getAlias();
		if (alias == null) {
			alias = "";
		} else {
			alias = alias + ".";
		}
		return this.queryFragment.replace("{alias}.", alias);
	}
	
	private void addParameterIfAny(HqlAssembler hqlAssembler) {
		Matcher matcher = Pattern.compile(":[A-Za-z_0-9]*").matcher(this.queryFragment);
		if (matcher.find()) {
			String paramName = this.queryFragment.substring(matcher.start()+1, matcher.end());
			hqlAssembler.addParameter(paramName, value);
		}
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		return false;
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

	@Override
	public boolean wasSet() {
		return value != null;
	}
}
