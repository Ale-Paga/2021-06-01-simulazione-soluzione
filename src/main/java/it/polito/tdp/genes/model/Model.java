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
	private List<Genes> essentialGenes;
	//private List<Adiacenza> archi;
	private List<Interactions> archi;
	
	public Model() {
	 this.dao = new GenesDao();
	 this.idMap = new HashMap<String, Genes>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		essentialGenes = dao.getEssentialGenes();
		for(Genes g: essentialGenes) {
			this.idMap.put(g.getGeneId(), g);
		}
		//aggiungere i vertici
		Graphs.addAllVertices(grafo, essentialGenes);
		
		//aggiungere archi
		//archi = dao.getAdiacenze();
		archi = dao.getInteractions(idMap);
		
		/*for(Adiacenza a: archi) {
			if( idMap.containsKey(a.getGeneID1()) && idMap.containsKey(a.getGeneID2()) ) {
				Genes g1 = idMap.get(a.getGeneID1());
				Genes g2 = idMap.get(a.getGeneID2());
				
				if( g1.getChromosome() == g2.getChromosome()) {
					Graphs.addEdge(grafo, g1, g2, 2*a.getPeso() );
				}else if( g1.getChromosome() != g2.getChromosome()){
					Graphs.addEdge(grafo, g1, g2, a.getPeso() );
				}
			}
			
		}*/
		
		for(Interactions arco: archi) {
			if(arco.getGene1().getChromosome() == arco.getGene2().getChromosome()) {
				Graphs.addEdge(this.grafo, arco.getGene1(), arco.getGene2(), Math.abs(arco.getExpressionCorr()*2.0));
			}else {
				Graphs.addEdge(this.grafo, arco.getGene1(), arco.getGene2(), Math.abs(arco.getExpressionCorr()));
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
	
	/*public List<Adiacenza> geniAdiacenti(Genes g){
		Set<DefaultWeightedEdge> adiac= grafo.outgoingEdgesOf(g);
		Set<DefaultWeightedEdge> adiac= grafo.EdgesOf(g); meglio questo
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		for(DefaultWeightedEdge d: adiac) {
			result.add(new Adiacenza(g.getGeneId(), Graphs.getOppositeVertex(grafo, d, g).getGeneId(), grafo.getEdgeWeight(d)));
		}
		Collections.sort(result);
		return result;
		
	}*/
	public List<Adiacente> geniAdiacenti(Genes g){
		List<Genes> vicini = Graphs.neighborListOf(this.grafo, g);
		
		List<Adiacente> result = new ArrayList<Adiacente>();
		for(Genes v: vicini) {
			result.add(new Adiacente(v, this.grafo.getEdgeWeight(this.grafo.getEdge(g, v))));
		}
		Collections.sort(result);
		return result;
		
	}
	
	public Map<Genes, Integer> simulaIngegneri(Genes start, int n){
		try {
			Simulatore sim = new Simulatore(start, n, grafo);
			sim.run();
			return sim.getGeniStudiati();
		}catch (IllegalArgumentException ex) {
			return null;
		}
		
	}
	
}
