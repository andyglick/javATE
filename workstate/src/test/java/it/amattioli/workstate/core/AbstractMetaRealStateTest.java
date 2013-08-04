package it.amattioli.workstate.core;

import it.amattioli.workstate.core.MetaAttribute;
import it.amattioli.workstate.core.MetaRealState;

public abstract class AbstractMetaRealStateTest extends AbstractMetaStateTest {
     
   public void testAddAttribute() throws Exception {
     MetaRealState testingState = (MetaRealState)this.testingState;
     MetaAttribute attr = new MetaAttribute("attr",String.class,"");
     testingState.addAttribute(attr);
     assertTrue(testingState.isAllowedAttribute("attr"));
   }
   
   public void testAddNullAttribute() {
     MetaRealState testingState = (MetaRealState)this.testingState;
     try {
       testingState.addAttribute(null);
     } catch(NullPointerException e) {
       return;
     }
     fail("Ho aggiunto un attributo nullo ad un meta-stato");
   }
   
   public void testAddDuplicateAttribute() throws Exception {
     MetaRealState testingState = (MetaRealState)this.testingState;
     MetaAttribute attr1 = new MetaAttribute("attr1",String.class,"");
     testingState.addAttribute(attr1);
     MetaAttribute attr2 = new MetaAttribute("attr1",String.class,"");
     try {
       testingState.addAttribute(attr2);
     } catch(IllegalArgumentException e) {
       return;
     }
     fail("Ho aggiunto un attributo duplicato ad un meta-stato");
   }
     
}
