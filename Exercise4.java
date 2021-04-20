// PROG2 VT2021, Övning 4
// Grupp 033
// Viggo Asklöf vias2878
// Stanislav Alpatiev stanl5991

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Exercise4 implements Ex4 {
	private final Graph<Nodes.GraphNode> graph;
	private final ListGraph<Nodes.GraphNode> listGraph = new ListGraph<Nodes.GraphNode>();

	public Exercise4(Graph<Nodes.GraphNode> graph) {
		this.graph = graph;
	}

	@Override
	public void loadLocationGraph(String filename) throws FileNotFoundException{
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader in = new BufferedReader(reader);
			String line;
			String name;
			String destination;
			String connection;
			double x;
			double y;
			int weight;
			int counter = -1;

			line = in.readLine();
			var txtNodes= line.split(";");
			for(int i = 0; i < (txtNodes.length/3); i++) {
				counter++;
				name = txtNodes[counter];
				counter++;
				x = Double.parseDouble(txtNodes[counter]);
				counter++;
				y = Double.parseDouble(txtNodes[counter]);
				listGraph.add(new Nodes.LocationNode(name, x, y));
			}

			Set<Nodes.GraphNode> nodes = listGraph.getNodes();


			while((line = in.readLine()) != null) {
				var edge= line.split(";");
				name = edge[1];
				destination = edge[1];
				connection = edge[2];
				weight = Integer.parseInt(edge[3]);
				listGraph.connect();
			}

			/*en inledande lång rad med semikolonseparerade uppgifter om noder, och därefter
			flera rader med uppgifter om förbindelserna, en förbindelse per rad.

			Uppgifter om noder ska omfatta nodens namn, nodens x-koordinat och nodens ykoordinat,
			separerade med semikolon.

			Raderna med uppgifter om förbindelserna ska för varje förbindelse innehålla namnet
			på från-noden, namnet på till-noden, namnet på förbindelsen och förbindelsens vikt,
			separerade med semikolon.*/

			/*Stockholm;469.0;242.0;Oslo;398.0;219.0;Warszawa;503.0;377.0
			Stockholm;Oslo;Train;3
			Stockholm;Warszawa;Airplane;2
			Oslo;Stockholm;Train;3
			Warszawa;Stockholm;Airplane;2*/

			/*recordings.add(new Recording(title, artist, year, genre));*/
			in.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}