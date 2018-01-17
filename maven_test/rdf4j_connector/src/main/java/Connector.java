
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.Model;
import java.util.LinkedList;
import java.util.List;

public class Connector {
	private String serverID;
	private String repoID;
	private Repository repo;
	private ValueFactory vf;
	private String ns;

	Connector (String serverID, String repoID)
	{
		this.serverID = serverID;
		this.repoID = repoID;
		repo = new HTTPRepository (serverID, repoID);
		repo.initialize();
		vf = repo.getValueFactory();
		ns = "http://kif.cs.lth.se/";
	}
	
	public static void addScene (int sceneIndex)
	{
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
			.setNamespace ("kif", ns)
			.namedGraph ("kif:graphScene" + sceneIndex)
				.subject ("kif:scene" + sceneIndex)
					.add (RDF.TYPE, "kif:Scene")
					.add ("kif:Index", sceneIndex)
			.build();
		  
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			repoConn.add (model);
		}
	}
	
	public static void addCluster (int sceneIndex, int clusterIndex)
	{
	
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
			.setNamespace ("kif", ns)
			.namedGraph ("kif:graphScene" + sceneIndex)
				.subject ("kif:scene" + sceneIndex + "/cluster" + clusterIndex)
					.add (RDF.TYPE, "kif:Cluster")
					.add ("kif:Index", clusterIndex)
				.subject ("kif:scene" + sceneIndex)
					.add ("kif:hasCluster", "kif:cluster" + clusterIndex)
			.build();
		  
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			repoConn.add (model);
		}
	}
	
	public static void addCurrentPosition (int sceneIndex, String position)
	{
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
			.setNamespace ("kif", ns)
			.namedGraph ("kif:graphScene" + sceneIndex)
				.subject ("kif:scene" + sceneIndex)
					.add ("kif:currentPosition", position)
			.build();
		  
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			repoConn.add (model);
		}
	}
	
	public static void addNextPosition (int sceneIndex, int clusterIndex, String position) 
	{
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
			.setNamespace ("kif", ns)
			.namedGraph ("kif:graphScene" + sceneIndex)
				.subject ("kif:scene" + sceneIndex + "/cluster" + clusterIndex)
					.add ("kif:nextPosition", position)
			.build();
		  
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			repoConn.add (model);
		}
	}
	
	public static void addModel (int sceneIndex, int clusterIndex, String name, double score, String pose)
	{
		ModelBuilder builder = new ModelBuilder();
		Model model = builder
			.setNamespace ("kif", ns)
			.namedGraph ("kif:graphScene" + sceneIndex)
				.subject ("kif:scene" + sceneIndex + "/cluster" + clusterIndex + "/" + name)
					.add ("kif:name", name)
					.add ("kif:score", score)
					.add ("kif:pose", pose)
					.add (RDF.TYPE, "kif:ModelData")
				.subject ("kif:scene" + sceneIndex + "/cluster" + clusterIndex)
					.add ("kif:identifiedModel", "kif:scene" + sceneIndex + "/cluster" + clusterIndex + "/" + name)
			.build();
		  
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			repoConn.add (model);
		}
	}
	
	public static double getScore (int sceneIndex, int clusterIndex, String modelName)
	{
		double score = 0.0;
		IRI contextIRI = vf.createIRI(ns + "graphScene" + sceneIndex);
		IRI modelIRI = vf.createIRI (ns + "scene" + sceneIndex + "/cluster" + clusterIndex + "/" + modelName);
		IRI scoreIRI = vf.createIRI (ns + "score");	
		try (RepositoryConnection repoConn = repo.getConnection())
		{		
			try (RepositoryResult<Statement> statements = repoConn.getStatements(modelIRI, scoreIRI, null, contextIRI)) 
			{
			   	while (statements.hasNext()) 
			   	{
			  		Statement st = statements.next();
			  		score = ((Literal)(st.getObject())).doubleValue();
		  		}
	  		}
		}
		
		return score;
	}
	
	public static String getPose (int sceneIndex, int clusterIndex, String modelName)
	{
		String pose = "";
		IRI contextIRI = vf.createIRI(ns + "graphScene" + sceneIndex);
		IRI modelIRI = vf.createIRI (ns + "scene" + sceneIndex + "/cluster" + clusterIndex + "/" + modelName);
		IRI poseIRI = vf.createIRI (ns + "pose");
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			try (RepositoryResult<Statement> statements = repoConn.getStatements(modelIRI, poseIRI, null, contextIRI)) 
			{
			   	while (statements.hasNext()) 
			   	{
			  		Statement st = statements.next();
			  		pose = st.getObject().stringValue();
		  		}
	  		}
		}
		
		return pose;
	}
	
	public static String getCurrentPosition (int sceneIndex)
	{
		String position = "";
		IRI contextIRI = vf.createIRI(ns + "graphScene" + sceneIndex);
		IRI sceneIRI = vf.createIRI (ns + "scene" + sceneIndex);
		IRI currentPositionIRI = vf.createIRI (ns + "currentPosition");
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			try (RepositoryResult<Statement> statements = repoConn.getStatements(sceneIRI, currentPositionIRI, null, contextIRI)) 
			{
			   	while (statements.hasNext()) 
			   	{
			  		Statement st = statements.next();
			  		position = st.getObject().stringValue();
		  		}
	  		}
		}
		
		return position;
	}
	
	public static String getNextPosition (int sceneIndex, int clusterIndex)
	{
		String position = "";
		IRI contextIRI = vf.createIRI(ns + "graphScene" + sceneIndex);
		IRI clusterIRI = vf.createIRI (ns + "scene" + sceneIndex + "/cluster" + clusterIndex);
		IRI nextPositionIRI = vf.createIRI (ns + "nextPosition");
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			try (RepositoryResult<Statement> statements = repoConn.getStatements(clusterIRI, nextPositionIRI, null, contextIRI)) 
			{
			   	while (statements.hasNext()) 
			   	{
			  		Statement st = statements.next();
			  		position = st.getObject().stringValue();
		  		}
	  		}
		}
		
		return position;
	}
	
	public static List getAllIdentifiedModels(int sceneIndex, int clusterIndex)
	{
		List<ClusterResults> crList = new LinkedList<ClusterResults> ();
		IRI contextIRI = vf.createIRI(ns, "graphScene" + sceneIndex);
		IRI clusterIRI = vf.createIRI (ns + "scene" + sceneIndex + "/cluster" + clusterIndex);
		IRI identifiedModelIRI = vf.createIRI (ns + "identifiedModel");
		IRI nameIRI = vf.createIRI (ns + "name");
		IRI scoreIRI = vf.createIRI (ns + "score");
		IRI poseIRI = vf.createIRI (ns + "pose");
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			try (RepositoryResult<Statement> statements1 = repoConn.getStatements(clusterIRI, identifiedModelIRI, null, contextIRI)) 
			{
			   	while (statements1.hasNext()) 
			   	{
			  		Statement st1 = statements1.next();
			  		ClusterResults cr = new ClusterResults ();
			  		cr.sceneIndex = sceneIndex;
			  		cr.clusterIndex = clusterIndex;
			  		try (RepositoryResult<Statement> statements2 = repoConn.getStatements((IRI)st1.getObject(), null, null, contextIRI)) 
					{
						while (statements2.hasNext()) 
						{
							Statement st2 = statements2.next();
							if (st2.getPredicate().equals(nameIRI))
							{
								cr.modelName = st2.getObject().stringValue();
							}
							else if (st2.getPredicate().equals(scoreIRI))
							{
								cr.score = ((Literal)st2.getObject()).doubleValue();
							}
							else if (st2.getPredicate().equals(poseIRI))
							{
								cr.pose = st2.getObject().stringValue();
							}					
						}
					}
			  		crList.add (cr);
		  		}
	  		}
		}
		
		return crList;
	}
	
	public static void clearRepo ()
	{
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			try (RepositoryResult<Statement> statements = repoConn.getStatements(null, null, null)) 
			{
				// Remove all statements in repository
				System.out.println ("Removing " + repoConn.size() + " statements in repository " + repoID);

			   	while (statements.hasNext()) 
			   	{
			  		Statement st = statements.next();
					repoConn.remove (st);		
			   	}
			}
		}
	}
	
	public static void close ()
	{
		repo.shutDown();
	}
}














































