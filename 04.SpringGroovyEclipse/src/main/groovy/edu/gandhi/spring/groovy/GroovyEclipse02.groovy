package edu.gandhi.spring.groovy
//With Grape We Can Do Maven Like Dependency Management
//@Grapes(@Grab(group='org.apache.commons', module='commons-collections4', version='4.3'))
import java.time.LocalDateTime
import org.springframework.core.io.ClassPathResource

import groovy.util.slurpersupport.Attributes
import groovy.xml.StreamingMarkupBuilder

def firstClosure = {println "Inside A Closure,Execution Deffered: ${LocalDateTime.now()} & $it"}
//for(def timeIndex in 1..5) {firstClosure(timeIndex);sleep(100);}
//(5..10).each { arg-> println "Inside A Each Closure,Execution Deffered: ${LocalDateTime.now()} & $arg";sleep(100); }
//(1..10).findAll { it%2==0 }.each { println "Even No:$it|" }
println "If it walks like a duck and it quacks like a duck, then it must be a duck-DuckTyping"
//def fileGpx = new File("C:/PrajitWs/Work/StsWs/04.GroovyEclipse/src/FellsLoop.gpx")
def content = new XmlSlurper().parse(new ClassPathResource("FellsLoop.gpx").getFile())
println "Start Dumping=>${content.name.value.dump()}"
println "Start Inspecting=>${content.name.value.inspect()}"
println "${content.name}=>${content.desc} Created By ${content.@creator}"
content.rte.rtept.each { println "${it.@lat},${it.@lon}=>${it.time}" }
content.with {
	println attributes()['creator']
	println attributes()['version']
}
def streamingMarkupBuilder = new StreamingMarkupBuilder()
println streamingMarkupBuilder.bind{
	WayPoint{
		//Root Element
		content.rte.rtept.each { point->
			Point(Time: point.time.toString()){
				Latitude(point.@lat)//Child Element Instead Of Attribute
				Longitude(point.@lon)//Child Element Instead Of Attribute
			}
		}
	}
}.toString()//StreamingMarkupBuilder Does Lazy Evaluation Until ToString Invoked
//F[ast]I[solated]R[epeatable]S[elf-Verifyable]T[imely] Principle Of Unit Test Brett Schuchert,Tim Ottinger// Use Spock Or Gmock
//We CanNot Verify The Interaction In Stub But Can Do It In Mock.Mock Help Us Todo Interaction Testing.