package it.amattioli.authorizate.users.hibernate;

import it.amattioli.authorizate.users.DefaultRole;
import it.amattioli.authorizate.users.DefaultRoleSpecification;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.CriteriaExclusionListSpecification;
import it.amattioli.dominate.hibernate.specifications.CriteriaStringSpecification;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;

public class HibernateRoleSpecification extends DefaultRoleSpecification {
	
	@Override
	public void assembleQuery(Assembler assembler) {
		assembler.startConjunction();
		CriteriaStringSpecification<DefaultRole> idSpec = new CriteriaStringSpecification<DefaultRole>("id");
		idSpec.setValue(getId());
		idSpec.assembleQuery(assembler);
		CriteriaStringSpecification<DefaultRole> descriptionSpec = new CriteriaStringSpecification<DefaultRole>("description");
		descriptionSpec.setValue(getDescription());
		descriptionSpec.assembleQuery(assembler);
		CriteriaExclusionListSpecification<DefaultRole> exclusionSpec = new CriteriaExclusionListSpecification<DefaultRole>();
		for (DefaultRole curr: getExclusionList()) {
			exclusionSpec.addToExclusionList(curr);
		}
		exclusionSpec.assembleQuery(assembler);
		assembler.endConjunction();
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

}
