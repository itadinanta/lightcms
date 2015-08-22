package it.itadinanta;

public class BeanTest {
	public int propertyOne;

	public String propertyTwo;

	public int getPropertyOne() {
		return propertyOne;
	}

	public void setPropertyOne(int a) {
		this.propertyOne = a;
	}
	
	public void setPropertyOneAsString(String a) {
		this.propertyOne = Integer.parseInt(a);
	}
	
	public String getPropertyTwo() {
		return propertyTwo;
	}

	public void setPropertyTwo(String b) {
		this.propertyTwo = b;
	}
}
