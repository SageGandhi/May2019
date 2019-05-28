package edu.gandhi.spring.groovy
//Used For Jdbc Jar Loading,Need To UnderStand:Set to true if you want to use the system classloader when loading the grape.
//This is normally only required when a core Java class needs to reference the grabbed classes, for a database driver accessed
//using DriverManager.
@GrabConfig(systemClassLoader=true)
import groovyx.net.http.RESTClient

def url = new ConfigSlurper().parse(Configuration.class)
def client = new RESTClient(url.HttpBinGetUrl),
response = client.get(Collections.emptyMap())
println "${response.data}=>${response.status}"
println "Email:${response.data.args.Email},"+
		"FirstName:${response.data.args.FirstName},"+
		"LastName:${response.data.args.LastName}"