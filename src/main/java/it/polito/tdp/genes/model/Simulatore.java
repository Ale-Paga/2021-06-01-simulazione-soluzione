package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	////Coda degli eventi
	private PriorityQueue<Evento> queue;
	
	////Modello del mondo
	//due possibilità
	// 1) dato un ingegnere (0...n-1), dimmi su quale gene lavora
	private List<Genes> geneStudiato;  //geneStudiato.get(nIng)
	// 2) dato un gene, dimmi quandti ingegneri ci lavorano
	// private Map<Genes, Integer> numIngegneri;
	
	
	//private Graph<Genes, DefaultWeightedEdge> grafo;
	
	////Parametri input
	private int nTotIng;
	private int TMAX = 36;
	private double probMantenereGene =0.3;
	private Genes startGene;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	
	////Valori output
	//li dedurremo dal gene studiato
	
	public Simulatore(Genes start, int n, Graph<Genes, DefaultWeightedEdge> gr) {
		this.startGene=start;
		this.nTotIng=n;
		this.grafo=gr;
		
		if(this.grafo.degreeOf(this.startGene)==0) {
			throw new IllegalArgumentException("Vertice partenza isolato");
		}
		
		//inizializza coda eventi
		this.queue=new PriorityQueue<Evento>();
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			queue.add(new Evento(0, nIng));
		}
		
		//inizializza mondo, creando un array con nTotIng valori pari a startGene
		this.geneStudiato = new ArrayList<Genes>();
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			this.geneStudiato.add(this.startGene);
		}
	}
	
	//public void init(Genes ge, Graph<Genes, DefaultWeightedEdge> gr, int n) {
		
		
		
		/*this.partenza= ge;
		this.grafo=gr;
		this.nTotIng=n;
		
		//stato iniziale
		this.t=1;
		this.lista= new HashMap<Genes, Integer>();
		
		for(Genes g: this.grafo.vertexSet()) {
			lista.put(g, 0);
		}
		
		//creo la coda
		this.queue= new PriorityQueue<Evento>();
		
		//inserisco privo evento
		this.queue.add(new Evento(t, partenza, nTotIng));*/
	//}
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			Evento ev = queue.poll();
			
			int T = ev.getT();
			int nIng = ev.getnIng();
			Genes g = this.geneStudiato.get(nIng);
			
			if(T<TMAX) {
				//cosa studierà nIng al mese T+1?
				if(Math.random()<this.probMantenereGene) {
					//mantieni
					this.queue.add(new Evento(T+1, nIng));
				}else {
					//cambia gene
					
					//calcolo la somma dei pesi degli adiacenti, S
					double S =0;
					for(DefaultWeightedEdge edge: this.grafo.edgesOf(g)){
						S+= this.grafo.getEdgeWeight(edge);
					}
					
					//estrai numero casuale R tra 0 e S
					Double R = Math.random()*S;
					
					//confronta r con le somme parziali dei pesi
					Genes nuovo =null;
					double somma=0.0;
					for(DefaultWeightedEdge edge: this.grafo.edgesOf(g)){
						somma+=this.grafo.getEdgeWeight(edge);
						if(somma>R) {
							nuovo= Graphs.getOppositeVertex(this.grafo,	 edge, g);
							break;
						}
					}
					
					this.geneStudiato.set(nIng, nuovo);
					this.queue.add(new Evento(T+1, nIng));
				}
			}
		}
		
		/*Evento e;
		
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
			
		}*/
	}
	
	public Map<Genes, Integer> getGeniStudiati(){
		Map<Genes, Integer> studiati = new HashMap<Genes, Integer>();
		for(int nIng=0; nIng<this.nTotIng; nIng++) {
			Genes g = this.geneStudiato.get(nIng);
			if(studiati.containsKey(g)) {
				studiati.put(g, studiati.get(g)+1);
			}else {
				studiati.put(g, 1);
			}
		}
		return studiati;
	}
}
