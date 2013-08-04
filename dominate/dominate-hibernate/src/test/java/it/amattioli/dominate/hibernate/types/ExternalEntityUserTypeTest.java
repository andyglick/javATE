package it.amattioli.dominate.hibernate.types;

import static org.mockito.Mockito.*;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.hibernate.FakeEntity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.memory.CollectionRepository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

public class ExternalEntityUserTypeTest extends TestCase {
	
	public final class ExternalEntityRepositoryFactory extends AbstractRepositoryFactory {
		
		public Repository<String, ExternalEntity> getExternalEntityRepository() {
			List<ExternalEntity> l = new ArrayList<ExternalEntity>();
			l.add(new ExternalEntity("1234"));
			return new CollectionRepository<String, ExternalEntity>(l);
		}

		@Override
		public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected <I extends Serializable, T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public void setUp() {
		RepositoryRegistry.instance().setRepositoryFactory(new ExternalEntityRepositoryFactory());
	}

	public void testParameters() {
		Properties p = new Properties();
		p.setProperty(ExternalEntityUserType.ENTITY_CLASS_PARAM, "it.amattioli.dominate.hibernate.types.ExternalEntity");
		p.setProperty(ExternalEntityUserType.ID_TYPE_PARAM, "CHAR");
		ExternalEntityUserType<String, ExternalEntity> t = new ExternalEntityUserType<String, ExternalEntity>();
		t.setParameterValues(p);
		
		assertEquals(ExternalEntity.class, t.returnedClass());
		assertEquals(Types.CHAR, t.sqlTypes()[0]);
	}
	
	public void testGet() throws Exception {
		ExternalEntityUserType<String, ExternalEntity> t = createUserType(ExternalEntity.class,Types.CHAR);
		
		ResultSet r = mock(ResultSet.class);
		when(r.getString("externalId")).thenReturn("1234");
		
		ExternalEntity result = (ExternalEntity)t.nullSafeGet(r, new String[] {"externalId"}, null);
		assertEquals("1234", result.getId());
	}
	
	public void testGetNull() throws Exception {
		ExternalEntityUserType<String, ExternalEntity> t = createUserType(ExternalEntity.class,Types.CHAR);
		
		ResultSet r = mock(ResultSet.class);
		when(r.getString("externalId")).thenReturn(null);
		
		ExternalEntity result = (ExternalEntity)t.nullSafeGet(r, new String[] {"externalId"}, null);
		assertNull(result);
	}
	
	public void testSet() throws Exception {
		ExternalEntityUserType<String, ExternalEntity> t = createUserType(ExternalEntity.class,Types.CHAR);
		
		PreparedStatement p = mock(PreparedStatement.class);
		ExternalEntity e = new ExternalEntity("4321");
		
		t.nullSafeSet(p, e, 1);
		
		verify(p).setObject(1, "4321", Types.CHAR);
	}
	
	public void testSetNull() throws Exception {
		ExternalEntityUserType<String, ExternalEntity> t = createUserType(ExternalEntity.class,Types.CHAR);
		
		PreparedStatement p = mock(PreparedStatement.class);
		
		t.nullSafeSet(p, null, 1);
		
		verify(p).setNull(1, Types.CHAR);
	}

	private <I extends Serializable, T extends Entity<I>> ExternalEntityUserType<I, T> createUserType(Class<T> classParam, int sqlTypeParam) {
		return new ExternalEntityUserType<I, T>(classParam, sqlTypeParam, false);
	}
	
}
