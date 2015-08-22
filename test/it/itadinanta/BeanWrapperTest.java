package it.itadinanta;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import junit.framework.TestCase;

public class BeanWrapperTest extends TestCase {
	public void testWrap() {
		BeanTest aBean = new BeanTest();
		BeanWrapper w = new BeanWrapperImpl(BeanTest.class);
		w.setWrappedInstance(aBean);
		w.setPropertyValue("propertyOne", 1);
		assertEquals(aBean.getPropertyOne(), 1);
		w.setPropertyValue("propertyOneAsString", "2");
		assertEquals(aBean.getPropertyOne(), 2);
	}
}
