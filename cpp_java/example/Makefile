all: run

Main.class: Main.java
	javac Main.java

main: main.cpp
	g++ -o main.out -g \
	-I/usr/lib/jvm/java-8-openjdk-amd64/include/ \
	-I/usr/lib/jvm/java-8-openjdk-amd64/include/linux/ \
	-L/usr/bin/java \
	-L/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server/ \
	main.cpp \
	../JNI_Helper.cpp \
	-ljvm

run: Main.class main
	LD_LIBRARY_PATH="/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/server" \
	./main.out

clean:
	rm -f Main.class main.out
