import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.impl.SimpleIRI;

public class rdf4j_test {

	public static void main(String[] args) {
		/*
		
		// We use a ValueFactory to create the building blocks of our RDF statements:
		// IRIs, blank nodes and literals.
		ValueFactory vf = SimpleValueFactory.getInstance();

		// We want to reuse this namespace when creating several building blocks.
		String ex = "http://example.org/";

		// Create IRIs for the resources we want to add.
		IRI picasso = vf.createIRI(ex, "Picasso");
		IRI artist = vf.createIRI(ex, "Artist");		
		
		ModelBuilder builder = new ModelBuilder();
		builder.setNamespace("ex", "http://example.org/")
			  		.subject("ex:Picasso")
			  			.add(RDF.TYPE, "ex:Artist")
			  			.add(FOAF.FIRST_NAME, "Pablo");
		Model model = builder.build();
		
		for (Statement statement: model) {
			System.out.println (statement);
		}
		
		Rio.write(model, System.out, RDFFormat.RDFXML);	
		
		*/
		
		//*
		
		Repository repo = new SailRepository(new MemoryStore());
		repo.initialize();
		
		try (RepositoryConnection repoConnection = repo.getConnection())
		{	
			// Add statement to connection
			ValueFactory vf = repo.getValueFactory();
			IRI scene1 = vf.createIRI ("http://Scene1");
			IRI identifiedModel = vf.createIRI ("http://IdentifiedModel");
			IRI model1 = vf.createIRI ("http://Model1");
			IRI score = vf.createIRI ("http://Score");
			Literal scoreValue = vf.createLiteral (0.3);
			repoConnection.add (scene1, identifiedModel, model1);
			repoConnection.add (model1, score, scoreValue);
		}
		catch (RDF4JException e) 
		{
		   // handle exception. This catch-clause is
		   // optional since RDF4JException is an unchecked exception
		}
		
		//*/
	}
}

















