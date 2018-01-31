#include <iostream>
#include <stdexcept>

#include "rdf_connector.hpp"

int main(int argc, char** argv) {
	RDF_Connector rdf("./maven_test/rdf4j_connector/target/rdf4j_connector-1.0-SNAPSHOT.jar");
	rdf.set_class("Connector");
	rdf.initialize("http://vm25.cs.lth.se/rdf4j-server", "object_repo");

	rdf.add_model(1, 3, "model", 0.1265, "pose");
	std::cout << "Model successfully added, connection is working!" << std::endl;
	std::cout << "Shutting down..." << std::endl;

	return 0;
}


