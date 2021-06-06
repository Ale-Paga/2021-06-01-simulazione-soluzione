package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private GenesDao dao;
	private Map<String, Genes> idMap;
	private List<Genes> vertici;
	private List<Adiacenza> archi;
	
	public Model() {
	 this.dao = new GenesDao();
	 this.idMap = new HashMap<String, Genes>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vertici = dao.getEssentialGenes();
		for(Genes g: vertici) {
			this.idMap.put(g.getGeneId(), g);
		}
		//aggiungere i vertici
		Graphs.addAllVertices(grafo, vertici);
		
		//aggiungere archi
		archi = dao.getAdiacenze();
		
		for(Adiacenza a: archi) {
			if( idMap.containsKey(a.getGeneID1()) && idMap.containsKey(a.getGeneID2()) ) {
				Genes g1 = idMap.get(a.getGeneID1());
				Genes g2 = idMap.get(a.getGeneID2());
				
				if( g1.getChromosome() == g2.getChromosome()) {
					Graphs.addEdge(grafo, g1, g2, 2*a.getPeso() );
				}else if( g1.getChromosome() != g2.getChromosome()){
					Graphs.addEdge(grafo, g1, g2, a.getPeso() );
				}
			}
			
		}
	}
	
	public int getNVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNArchi(){
		return grafo.edgeSet().size();
	}
	
	public Set getVertici() {
		return grafo.vertexSet();
	}
	
	public List<Adiacenza> geniAdiacenti(Genes g){
		Set<DefaultWeightedEdge> adiac= grafo.outgoingEdgesOf(g);
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		for(DefaultWeightedEdge d: adiac) {
			result.add(new Adiacenza(g.getGeneId(), Graphs.getOppositeVertex(grafo, d, g).getGeneId(), grafo.getEdgeWeight(d)));
		}
		Collections.sort(result);
		return result;
		
	}
	
}
