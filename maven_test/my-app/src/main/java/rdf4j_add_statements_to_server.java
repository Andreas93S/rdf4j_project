


import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.RDF4JException;


public class rdf4j_add_statements_to_server {

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
			
			//
			// Add statement to connection
			//
			
			// Create a ValueFactory object for the connected repository
			ValueFactory vf = repo.getValueFactory();
			
			// Add statements about the scene
			IRI scene0 = vf.createIRI ("http://Scene0");
			IRI sceneIndex = vf.createIRI ("http://Scene:Index");
			IRI identifiedModel = vf.createIRI ("http://Scene:IdentifiedModel");
			IRI nextPosition = vf.createIRI ("http://Scene:NextPosition");
			IRI model0 = vf.createIRI ("http://Scene0/Model0");
			IRI position = vf.createIRI ("http://Scene0/Position");
			Literal sceneIndexValue = vf.createliteral (0);
			repoConnection.add (scene0, sceneIndex, sceneIndexValue);
			repoConnection.add (scene0, identifiedModel, model0);
			repoConnection.add (scene0, nextPosition, position);
			
			// Add statements about the identified model
			IRI modelName = vf.createIRI ("http://Model:Name");
			IRI score = vf.createIRI ("http://Model:Score");
			IRI pose_predicate = vf.createIRI ("http://Model:Pose");
			IRI pose_object = vf.createIRI ("http://Scene0/Model0/Pose");
			Literal name = vf.createLiteral ("Model0");
			Literal scoreValue = vf.createLiteral (0.3);			
			repoConnection.add (model0, modelName, name);
			repoConnection.add (model0, score, scoreValue);
			repoConnection.add (model0, pose_predicate, pose_object);
			
			// Add statements about the next position
			
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
