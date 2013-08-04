package it.amattioli.dominate.jpa.specifications;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.AbstractSpecification;
import it.amattioli.dominate.specifications.Assembler;

/**
 * This specification is used for adding a custom fragment of Jpql query having a single
 * parameter.
 * <p>
 * It is particularly useful for adding subqueries. For example:
 * <pre>
 * new JpaqlCustomSpecification<MyEntity, String>("exists( select 1 from {alias}.details d where d.my_field = :my_param)");
 * </pre>
 * Notice the "{alias}" syntax for inserting the alias of the main query entity.
 * <p>
 * The value returned by {@link #isSatisfiedBy(Entity)} is always false. To return a meaningful
 * value you have to override this method.
 * 
 * @author andrea
 *
 * @param <T> The class of the entities that can be checked by this specification 
 * @param <U> The class of the query parameter
 */
public class JpaqlCustomSpecification<T extends Entity<?>, U> extends AbstractSpecification<T> {
	private U value;
	private String queryFragment;
	
	public JpaqlCustomSpecification(String queryFragment) {
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
			JpaqlAssembler hqlAssembler = (JpaqlAssembler)assembler;
			hqlAssembler.newCriteria();
			hqlAssembler.append(queryFragmentFor(hqlAssembler));
			addParameterIfAny(hqlAssembler);
		}
		
	}

	private String queryFragmentFor(JpaqlAssembler hqlAssembler) {
		String alias = hqlAssembler.getAlias();
		if (alias == null) {
			alias = "";
		} else {
			alias = alias + ".";
		}
		return this.queryFragment.replace("{alias}.", alias);
	}
	
	private void addParameterIfAny(JpaqlAssembler hqlAssembler) {
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
		return assembler instanceof JpaqlAssembler;
	}

	@Override
	public boolean wasSet() {
		return value != null;
	}
}
