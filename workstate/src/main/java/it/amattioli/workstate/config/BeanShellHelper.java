package it.amattioli.workstate.config;

import bsh.Interpreter;
import bsh.EvalError;

public class BeanShellHelper {
	public static Interpreter i = new Interpreter();

	private BeanShellHelper() {
	}

	/**
	 * Evaluate a BeanShell expression. This method interprets the string parameter
	 * as a BeanShell expression and returns the result.
	 * 
	 * @param expr the string containing the evaluating expression
	 *            
	 * @return the result of the expression evaluation
	 * @throws EvalError if the expression has errors
	 * 
	 */
	public static Object evalExpr(String expr) throws EvalError {
		String exprResult = "expressionResult";
		StringBuffer code = new StringBuffer("Object ")
		                    .append(exprResult)
				            .append(" = ")
				            .append(expr)
				            .append(";");
		i.eval(code.toString());
		return i.get(exprResult);
	}

}
