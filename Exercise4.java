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
			var nodes= line.split(";");
			for(int i = 0; i < (nodes.length/3); i++) {
				counter++;
				name = nodes[counter];
				counter++;
				x = Double.parseDouble(nodes[counter]);
				counter++;
				y = Double.parseDouble(nodes[counter]);
				Nodes.LocationNode node = new Nodes.LocationNode(name, x, y);
				graph.add(node);
			}

			while((line = in.readLine()) != null) {
				var edge= line.split(";");

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

			/*String title = "";
			String artist = "";
			int year = 0;
			int numberOfRecords = Integer.parseInt(in.readLine());
			int numberOfGenre = 0;

			for (int i = 0; i < numberOfRecords; i++) {
				line = in.readLine();
				var artistTitleYear= line.split(";");
				artist = artistTitleYear[0];
				title = artistTitleYear[1];
				year = Integer.parseInt(artistTitleYear[2]);
				numberOfGenre = Integer.parseInt(in.readLine());
				Set<String> genre = new HashSet<>(numberOfGenre);

				for (int n = 0; n < numberOfGenre; n++) {
					genre.add(in.readLine());
				}

				recordings.add(new Recording(title, artist, year, genre));
			}*/
			in.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}