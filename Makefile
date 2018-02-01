all: run

maven_rule: maven/rdf4j_connector/src/main/java/*
	cd maven/rdf4j_connector/ && \
	mvn package

main: cpp/main.cpp
	g++ -std=c++11 \
	-o cpp/main.out -g \
	-I/usr/lib/jvm/java-8-openjdk-amd64/include/ \
	-I/usr/lib/jvm/java-8-openjdk-amd64/include/linux/ \
	-L/usr/bin/java \
	-L/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server/ \
	cpp/main.cpp \
	cpp/jni_helper.cpp \
	cpp/rdf_connector.cpp \
	-ljvm

run: maven_rule main
	LD_LIBRARY_PATH="/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server" \
	cpp/main.out

clean: 
	cd maven/rdf4j_connector/ && \
	mvn clean
	rm cpp/main.out
