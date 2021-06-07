package it.polito.tdp.genes.model;

public class Evento implements Comparable<Evento>{
	
	private int t;  //tempo da 0 a 36
	private int nIng;  //numero ingegnere
	
	
	
	public Evento(int t, int nIng) {
		super();
		this.t = t;
		this.nIng = nIng;
	}



	public int getT() {
		return t;
	}



	public void setT(int t) {
		this.t = t;
	}



	public int getnIng() {
		return nIng;
	}



	public void setnIng(int nIng) {
		this.nIng = nIng;
	}

	@Override
	public int compareTo(Evento other) {
		return this.t-other.t ;
	}
}
