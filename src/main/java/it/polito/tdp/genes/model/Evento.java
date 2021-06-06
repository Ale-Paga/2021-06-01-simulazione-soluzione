package it.polito.tdp.genes.model;

public class Evento implements Comparable<Evento>{
	
	private int t;
	private Genes genes;
	private int nIng;
	
	public Evento(int t, Genes genes, int nIng) {
		super();
		this.t = t;
		this.genes = genes;
		this.nIng = nIng;
	}


	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.t-o.t;
	}


	public int getT() {
		return t;
	}


	public void setT(int t) {
		this.t = t;
	}


	public Genes getGenes() {
		return genes;
	}


	public void setGenes(Genes genes) {
		this.genes = genes;
	}
	
	public void nIngMeno1() {
		this.nIng=this.nIng-1;
	}


	public int getnIng() {
		return nIng;
	}


	public void setnIng(int nIng) {
		this.nIng = nIng;
	}

}
