package it.amattioli.authorizate.users.hibernate;

import it.amattioli.authorizate.users.DefaultUser;
import it.amattioli.authorizate.users.DefaultUserSpecification;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.dominate.hibernate.specifications.HqlStringSpecification;

public class HibernateUserSpecification extends DefaultUserSpecification {
	
	@Override
	public void assembleQuery(Assembler assembler) {
		assembler.startConjunction();
		HqlStringSpecification<DefaultUser> idSpec = new HqlStringSpecification<DefaultUser>("id");
		idSpec.setValue(getId());
		idSpec.assembleQuery(assembler);
		HqlStringSpecification<DefaultUser> cnSpec = new HqlStringSpecification<DefaultUser>("commonName");
		cnSpec.setValue(getCommonName());
		cnSpec.assembleQuery(assembler);
		HqlStringSpecification<DefaultUser> snSpec = new HqlStringSpecification<DefaultUser>("surname");
		snSpec.setValue(getSurname());
		snSpec.assembleQuery(assembler);
		assembler.endConjunction();
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

}
