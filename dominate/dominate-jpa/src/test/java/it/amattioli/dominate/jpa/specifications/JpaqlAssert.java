package it.amattioli.dominate.jpa.specifications;

import java.io.StringReader;

import junit.framework.Assert;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.hql.antlr.HqlBaseLexer;

import antlr.Token;

public class JpaqlAssert {

	public static void areSameQuery(String s1, String s2) throws Exception {
		HqlBaseLexer lexer1 = new HqlBaseLexer(new StringReader(s1));
		HqlBaseLexer lexer2 = new HqlBaseLexer(new StringReader(s2));
		Token t1;
		Token t2;
		do {
			t1 = lexer1.nextToken();
			t2 = lexer2.nextToken();
			if (!ObjectUtils.equals(t1.getText(), t2.getText())) {
				Assert.fail("Not same query");
			}
		} while (!(t1.getText() == null && t2.getText() == null));
	}
	
}
