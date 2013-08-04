package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import junit.framework.TestCase;

public class HqlAssemblerTest extends TestCase {
	
	public void testSingleSpecification() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.newCriteria();
		assembler.append("property = 0");
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where property = 0", queryString);
	}
	
	public void testEmptyConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias", queryString);
	}
	
	public void testConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		spec2.addHqlCondition(assembler);
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and upper(entity_alias.description) like :description)", queryString);
	}
	
	public void testConjunctionWithOneSpecification() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startConjunction();
		assembler.newCriteria();
		assembler.append("property = 0");
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (property = 0)", queryString);
	}
	
	public void testEmptyDisjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startDisjunction();
		assembler.endDisjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias", queryString);
	}
	
	public void testDisjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		assembler.startDisjunction();
		spec1.addHqlCondition(assembler);
		spec2.addHqlCondition(assembler);
		assembler.endDisjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id or upper(entity_alias.description) like :description)", queryString);
	}
	
	public void testDisjunctionWithOneSpecification() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startDisjunction();
		assembler.newCriteria();
		assembler.append("property = 0");
		assembler.endDisjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (property = 0)", queryString);
	}
	
	public void testNestedConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		HqlEnumSpecification<MyEntity, MyEnum> spec3 = new HqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec3.setValue(MyEnum.VALUE1);
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		assembler.startConjunction();
		spec2.addHqlCondition(assembler);
		spec3.addHqlCondition(assembler);
		assembler.endConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and (upper(entity_alias.description) like :description and entity_alias.enumProperty like :enumProperty ))", 
				               queryString);
	}
	
	public void testDisjunctionNestedInConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		HqlEnumSpecification<MyEntity, MyEnum> spec3 = new HqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec3.setValue(MyEnum.VALUE1);
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		assembler.startDisjunction();
		spec2.addHqlCondition(assembler);
		spec3.addHqlCondition(assembler);
		assembler.endDisjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and (upper(entity_alias.description) like :description or entity_alias.enumProperty like :enumProperty ))", 
				               queryString);
	}
	
	public void testNotSetDisjunctionDoubleNestedInConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		
		HqlEnumSpecification<MyEntity, MyEnum> spec3 = new HqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		
		HqlStringSpecification<MyEntity> spec4 = new HqlStringSpecification<MyEntity>("description");
		spec4.setValue("descr1");
		HqlEnumSpecification<MyEntity, MyEnum> spec5 = new HqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec5.setValue(MyEnum.VALUE1);
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		assembler.startDisjunction();
		assembler.startDisjunction();
		spec2.addHqlCondition(assembler);
		spec3.addHqlCondition(assembler);
		assembler.endDisjunction();
		assembler.startDisjunction();
		spec4.addHqlCondition(assembler);
		spec5.addHqlCondition(assembler);
		assembler.endDisjunction();
		assembler.endDisjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id and ((upper(entity_alias.description) like :description or entity_alias.enumProperty like :enumProperty )))", 
				               queryString);
	}
	
	public void testEmptyConjunctionNestedInConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		assembler.startConjunction();
		assembler.endConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id )",
				               queryString);
	}
	
	public void testEmptyDisjunctionNestedInConjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		assembler.startDisjunction();
		assembler.endDisjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id )",
				               queryString);
	}
	
	public void testDisjunctionNestedInDisjunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		HqlEnumSpecification<MyEntity, MyEnum> spec3 = new HqlEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec3.setValue(MyEnum.VALUE1);
		assembler.startDisjunction();
		spec1.addHqlCondition(assembler);
		assembler.startDisjunction();
		spec2.addHqlCondition(assembler);
		spec3.addHqlCondition(assembler);
		assembler.endDisjunction();
		assembler.endDisjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where (upper(entity_alias.id) like :id or (upper(entity_alias.description) like :description or entity_alias.enumProperty like :enumProperty))", 
				               queryString);
	}
	
	public void testJoin() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startConjunction();
		assembler.addJoin("left outer join entity_alias.assoc");
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("select entity_alias from MyEntity entity_alias left outer join entity_alias.assoc", queryString);
	}
	
	public void testOrder() throws Exception {
		ArrayList<Order> orders = new ArrayList<Order>() {{
			add(new Order("descr1",false));
		}};
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", orders);
		assembler.startConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias order by entity_alias.descr1 asc", queryString);
	}
	
	public void testMultipleOrder() throws Exception {
		ArrayList<Order> orders = new ArrayList<Order>() {{
			add(new Order("descr1",false));
			add(new Order("descr2",true));
			add(new Order("descr3",false));
		}};
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", orders);
		assembler.startConjunction();
		assembler.endConjunction();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias order by entity_alias.descr1 asc,entity_alias.descr2 desc,entity_alias.descr3 asc", queryString);
	}
	
	public void testSimpleNot() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		assembler.startNegation();
		spec1.addHqlCondition(assembler);
		assembler.endNegation();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where not(upper(entity_alias.id) like :id)", queryString);
	}
	
	public void testEmptyNot() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		assembler.startNegation();
		assembler.endNegation();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias", queryString);
	}
	
	public void testNegatedJunction() throws Exception {
		HqlAssembler assembler = new HqlAssembler("from MyEntity ", new ArrayList<Order>());
		HqlStringSpecification<MyEntity> spec1 = new HqlStringSpecification<MyEntity>("id");
		spec1.setValue("id1");
		HqlStringSpecification<MyEntity> spec2 = new HqlStringSpecification<MyEntity>("description");
		spec2.setValue("descr1");
		assembler.startNegation();
		assembler.startConjunction();
		spec1.addHqlCondition(assembler);
		spec2.addHqlCondition(assembler);
		assembler.endConjunction();
		assembler.endNegation();
		String queryString = assembler.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where not((upper(entity_alias.id) like :id and upper(entity_alias.description) like :description))", queryString);
	}
}
