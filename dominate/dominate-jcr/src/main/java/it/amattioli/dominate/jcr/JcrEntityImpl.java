package it.amattioli.dominate.jcr;

public class JcrEntityImpl implements JcrEntity {
	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}
