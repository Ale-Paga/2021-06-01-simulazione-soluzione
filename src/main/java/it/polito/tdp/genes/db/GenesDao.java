package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT GeneID, Essential, Chromosome FROM Genes GROUP BY GeneID";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Genes> getEssentialGenes(){
		String sql = "SELECT g.GeneID, g.Essential, g.Chromosome "
				+ "FROM genes AS g "
				+ "WHERE g.Essential='Essential' "
				+ "GROUP BY g.GeneID ";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	


	public List<Adiacenza> getAdiacenze(){
		String sql = "SELECT i.GeneID1 AS g1, i.GeneID2 AS g2, ABS(i.Expression_Corr) AS corr "
				+ "FROM interactions AS i "
				+ "WHERE i.GeneID1 <> i.GeneID2 ";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Adiacenza ad = new Adiacenza(res.getString("g1"), res.getString("g2"), res.getDouble("corr"));
				result.add(ad);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Interactions> getInteractions(Map<String, Genes> idMap){
		String sql = "SELECT * "
				+ "FROM interactions ";
		List<Interactions> result = new ArrayList<Interactions>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes gene1 = idMap.get(res.getString("geneID1"));
				Genes gene2 = idMap.get(res.getString("geneID2"));
				if(gene1!=null && gene2!=null && !gene1.equals(gene2)) {
					result.add(new Interactions(gene1, gene2, res.getString("type"), res.getDouble("Expression_Corr")));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
