package it.amattioli.workstate.info;
import java.util.*;

public abstract class TreeBuilder {
  private Stack<TreeInfo> nodeStack = new Stack<TreeInfo>();
  private TreeInfo current; 
  
  protected abstract TreeInfo buildNode(String tag, Object info);
  
  public void newNode(String tag, Object info) {
    TreeInfo newNode = buildNode(tag, info);
    if (current != null) {
      current.addSubNode(tag,newNode);
      nodeStack.push(current);
    }
    current = newNode;    
  }
  
  public void closeNode() {
    if (!nodeStack.empty()) {
      current = nodeStack.pop();
    }
  }
  
  public TreeInfo getResult() {
    return current;
  }

}
