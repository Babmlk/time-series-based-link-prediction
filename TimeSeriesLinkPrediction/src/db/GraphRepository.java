package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import model.Edge;
import model.Graph;

public class GraphRepository {

	public Hashtable<String, Integer> map;
	public int seq;

	public GraphRepository(){
		this.map = new Hashtable<String, Integer>();
		this.seq = 0;
	}

	private	Graph buildGraph(ResultSet rs) throws SQLException{
		Graph graph = new Graph();
		while(rs.next()){
			String autor1 = rs.getString("autor1");
			String autor2 = rs.getString("autor2");
			int iAutor1 = 0;
			int iAutor2 = 0;
			if(this.map.containsKey(autor1)){
				iAutor1 = this.map.get(autor1);
			}else{
				iAutor1 = this.seq++;
				this.map.put(autor1, iAutor1);
			}
			if(this.map.containsKey(autor2)){
				iAutor2 = this.map.get(autor2);
			}else{
				iAutor2 = this.seq++;
				this.map.put(autor2, iAutor2);
			}
			String edgeId = iAutor1 + "#" + iAutor2;
			graph.addEdge(new Edge(edgeId), iAutor1, iAutor2);			
		}
		return graph;
	}

	/**
	 * Recupera um grafo por ano e categoria. A funcao map transforma o
	 * nome do autor em um inteiro.
	 * 
	 * @param ano
	 * @param categoria
	 * @param map
	 * @return
	 * @throws  
	 */
	public Graph retrieveGraphByYearAndCategory(int year, String category){
		Graph graph = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT DISTINCT autor1, autor2 ");
			buffer.append("FROM coautoria_arxiv ");
			buffer.append("WHERE ano = ? ");
			buffer.append("AND categoria = ? ");
			buffer.append("GROUP BY autor1, autor2 ");

			Connection connection = DataBase.getConnection();
			PreparedStatement stmt = connection.prepareStatement(buffer.toString());
			
			stmt.setInt(1, year);
			stmt.setString(2, category);
			ResultSet rs = stmt.executeQuery();
			
			graph = this.buildGraph(rs);
			
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;
	}
}
