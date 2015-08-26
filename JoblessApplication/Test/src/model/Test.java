package model;

public class Test {

	private String questionNum;
	private String questionDescription;
	private String answer;
	
	public String getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(String questionNum) {

		this.questionNum = questionNum;
	}
	
	public String getQuestionDescription() {

		return questionDescription;
	}
	
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Test [questionNum=" + questionNum + ", questionDescription="
				+ questionDescription + "]";
	}

	
}
