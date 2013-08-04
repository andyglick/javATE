package it.amattioli.workstate.actions;

/**
 * A guard is a condition that allows a choice between various transitions having
 * the same start state and the same triggering event.
 * 
 * @author andrea
 *
 */
public interface Guard {

	/**
	 * Check if the condition is satisfied or not. The check can be performed
	 * using event parameters and state attributes.
	 * 
	 * @param event the event that triggered the transition to which this guard
	 *        is associated
	 * @param state the state that completely contains the transition to which
	 *        this guard is associated
	 * 
	 */
	public boolean check(AttributeReader event, AttributeReader state);

	/**
	 * Return the priority level for the guard evaluation.
	 * To choose the right transition to trigger all the guards must be evaluated
	 * to know which is true. Normally all the guards should be indipendent
	 * one each other and the evaluation order should not be important. But
	 * to implement an "else" mechanism a priority has been associated to the guards.
	 * The null guard has a very low priority while all user defined guards have
	 * a high priority. In this way the null guard is always evaluated as the last
	 * guard and, as it always returns true, it works like an "else" branch.
	 * The priority should not be used for other purposes like forcing the evaluation
	 * order of user defined guards.
	 * 
	 */
	public Integer getPriority();

}