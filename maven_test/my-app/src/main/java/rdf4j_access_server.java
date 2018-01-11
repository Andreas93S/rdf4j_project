


import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.RDF4JException;


public class rdf4j_access_server {

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
			
			System.out.println ("Connection to " + repositoryID + ", " + rdf4jServer + " Successful");
			
		} 
		catch (RepositoryException e)
		{
			System.out.println ("Failed to establish connection to repository " + repositoryID + ", " + rdf4jServer);
		}
		finally
		{
			repo.shutDown ();
			System.out.println ("Repo shutted down");
		}
	}
}
