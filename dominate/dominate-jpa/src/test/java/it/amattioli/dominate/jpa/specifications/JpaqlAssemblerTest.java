package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import junit.framework.TestCase;

public class JpaqlAssemblerTest extends TestCase {
	
	public void testSingleSpecification() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.newCriteria();
		assembler.append("property = 0");
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where property = 0", queryString);
	}
	
	public void testEmptyConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias", queryString);
	}
	
	public void testConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		spec2.addJpaqlCondition(assembler);
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and upper(entity_alias.description) like :description)", queryString);
	}
	
	public void testConjunctionWithOneSpecification() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startConjunction();
		assembler.newCriteria();
		assembler.append("property = 0");
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (property = 0)", queryString);
	}
	
	public void testEmptyDisjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startDisjunction();
		assembler.endDisjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias", queryString);
	}
	
	public void testDisjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		assembler.startDisjunction();
		spec1.addJpaqlCondition(assembler);
		spec2.addJpaqlCondition(assembler);
		assembler.endDisjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id or upper(entity_alias.description) like :description)", queryString);
	}
	
	public void testDisjunctionWithOneSpecification() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startDisjunction();
		assembler.newCriteria();
		assembler.append("property = 0");
		assembler.endDisjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (property = 0)", queryString);
	}
	
	public void testNestedConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		JpaqlEnumSpecification<MyEntity, MyEnum> spec3 = new JpaqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec3.setValue(MyEnum.VALUE1);
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		assembler.startConjunction();
		spec2.addJpaqlCondition(assembler);
		spec3.addJpaqlCondition(assembler);
		assembler.endConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and (upper(entity_alias.description) like :description and entity_alias.enumProperty like :enumProperty ))", 
				               queryString);
	}
	
	public void testDisjunctionNestedInConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		JpaqlEnumSpecification<MyEntity, MyEnum> spec3 = new JpaqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec3.setValue(MyEnum.VALUE1);
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		assembler.startDisjunction();
		spec2.addJpaqlCondition(assembler);
		spec3.addJpaqlCondition(assembler);
		assembler.endDisjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and (upper(entity_alias.description) like :description or entity_alias.enumProperty like :enumProperty ))", 
				               queryString);
	}
	
	public void testNotSetDisjunctionDoubleNestedInConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		
		JpaqlEnumSpecification<MyEntity, MyEnum> spec3 = new JpaqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		
		JpaqlStringSpecification<MyEntity> spec4 = new JpaqlStringSpecification<MyEntity>("description");
		spec4.setValue("descr1");
		JpaqlEnumSpecification<MyEntity, MyEnum> spec5 = new JpaqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec5.setValue(MyEnum.VALUE1);
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		assembler.startDisjunction();
		assembler.startDisjunction();
		spec2.addJpaqlCondition(assembler);
		spec3.addJpaqlCondition(assembler);
		assembler.endDisjunction();
		assembler.startDisjunction();
		spec4.addJpaqlCondition(assembler);
		spec5.addJpaqlCondition(assembler);
		assembler.endDisjunction();
		assembler.endDisjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and ((upper(entity_alias.description) like :description or entity_alias.enumProperty like :enumProperty )))", 
				               queryString);
	}
	
	public void testEmptyConjunctionNestedInConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		assembler.startConjunction();
		assembler.endConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id )",
				               queryString);
	}
	
	public void testEmptyDisjunctionNestedInConjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		assembler.startDisjunction();
		assembler.endDisjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id )",
				               queryString);
	}
	
	public void testDisjunctionNestedInDisjunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		JpaqlEnumSpecification<MyEntity, MyEnum> spec3 = new JpaqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec3.setValue(MyEnum.VALUE1);
		assembler.startDisjunction();
		spec1.addJpaqlCondition(assembler);
		assembler.startDisjunction();
		spec2.addJpaqlCondition(assembler);
		spec3.addJpaqlCondition(assembler);
		assembler.endDisjunction();
		assembler.endDisjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id or (upper(entity_alias.description) like :description or entity_alias.enumProperty like :enumProperty))", 
				               queryString);
	}
	
	public void testJoin() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startConjunction();
		assembler.addJoin("left outer join entity_alias.assoc");
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("select entity_alias from MyEntity entity_alias left outer join entity_alias.assoc", queryString);
	}
	
	public void testOrder() throws Exception {
		ArrayList<Order> orders = new ArrayList<Order>() {{
			add(new Order("descr1",false));
		}};
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", orders);
		assembler.startConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias order by entity_alias.descr1 asc", queryString);
	}
	
	public void testMultipleOrder() throws Exception {
		ArrayList<Order> orders = new ArrayList<Order>() {{
			add(new Order("descr1",false));
			add(new Order("descr2",true));
			add(new Order("descr3",false));
		}};
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", orders);
		assembler.startConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias order by entity_alias.descr1 asc,entity_alias.descr2 desc,entity_alias.descr3 asc", queryString);
	}
	
	public void testSimpleNot() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startNegation();
		spec1.addJpaqlCondition(assembler);
		assembler.endNegation();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where not(upper(entity_alias.id) like :id)", queryString);
	}
	
	public void testEmptyNot() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startNegation();
		assembler.endNegation();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias", queryString);
	}
	
	public void testNegatedJunction() throws Exception {
		JpaqlAssembler assembler = new JpaqlAssembler("from MyEntity ", new ArrayList<Order>());
		JpaqlStringSpecification<MyEntity> spec1 = new JpaqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		JpaqlStringSpecification<MyEntity> spec2 = new JpaqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		assembler.startNegation();
		assembler.startConjunction();
		spec1.addJpaqlCondition(assembler);
		spec2.addJpaqlCondition(assembler);
		assembler.endConjunction();
		assembler.endNegation();
		String queryString = assembler.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where not((upper(entity_alias.id) like :id and upper(entity_alias.description) like :description))", queryString);
	}
}
