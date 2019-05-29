package edu.gandhi.spring.groovy

import java.time.LocalDate
import java.time.Month

//import org.springframework.core.io.ClassPathResource

println new Java2Groovy(firstName: "Prajit",lastName: "Gandhi",email: "Prajit.Gandhi@gmail.com",dob: java.sql.Date.valueOf(LocalDate.of(1988, Month.FEBRUARY, 22)))
//println new ClassPathResource("Jenkins2Learning.md").getFile().eachLine { lineContent->println "$lineContent" }
[String,List,File,Map].each { clazz->print "|$clazz.package.name|" }
println "${[String,List,File,Map]*.package*.name}"
def xmlContent = "<Customers><Corporate>"
		.plus("<Customer name='Bill Gates' company='Microsoft' />")
		.plus("<Customer name='Tim Cook' company='Apple' />")
		.plus("<Customer name='Larry Ellison' company='Oracle' />")
		.plus("</Corporate></Customers>")
def customers = new XmlSlurper().parseText(xmlContent)//Lazy Evaluation
for(def customer in customers.Corporate.Customer) {
	println "${customer.@name} Works For ${customer.@company}"
}
//A BackSlash At End Of Line Discards LineFeed
println "GROOVY “A situation or an activity that one enjoys or to which one is especially well suited found his groove playing bass in a trio). A very \
	pleasurable experience; enjoy oneself (just sitting around, grooving on the music). To be affected with pleasurable excitement.\
	To react or interact harmoniously.”(http://dict.leo.org)."
println "${['http://www.groovy-lang.org','http://gpars.codehaus.org','http://gr8conf.org/']*.toURL()}"
println "Do what you think is interesting, do something that you think is fun and worthwhile, because otherwise you won’t do it well anyway.—Brian Kernighan"
//#!/usr/bin/env groovy Sha[rp]Bang Need Execute Permission For This Script File Containing ShaBang,Program Loader Use First Line As Interpreter Directive
println "${URLEncoder.encode 'PrajitP@ssw0rd1$','UTF-8'}" //=>java.net.URLEncoder.encode("PrajitP@ssw0rd1$", "UTF-8");
//Auto Imported Packages groovy.lang.*, groovy.util.*,java.lang.*, java.util.*, java.net.*, java.io.*, java.math.BigInteger,java.math.BigDecimal
//def expectedVal = 13,actualVal = 6;assert expectedVal == actualVal+actualVal
assert ("Pra"*3<<"Gandhi").size() == 3*3+6
/**
 * Method Are Public By Default,Phases Of Compilation AST Transformation:
 * Initialization->Parsing->Conversion->Semantic Analysis->Canonicalization->
 * Instruction Selection->Class Generation->Output->Finalization
 */
//Compile Time MetaProgramming Using A[bstract]S[yntax]T[ree] Transformation Like @groovy.transform.ToString/@groovy.transform.Immutable
