
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
import java.util.LinkedList;
import java.util.List;

public class Connector_old {
	private String serverID;
	private String repoID;
	private Repository repo;
	private ValueFactory vf;

	Connector_old (String serverID, String repoID)
	{
		this.serverID = serverID;
		this.repoID = repoID;
		repo = new HTTPRepository (serverID, repoID);
		repo.initialize();
		vf = repo.getValueFactory();
	}
	
	public void addScene (int sceneIndex)
	{
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI sceneIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex);
			IRI index = vf.createIRI ("http://kif.cs.lth.se/index");
			Literal indexL = vf.createLiteral (sceneIndex);
			repoConn.add (sceneIRI, index, indexL, context);
		}
	}
	
	public void addCluster (int sceneIndex, int clusterIndex)
	{
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI sceneIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex);
			IRI clusterIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex);
			IRI cluster = vf.createIRI ("http://kif.cs.lth.se/cluster");
			Literal sceneIndexValue = vf.createLiteral (sceneIndex);
			repoConn.add (sceneIRI, cluster, clusterIRI, context);
		}
	}
	
	public void addCurrentPosition (int sceneIndex, String position)
	{
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI sceneIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex);
			IRI currentPosition = vf.createIRI ("http://kif.cs.lth.se/currentPosition");
			Literal currentPositionL = vf.createLiteral (position);
			repoConn.add (sceneIRI, currentPosition, currentPositionL, context);
		}
	}
	
	public void addNextPosition (int sceneIndex, int clusterIndex, String position) 
	{
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI clusterIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex);
			IRI nextPosition = vf.createIRI ("http://kif.cs.lth.se/nextPosition");
			Literal nextPositionL = vf.createLiteral (position);
			repoConn.add (clusterIRI, nextPosition, nextPositionL, context);
		}
	}
	
	public void addModel (int sceneIndex, int clusterIndex, String name, double score, String pose)
	{
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI sceneIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex);
			IRI clusterIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex);
			IRI identifiedModel = vf.createIRI ("http://kif.cs.lth.se/identifiedModel");
			IRI modelIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex + "/" + name);
			IRI modelName = vf.createIRI ("http://kif.cs.lth.se/name");
			IRI modelScore = vf.createIRI ("http://kif.cs.lth.se/score");
			IRI modelPose = vf.createIRI ("http://kif.cs.lth.se/pose");
			Literal nameL = vf.createLiteral (name);
			Literal scoreL = vf.createLiteral (score);
			Literal poseL = vf.createLiteral (pose);
			repoConn.add (modelIRI, modelName, nameL, context);
			repoConn.add (modelIRI, modelScore, scoreL, context);
			repoConn.add (modelIRI, modelPose, poseL, context);
			repoConn.add (clusterIRI, identifiedModel, modelIRI, context);
		}
	}
	
	public double getScore (int sceneIndex, int clusterIndex, String modelName)
	{
		double score = 0.0;
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI modelIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex + "/" + modelName);
			IRI modelScore = vf.createIRI ("http://kif.cs.lth.se/score");			
			try (RepositoryResult<Statement> statements = repoConn.getStatements(modelIRI, modelScore, null, context)) 
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
	
	public String getPose (int sceneIndex, int clusterIndex, String modelName)
	{
		String pose = "";
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI modelIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex + "/" + modelName);
			IRI modelPose = vf.createIRI ("http://kif.cs.lth.se/pose");
			try (RepositoryResult<Statement> statements = repoConn.getStatements(modelIRI, modelPose, null, context)) 
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
	
	public String getCurrentPosition (int sceneIndex)
	{
		String position = "";
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI sceneIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex);
			IRI currentPosition = vf.createIRI ("http://kif.cs.lth.se/currentPosition");
			try (RepositoryResult<Statement> statements = repoConn.getStatements(sceneIRI, currentPosition, null, context)) 
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
	
	public String getNextPosition (int sceneIndex, int clusterIndex)
	{
		String position = "";
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI clusterIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex);
			IRI nextPosition = vf.createIRI ("http://kif.cs.lth.se/nextPosition");
			try (RepositoryResult<Statement> statements = repoConn.getStatements(clusterIRI, nextPosition, null, context)) 
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
	
	public List getAllIdentifiedModels(int sceneIndex, int clusterIndex)
	{
		List<ClusterResults> crList = new LinkedList<ClusterResults> ();
		try (RepositoryConnection repoConn = repo.getConnection())
		{
			IRI context = vf.createIRI("http://kif.cs.lth.se/context" + sceneIndex);
			IRI clusterIRI = vf.createIRI ("http://kif.cs.lth.se/scene" + sceneIndex + "/cluster" + clusterIndex);
			IRI identifiedModel = vf.createIRI ("http://kif.cs.lth.se/identifiedModel");
			IRI modelName = vf.createIRI ("http://kif.cs.lth.se/name");
			IRI modelScore = vf.createIRI ("http://kif.cs.lth.se/score");
			IRI modelPose = vf.createIRI ("http://kif.cs.lth.se/pose");
			try (RepositoryResult<Statement> statements1 = repoConn.getStatements(clusterIRI, identifiedModel, null, context)) 
			{
			   	while (statements1.hasNext()) 
			   	{
			  		Statement st1 = statements1.next();
			  		ClusterResults cr = new ClusterResults ();
			  		cr.sceneIndex = sceneIndex;
			  		cr.clusterIndex = clusterIndex;
			  		try (RepositoryResult<Statement> statements2 = repoConn.getStatements((IRI)st1.getObject(), null, null, context)) 
					{
						while (statements2.hasNext()) 
						{
							Statement st2 = statements2.next();
							if (st2.getPredicate().equals(modelName))
							{
								cr.modelName = st2.getObject().stringValue();
							}
							else if (st2.getPredicate().equals(modelScore))
							{
								cr.score = ((Literal)st2.getObject()).doubleValue();
							}
							else
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
	
	public void close ()
	{
		repo.shutDown();
	}
}














































