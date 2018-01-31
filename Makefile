all: run

maven:
	cd maven_test/rdf4j_connector/ && \
	mvn package
	#mvn test-compile && \
	#mvn test-compile assembly:single


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

run: maven main
	LD_LIBRARY_PATH="/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server" \
	cpp/main.out

clean: 
	cd maven_test/rdf4j_connector/ && \
	mvn clean
	rm cpp/main.out
