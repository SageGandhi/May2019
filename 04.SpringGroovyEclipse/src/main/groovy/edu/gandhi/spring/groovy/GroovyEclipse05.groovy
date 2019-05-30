package edu.gandhi.spring.groovy

import java.awt.Rectangle

def romanCharacter = ['', 'I', 'II', 'III', 'IV', 'V', 'VI', 'VII','VIII'],
	httpStatusCodes=[100:'Continue',200:'Ok',400:'Bad Request',500:'Internal Server Error'],
	totalHandShakes = 0
1.upto(100) {guestNumber ->totalHandShakes += (guestNumber-1)}
println "Handshake:${totalHandShakes}"
//println "${'123456789'=~ /\d+/} & ${romanCharacter} & ${httpStatusCodes}"
httpStatusCodes.each { key,value->println"${key}==${value}" }
println "Evaluating @ Runtime:${evaluate("10**10")}"
//customeMethod(Params) Invocation Converted To getMetaClass().invokeMethod(this, "customeMethod", [Params])
//Method Calls Are Redirected Through The Object’s MetaClass
println "Do not worry about your difficulties in mathematics. I can assure you mine are still greater.—Albert Einstein"
println "${123G.getClass().getName()} & ${1.23G.getClass().getName()}"
java.awt.Point _origin = [0,0];_origin = [x:25,y:25]; 
def squareRectangle = new Rectangle();squareRectangle.size = [width: 100, height:100];squareRectangle.location = [x:25,y:25];
println "Origin:${_origin} ,Rectangle:${squareRectangle}"