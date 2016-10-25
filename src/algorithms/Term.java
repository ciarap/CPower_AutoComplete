package algorithms;

public class Term {
	@Override
	public String toString() {
		return "Term [weight=" + weight + ", word=" + word + "]";
	}
	private double weight;
	private String word;
	
	public Term(String weight, String word)throws IllegalArgumentException {
		double weightInt=Double.parseDouble(weight.trim());
		if(weightInt>=0)
		 this.weight = weightInt;
		this.word = word;
	
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}
