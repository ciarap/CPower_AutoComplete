package algorithms;

public class Term implements Comparable {

	private double weight;
	private String word;

	public Term(String weight, String word)throws IllegalArgumentException {
		if (weight==null || word==null){
			weight="0";
			word="empty";
		}
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
	@Override
	public int compareTo(Object that) {
		
		return (int) ((int) ((Term) that).getWeight()-this.weight);
	}
	


	

}
