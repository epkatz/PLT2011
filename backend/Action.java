
public class Action {
	private String action;
	private float points;
	
	/**Constructor.
	 * 
	 * @param String action
	 * @param float points
	 */
	public Action(String action,double points){
		this.action=action;
		this.points=(float)points;
	}
	
	/**Returns the string representation of the action
	 * 
	 * @return String action
	 */
	public String getAction() {
		return action;
	}
	
	/**Sets the action
	 * 
	 * @param String action
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	/**Return the points gained for completing the action
	 * 
	 * @return float points
	 */
	public float getPoints() {
		return points;
	}
	
	/**Sets the points gained for completing the action
	 * 
	 * @param float points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**Determines if two actions are equal by comparing
	 * the action and points received
	 * 
	 * @return boolean isEqual
	 */
	public boolean equals(Object obj){
		if (this == obj)	//Reference to seld
			return true;
		if (obj == null)	//Null reference
			return false;
		if (getClass() != obj.getClass())	//Not the same class
			return false;
		final Action other = (Action) obj;
		if (action.equals(other.action) && points==other.points)	//Equal action and points
			return true;
		return false;
	}
	
	/**Returns a string representation of the action
	 * 
	 * @return String representation
	 */
	public String toString() {
		return "Action [action=" + action + ", points=" + points + "]";
	}
	
}
