package it.amattioli.authorizate.users.ldap;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.specifications.Assembler;

public class LdapQueryAssembler implements Assembler {
	private static final String NOT = "!";
	private static final String AND = "&";
	private static final String OR = "|";
	private StringBuilder ldapQueryBuilder = new StringBuilder();
	private List<String> toBeAppended = new ArrayList<String>();

	@Override
	public void startNegation() {
		toBeAppended.add("(");
		toBeAppended.add(NOT);
	}
	
	@Override
	public void endNegation() {
		if (ldapQueryBuilder.length() != 0) {
			ldapQueryBuilder.append(')');
		}
	}

	@Override
	public void startConjunction() {
		toBeAppended.add("(");
		toBeAppended.add(AND);
	}
	
	@Override
	public void endConjunction() {
		if (ldapQueryBuilder.length() != 0) {
			ldapQueryBuilder.append(')');
		}
	}

	@Override
	public void startDisjunction() {
		toBeAppended.add("(");
		toBeAppended.add(OR);
	}
	
	@Override
	public void endDisjunction() {
		if (ldapQueryBuilder.length() != 0) {
			ldapQueryBuilder.append(')');
		}
	}
	
	public LdapQueryAssembler append(String fragment) {
		for (String curr : toBeAppended) {
			ldapQueryBuilder.append(curr);
		}
		toBeAppended.clear();
		ldapQueryBuilder.append(fragment);
		return this;
	}

	public String getAssembledQuery() {
		return ldapQueryBuilder.toString();
	}

	@Override
	public void noResults() {
		
	}
}
