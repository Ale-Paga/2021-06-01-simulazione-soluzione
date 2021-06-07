package it.polito.tdp.genes.model;

public class Adiacente implements Comparable<Adiacente>{
	private Genes gene;
	private Double peso;
	public Adiacente(Genes gene, Double peso) {
		super();
		this.gene = gene;
		this.peso = peso;
	}
	public Genes getGene() {
		return gene;
	}
	public void setGene(Genes gene) {
		this.gene = gene;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacente o) {
		// TODO Auto-generated method stub
		return -(this.peso.compareTo(o.peso));   //ci sta il - perché voglio ordine decrescente
		//return (int) (this.peso-o.peso); non posso usarlo perché sono dei double e il return è int quindi troncherei il valore dopo la virgola --> 0.7-0.2 = 0.5 --> 0 li vede come uguali
	}
	
	

}
