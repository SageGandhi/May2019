package edu.gandhi.spring.groovy

@Grab('commons-lang:commons-lang:2.4')
import org.apache.commons.lang.ClassUtils

import groovy.transform.Immutable
import groovy.transform.TypeChecked

class OuterClass{
	class InnerClass{
	}
	void innerOuterClassAssertion() {
		assert !ClassUtils.isInnerClass(OuterClass)
		assert ClassUtils.isInnerClass(OuterClass.InnerClass)
	}
	@TypeChecked//Fourty2 Will Give Compile Time Error
	int answerToEveryThing() {42/*"Fourty2"*/}
}
//Create Another Script File In Intellij,@Grab Works In Intellij,Check In Groovy Console=>Script=>Inspect Ast
//new OuterClass().innerOuterClassAssertion()
@Immutable
class Money{
	int amount;
	String currency;
	Money plus(Money other) {
		if(!other) return this
		if(other.currency!=currency) throw new IllegalArgumentException("Can't Add $other.currency To $currency")
		return new Money(amount: amount+other.amount,currency: currency)
	}
	Money plus(Integer other) {
		return new Money(amount: amount+other,currency: currency)
	}
}