package it.amattioli.dominate.morphia;

import java.util.Iterator;
import java.util.List;

import org.bson.types.CodeWScope;

import com.google.code.morphia.Key;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.CriteriaContainer;
import com.google.code.morphia.query.CriteriaContainerImpl;
import com.google.code.morphia.query.FieldEnd;
import com.google.code.morphia.query.Query;

public class MorphiaQueryStub<T> implements Query<T> {

	@Override
	public CriteriaContainer and(Criteria... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> batchSize(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldEnd<? extends CriteriaContainerImpl> criteria(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> disableSnapshotMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> disableTimeout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> disableValidation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> enableSnapshotMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> enableTimeout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> enableValidation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldEnd<? extends Query<T>> field(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> filter(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<T> getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> hintIndex(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> limit(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> offset(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CriteriaContainer or(Criteria... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> order(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> queryNonPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> queryPrimaryOnly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> retrievedFields(boolean arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> skip(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> where(CodeWScope arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> where(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Key<T>> asKeyList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> asList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<T> fetch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<T> fetchEmptyEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Key<T>> fetchKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Key<T> getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> clone() {
		return null;
	}

}
