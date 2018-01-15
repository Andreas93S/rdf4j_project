import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// Wood object
		Connector conn = new Connector("http://vm25.cs.lth.se/rdf4j-server", "object_repo");
		conn.addScene (0);
		System.out.println("Scene 0 added");
		conn.addCluster (0, 0);
		System.out.println("Cluster 0 added");
		conn.addModel (0, 0, "Wood object", 0.89, "0.1,0.2,0.3,...");
		System.out.println("Results for wood object added");
		conn.addCurrentPosition (0, "0.3,0.3,0.5,...");
		System.out.println("Current position for scene 0 added");
		conn.addNextPosition (0, 0, "0.15,0.4,0.8,...");
		System.out.println("Next position for scene 0, cluster 0 added");
		
		System.out.println ("\n\nScore for wood object in scene 0, cluster 0: " + conn.getScore (0, 0, "Wood object"));
		System.out.println ("\n\nPose for wood object in scene 0, cluster 0: " + conn.getPose (0, 0, "Wood object"));
		System.out.println ("\n\nCurrent position of robot arm: " + conn.getCurrentPosition (0));
		System.out.println ("\n\nNext position for robot arm: " + conn.getNextPosition (0, 0));
		
		// Xbox controller		
		conn.addModel (0, 0, "Xbox controller", 0.53, "0.5,0.7,0.1,...");
		System.out.println("Results for xbox controller added");
		
		List<Connector.ClusterResults> crList = conn.getAllIdentifiedModels (0, 0);
		
		for (Connector.ClusterResults cr : crList)
		{
			System.out.println ("\n" + cr.modelName + ":");
			System.out.println ("\tScore: " + cr.score);
			System.out.println ("\tPose: " + cr.pose);
		}
	}
}
