// PROG2 VT2021, Övning 4
// Grupp 033
// Viggo Asklöf vias2878
// Stanislav Alpatiev stal5991

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Exercise4 implements Ex4 {
	private final Graph<Nodes.GraphNode> graph;

	public Exercise4(Graph<Nodes.GraphNode> graph) {
		this.graph = graph;
	}

	@Override
	public void loadLocationGraph(String filename) {
		Map<String, Nodes.GraphNode> locationNodes = new HashMap<>();
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader in = new BufferedReader(reader);
			String line;

			line = in.readLine();
			var txtNodes= line.split(";");
			for(int i = 0; i < txtNodes.length; i += 3) {
				String name = txtNodes[i];
				double x = Double.parseDouble(txtNodes[i + 1]);
				double y = Double.parseDouble(txtNodes[i + 2]);
				Nodes.GraphNode node = new Nodes.LocationNode(name, x, y);
				graph.add(node);
				locationNodes.put(name, node);
			}

			while((line = in.readLine()) != null) {
				var edge= line.split(";");
				String name = edge[0];
				String destination = edge[1];
				String connection = edge[2];
				int weight = Integer.parseInt(edge[3]);
				Nodes.GraphNode node1 = locationNodes.get(name);
				Nodes.GraphNode node2 = locationNodes.get(destination);
				if(graph.getEdgeBetween(node1, node2) == null) {
					graph.connect(node1, node2, connection, weight);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}