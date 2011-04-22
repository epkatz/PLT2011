package backend;

public class Action {
	private String action;
	private double points;
	public Action(String action,double points){
		this.action=action;
		this.points=points;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Action other = (Action) obj;
		if (action.equals(other.action) && points==other.points)
			return true;
		return false;
	}
	public String toString() {
		return "Action [action=" + action + ", points=" + points + "]";
	}
	
}
