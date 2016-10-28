package algorithms;

public class Term {

	private double weight;
	private String word;

	public Term(String weight, String word)throws IllegalArgumentException {
		double weightDouble=Double.parseDouble(weight.trim());
		if(weightDouble>=0)
			this.weight = weightDouble;
		else {
			this.weight=0.0;
		}
		this.word = word;

	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		if(weight>0){
			this.weight = weight;
		}
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		if(word!=null && word!=""){
			this.word = word;
		}
	}
	@Override
	public String toString() {
		return "Term [weight=" + weight + ", word=" + word + "]";
	}

}
