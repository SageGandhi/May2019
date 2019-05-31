package edu.gandhi.spring.groovy

import java.util.regex.Matcher
import java.util.regex.Pattern

//■ The regex find operator, =~
//■ The regex match operator, ==~ More Restrictive Than Find Operation
//■ The regex pattern operator, ~Pattern
println "Prajit Gandhi"==/Prajit Gandhi/
//Expanding WildCard Characters Are Greedy
def toungueTwister = 'She Sells Sea Shells At The Sea Shore Of Seychelles'
def findSea = (toungueTwister =~ /S.a/);println findSea instanceof Matcher
'The rain in Spain stays mainly in the plain!'.eachMatch(/\b\w*ain\b/) { println it }
def (firstName,activity,language) ="Prajit Learns Groovy" =~ /\S+/ //Non WhiteSpace Character
println "FirstName=>${firstName},Activity=>${activity},Language=>${language}"
(firstName,activity,language) ="Prajit:Gandhi Activity:Learns Topic:Groovy" =~ /(\S+):(\S+)/ //Non WhiteSpace Character
println "FirstName=>${firstName},Activity=>${activity},Language=>${language}"
def matchKeyValue="Prajit:Gandhi Activity:Learns Topic:Groovy" =~ /(\S+):(\S+)/
matchKeyValue.each {fullMatch,key,value->println "Matcher:${fullMatch}=>${fullMatch.size()},${key}=>${key.size()},${value}=>${value.size()}"}
def namePattern = ~/\bPrajit\b/;println "Instance Of Pattern=>${namePattern instanceof Pattern}"
Pattern sixLetterWord = ~/\w{6}/;println sixLetterWord.isCase("Prajit");println "Gandhi" in sixLetterWord;
["Prajit","Learns","Groovy","First","Time"].each { value->
	switch(value) {
		case sixLetterWord: println "${value} Is Six Letter Word.";break;
		default :println "${value} Is Not Six Letter Word.";break;			
	}
}
println "All Six Letter Word".plus(["Prajit","Learns","Groovy","First","Time"].grep(sixLetterWord));
(10..15).eachWithIndex { item,index->println "Item:${item}@Index:${index}"}//Page:125
