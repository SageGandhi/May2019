package edu.gandhi.spring.groovy

@Grab('commons-lang:commons-lang:2.4')
import org.apache.commons.lang.ClassUtils

class OuterClass{
	class InnerClass{
	}
	void innerOuterClassAssertion() {
		assert !ClassUtils.isInnerClass(OuterClass)
		assert ClassUtils.isInnerClass(OuterClass.InnerClass)
	}
}
//Create Another Script File In Intellij,@Grab Works In Intellij,Check In Groovy Console=>Script=>Inspect Ast
//new OuterClass().innerOuterClassAssertion()