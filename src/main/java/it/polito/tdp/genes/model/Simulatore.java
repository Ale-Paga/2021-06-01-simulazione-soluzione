package it.polito.tdp.genes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	//Coda degli eventi
	private PriorityQueue<Evento> queue;
	
	//Modello
	private Graph<Genes, DefaultWeightedEdge> grafo;
	
	//Parametri simulazione
	private int nIng;
	private Genes partenza;
	private int t=-1;
	
	//Valori output
	private Map<Genes, Integer> lista;
	
	public void init(Genes ge, Graph<Genes, DefaultWeightedEdge> gr, int n) {
		this.partenza= ge;
		this.grafo=gr;
		this.nIng=n;
		
		//stato iniziale
		this.t=1;
		this.lista= new HashMap<Genes, Integer>();
		
		for(Genes g: this.grafo.vertexSet()) {
			lista.put(g, 0);
		}
		
		//creo la coda
		this.queue= new PriorityQueue<Evento>();
		
		//inserisco privo evento
		this.queue.add(new Evento(t, partenza, nIng));
	}
	
	public void run() {
		Evento e;
		
		while( t<37) {
			//simulo e
			e=this.queue.poll();
			this.t=e.getT();
			
			double random=0;
			int nIngegneri = e.getnIng();
			Genes genes = e.getGenes();
			
			//vicini del gene
			List<Genes> vicini = Graphs.neighborListOf(this.grafo, this.partenza);
			
			for(int i=1; i<= e.getnIng(); i++) {
				random = Math.random();
				if(random>=0.3) {
					
				}
			}
			
		}
	}
}
