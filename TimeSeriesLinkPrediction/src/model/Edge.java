package model;

public class Edge {

	private String id;
	private int source;
	private int target;
	
	public Edge(String id){
		String[] nodes = id.split("#");
		this.source = Math.min(Integer.parseInt(nodes[0]), Integer.parseInt(nodes[1]));
		this.target = Math.max(Integer.parseInt(nodes[0]), Integer.parseInt(nodes[1]));
		this.id = this.source + "#" + this.target;
	}
	
	public Edge(Integer source, Integer target){
		this.source = Math.min(source, target);
		this.target = Math.max(source, target);
		this.id = this.source + "#" + this.target;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	@Override
	public boolean equals(Object obj){
		Edge edge = (Edge) obj;
		return (this.id.equals(edge.getId()));
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode();
	}
	
	@Override
	public String toString(){
		return this.id;
	}
	
}
