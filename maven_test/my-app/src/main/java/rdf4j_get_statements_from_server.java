


import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;


public class rdf4j_get_statements_from_server {

	public static void main(String[] args) {
		String rdf4jServer = "http://vm25.cs.lth.se/rdf4j-server";
		String repositoryID = "object_repo";
		Repository repo = new HTTPRepository (rdf4jServer, repositoryID);
		repo.initialize();
		
		try (RepositoryConnection repoConnection = repo.getConnection())
		{
			if (repoConnection.isOpen())
			{
				System.out.println ("Connection is open!");
			}
			else
			{
				System.out.println ("Connection is not open!");
			}
				
			if (repo.isWritable())
			{
				System.out.println ("Repository is writable!");
			}		
			else
			{
				System.out.println ("Repository is not writable!");
			}
			
			if (repoConnection.isEmpty())
			{
				System.out.println ("Repository is empty!");
			}		
			else
			{
				System.out.println ("Repository is not empty!");
			}
			
			System.out.println ("\n");
			
			try (RepositoryResult<Statement> statements = repoConnection.getStatements(null, null, null))
			{
			   	while (statements.hasNext()) 
			   	{
			  		Statement st = statements.next();
		 			// the subject is always `ex:VanGogh`, an IRI, so we can safely cast it
					Resource subject = (IRI)st.getSubject();
					// the property predicate is always an IRI
					IRI predicate = st.getPredicate();

					// the property value could be an IRI, a BNode, or a Literal. In RDF4J,
					// Value is is the supertype of all possible kinds of RDF values.
					Value object = st.getObject();

					// let's print out the statement in a nice way. We ignore the namespaces
					// and only print the local name of each IRI
					System.out.println (subject.stringValue() + ", " + predicate.getLocalName() + ", " + object.stringValue() + "\n");					
			   	}
			}
			System.out.println ("\n");
			
			System.out.println ("Connection to " + repositoryID + ", " + rdf4jServer + " Successful");
						
		} 
		catch (RepositoryException e)
		{
			System.out.println ("Failed to establish connection to repository " + repositoryID + ", " + rdf4jServer);
			System.out.println(e);
		}
		finally
		{
			repo.shutDown ();
			System.out.println ("Repo shutted down");
		}
	}
}
