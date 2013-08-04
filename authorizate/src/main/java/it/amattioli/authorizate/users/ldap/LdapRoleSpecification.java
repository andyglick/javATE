package it.amattioli.authorizate.users.ldap;

import org.apache.commons.lang.StringUtils;

import it.amattioli.authorizate.users.DefaultRole;
import it.amattioli.authorizate.users.DefaultRoleSpecification;
import it.amattioli.dominate.specifications.Assembler;

public class LdapRoleSpecification extends DefaultRoleSpecification {

	@Override
	public void assembleQuery(Assembler assembler) {
		assembler.startConjunction();
		if (!StringUtils.isBlank(getId())) {
			((LdapQueryAssembler)assembler).append("(uid=")
			                          .append(getId())
			                          .append(")");
		}
		if (!StringUtils.isBlank(getDescription())) {
			((LdapQueryAssembler)assembler).append("(description=")
		                                   .append(getDescription())
		                                   .append(")");
		}
		for (DefaultRole curr: getExclusionList()) {
			((LdapQueryAssembler)assembler).append("(!(cn=")
                                           .append(curr.getId())
                                           .append("))");
		}
		assembler.endConjunction();
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof LdapQueryAssembler;
	}

}
