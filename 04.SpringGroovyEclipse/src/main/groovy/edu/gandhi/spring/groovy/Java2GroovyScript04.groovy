package edu.gandhi.spring.groovy

import java.time.LocalDate
import java.time.Month

println new Java2Groovy(firstName: "Prajit",lastName: "Gandhi",email: "Prajit.Gandhi@gmail.com",dob: java.sql.Date.valueOf(LocalDate.of(1988, Month.FEBRUARY, 22)))