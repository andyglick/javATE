package it.amattioli.workstate.info;

public abstract class TreeDuplicator implements Visitor {
  private TreeBuilder builder = newBuilder(); 
  
  protected abstract TreeBuilder newBuilder();
  
  protected abstract Object getInfo(Receiver receiver);
  
  protected abstract String getTag(Receiver receiver);
  
  public void visit(Receiver receiver) {
    Object info = getInfo(receiver);
    String tag = getTag(receiver);
    if (info != null && !info.equals("")) {
      builder.newNode(tag, info);
    }
    receiver.receive(this);
    if (info != null && !info.equals("")) {
      builder.closeNode();
    }
  }
  
  public TreeInfo getResult() {
    return builder.getResult();
  }
  
}
