package it.amattioli.authorizate.users.ldap;

import org.apache.commons.lang.StringUtils;

import it.amattioli.authorizate.users.DefaultUserSpecification;
import it.amattioli.dominate.specifications.Assembler;

public class LdapUserSpecification extends DefaultUserSpecification {

	@Override
	public void assembleQuery(Assembler assembler) {
		assembler.startConjunction();
		if (!StringUtils.isBlank(getId())) {
			((LdapQueryAssembler)assembler).append("(uid=")
			                          .append(getId())
			                          .append(")");
		}
		if (!StringUtils.isBlank(getCommonName())) {
			((LdapQueryAssembler)assembler).append("(cn=")
			                          .append(getCommonName())
			                          .append(")");
		}
		if (!StringUtils.isBlank(getSurname())) {
			((LdapQueryAssembler)assembler).append("(sn=")
			                          .append(getSurname())
			                          .append(")");
		}
		assembler.endConjunction();
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof LdapQueryAssembler;
	}

}
