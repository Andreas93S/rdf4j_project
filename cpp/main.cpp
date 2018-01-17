#include <map>
#include <iostream>
#include <stdexcept>

#include "RDF_Connector.hpp"

std::map<std::string, jmethodID> mids;

int main(int argc, char** argv) {
	RDF_Connector jh("./maven_test/rdf4j_connector/target/rdf4j_connector-1.0-SNAPSHOT.jar");
	jh.set_class("Connector");
	jh.initialize("http://vm25.cs.lth.se/rdf4j-server", "object_repo");

	jh.add_model(1, 3, "model", 0.1265, "pose");

	return 0;
}


