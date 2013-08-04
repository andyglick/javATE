package it.amattioli.dominate.morphia;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.CriteriaContainer;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.QueryImpl;
import com.mongodb.Mongo;

import junit.framework.TestCase;

public class MorphiaAssemblerTest extends TestCase {

	private Datastore createDatastore() throws UnknownHostException {
		MorphiaSessionManager.setDatabase("test_database");
		Morphia morphia = new Morphia();
		morphia.map(MyEntity.class);
		Datastore ds = morphia.createDatastore(new Mongo(), "test_database");
		return ds;
	}
	
	public void testEmptyQuery() throws Exception {
		Datastore ds = createDatastore();
		Query<MyEntity> q = new QueryImpl<MyEntity>(MyEntity.class, null, ds);
		MorphiaAssembler<MyEntity> asm = new MorphiaAssembler<MyEntity>(q);
		assertEquals("{ }",asm.getAssembledQuery().toString());
	}
	
	public void testConjunction() throws Exception {
		Datastore ds = createDatastore();
		Query<MyEntity> q = new QueryImpl<MyEntity>(MyEntity.class, null, ds);
		MorphiaAssembler<MyEntity> asm = new MorphiaAssembler<MyEntity>(q);
		asm.startConjunction();
		asm.addCriteria(asm.getQuery().criteria("stringProperty").equal("a"));
		asm.addCriteria(asm.getQuery().criteria("longProperty").equal(1L));
		asm.endConjunction();
		assertEquals("{ \"stringProperty\" : \"a\" , \"longProperty\" : 1}",asm.getAssembledQuery().toString());
	}
	
	public void testDisjunction() throws Exception {
		Datastore ds = createDatastore();
		Query<MyEntity> q = new QueryImpl<MyEntity>(MyEntity.class, null, ds);
		MorphiaAssembler<MyEntity> asm = new MorphiaAssembler<MyEntity>(q);
		asm.startDisjunction();
		asm.addCriteria(asm.getQuery().criteria("stringProperty").equal("a"));
		asm.addCriteria(asm.getQuery().criteria("longProperty").equal(1L));
		asm.endDisjunction();
		assertEquals("{ \"$or\" : [ { \"stringProperty\" : \"a\"} , { \"longProperty\" : 1}]}",q.toString());
	}
	
	public void testNoResults() throws Exception {
		MorphiaSessionManager.setDatabase("test_database");
		Morphia morphia = new Morphia();
		morphia.map(MyEntity.class);
		Datastore ds = morphia.createDatastore(new Mongo(), "test_database");

		Query<MyEntity> q = new QueryImpl<MyEntity>(MyEntity.class, null, ds);
		MorphiaAssembler<MyEntity> asm = new MorphiaAssembler<MyEntity>(q);
		asm.noResults();
		assertEquals("{ \"$where\" : \"1 == 0\"}",asm.getAssembledQuery().toString());
	}

}
