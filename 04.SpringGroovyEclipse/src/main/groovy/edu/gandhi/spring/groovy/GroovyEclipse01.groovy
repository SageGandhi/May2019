package edu.gandhi.spring.groovy

import java.time.LocalDateTime
//Install Eclipse Groovy Plugin https://github.com/groovy/groovy-eclipse/wiki
def currentDateTime = LocalDateTime.now()
println "Learning Groovy!!,Todays Date:${currentDateTime}"
def topics = ["Spring", "WebFlux", "SimpleStorageService"];
println "Printing All:${topics[0]}, ${topics[1]}, ${topics[2]}"
for(def index=0;index<topics.size;index++)println "Printing All:${topics[index]}"
for(topic in topics)println "Printing All:$topic"
//Range Can Be Created On Any Comparable Interface
def enum Days{MonDay,TuesDay,WednesDay,ThursDay,FriDay,SaturDay,SunDay}
for(def weekDay in Days.MonDay..Days.FriDay)print "WeekDay:$weekDay|"
println "Days Extents From & To:${(Days.MonDay..Days.SunDay).from} & ${(Days.MonDay..Days.SunDay).to}"
def isEven(def value) {return value%2==0}//Function Definition
println "10 Is:${isEven(10)} But 5 Is:${isEven(5)}"
/*
 There are two ways of constructing a software design: One way is to
 make it so simple that there are obviously no deficiencies, and the
 other way is to make it so complicated that there are no obvious
 deficiencies. The first method is far more difficult.➤ Sir Charles Antony Richard Hoare
 */