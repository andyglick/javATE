package it.amattioli.workstate.info;
import java.util.*;

public abstract class TreeInfo implements java.io.Serializable {
  private Map<String,TreeInfo> subNodes;

  public abstract Object getInfo();

  public Map<String,TreeInfo> getSubNodes() {
    if (subNodes == null) {
      return Collections.emptyMap();
    } else {
      return subNodes;
    }
  }
  
  public void addSubNode(String tag,TreeInfo node) {
    if (subNodes == null) {
      subNodes = new TreeMap<String,TreeInfo>();
    }
    subNodes.put(tag,node);
  }

  public TreeInfo findTarget(String tag) {
    TreeInfo result = (TreeInfo)getSubNodes().get(tag);
    if (result == null) {
      Iterator<TreeInfo> iter = getSubNodes().values().iterator(); 
      while (iter.hasNext() && result == null) {
        TreeInfo current = iter.next();
        result = current.findTarget(tag);
      }
    }
    return result;
  }

  public StringBuffer appendString(StringBuffer buffer) {
    buffer.append(getInfo().toString());
    Collection<Map.Entry<String,TreeInfo>> subNodes = getSubNodes().entrySet();
    if (!subNodes.isEmpty()) {
      buffer.append(".(");
      for(Iterator<Map.Entry<String,TreeInfo>> iter = subNodes.iterator(); iter.hasNext();) {
        Map.Entry<String,TreeInfo> entry = iter.next();
        buffer.append(entry.getKey());
        buffer.append("=");
        entry.getValue().appendString(buffer);
        if (iter.hasNext()) {
          buffer.append(",");
        }
      }
      buffer.append(")");
    }
    return buffer;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    appendString(buffer);
    return buffer.toString();
  }
}
