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
(10..<15).eachWithIndex { item,index->println "Item:${item}@Index:${index}"}//Page:125 Half Exclusive
//https://github.com/danvega/apache-groovy-course.git
println "14 BoundsWithIn(10..<15): ${(10..<15).containsWithinBounds(14)} And (10..15) Is Instance Of Range: ${(10..15) instanceof Range}"
def currentAge = 31;//Switch Case Using Range
switch(currentAge) {
	case 0..<25: println "Age Below 25";break;
	case 25..<50: println "Age Below 50 And Above 25";break;
	case 50..<75: println "Age Below 75 And Above 50";break;
	default: println "Age Above Or EqualTo 75";break;
}
println "Ages Between 20..35:${[10,20,30,40,50,25,35,45,15].grep(20..35)}"
//Range Must Implemet next(++),previous(--) Operation & Comparable[compareTo] Interface
class WeekDay implements Comparable{
	static final AllDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
	private int index = 0
	WeekDay(String day) {this.index = AllDays.indexOf(day)}
	WeekDay next() {return new WeekDay(AllDays[this.index+1%AllDays.size()])}
	WeekDay previous() {return new WeekDay(AllDays[this.index-1])}
	int compareTo(Object other) {return this.index<=>other.index}//SpaceSHip Operator
	String toString() {return AllDays[this.index];}
}
def monday = new WeekDay('Mon'),friday = new WeekDay('Fri')
for(day in monday..friday)print "=>${day}"
def alphaList= ['A','B','C','D','E','F']
println "AlphaList[0..2]=>${alphaList[0..2]}";println "AlphaList[0,2,4]=>${alphaList[0,2,4]}"
alphaList[0..2]=['X','Y','Z'];println "AlphaList=>${alphaList}"
alphaList[3..5] = [];alphaList[1..1] = [0, 1, 2];println "AlphaList=>${alphaList}"
alphaList<<'Prajit'<<'Gandhi';println "AlphaList<<'Prajit'<<'Gandhi'=>${alphaList}"
println "AlphaList * 2=>${alphaList*2}";println "AlphaList - Prajit=>${alphaList - 'Prajit'}"
def assortedList = [[1,0],[2],[3,5,4]];assortedList = assortedList.sort {item->item.size()};
println "Sort List By Size:${assortedList}";
[1,2,3,4,5].inject(0) {handShakesIntermediate,item-> 
	temp=handShakesIntermediate+item;
	println"Until Now:${handShakesIntermediate}+${item}:${temp}";
	return temp; 
}//Fold Or Reduce Operation In Other Functional Language
[1,2,3,4,5].inject(1) {factorial,item-> 
	temp=factorial*item;
	println"Until Now:${factorial}*${item}:${temp}" 
	return temp;
}//Fold Or Reduce Operation In Other Functional Language
def quickSort(arrToSort) {
	if(arrToSort.size()<2)return arrToSort;
	def pivotElement = arrToSort[arrToSort.size().intdiv(2)],//size & getAt(index)
		leftSubList= arrToSort.findAll { item->item<pivotElement },//findAll
		rightSubList= arrToSort.findAll { item->item>pivotElement },
		middleEqToPivot = arrToSort.findAll { item->item==pivotElement }
	return quickSort(leftSubList).plus(middleEqToPivot).plus(quickSort(rightSubList));
}
println "Sorting Using QuickSort:${quickSort([10,100,20,90,30,80])}"
println "${[new URL('http', 'prajit.gandhi.com', 80, 'index.html'),\
	new URL('https', 'prajit.gandhi.com', 443, 'signout.html'),\
	new URL('ftp', 'prajit.gandhi.com', 21, 'downloads')\
].findAll {it.port<100}.collect {it.file.toLowerCase()}.sort()}"//Page 142