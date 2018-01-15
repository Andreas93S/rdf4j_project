all: run

maven:
	cd maven_test/my-app/ && mvn clean compile assembly:single

main: cpp/main.cpp
	cd cpp && g++ -o main.out -g \
	-I/usr/lib/jvm/java-8-openjdk-amd64/include/ \
	-I/usr/lib/jvm/java-8-openjdk-amd64/include/linux/ \
	-L/usr/bin/java \
	-L/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server/ \
	main.cpp \
	JNI_Helper.cpp \
	-ljvm

#run: maven main
run: main
	LD_LIBRARY_PATH="/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server" \
	./cpp/main.out

