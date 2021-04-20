// PROG2 VT2021, Övning 4
// Grupp 033
// Viggo Asklöf vias2878
// Stanislav Alpatiev stanl5991

import java.io.FileNotFoundException;
import java.util.*;

public interface Ex4 {

	void loadLocationGraph(String filename) throws FileNotFoundException;

	default SortedMap<Integer, SortedSet<Nodes.RecordNode>> optionalGetAlsoLiked(Nodes.RecordNode item) {
		return null;
	}

	default long optionalGetPopularity(Nodes.RecordNode item) {
		return -1;
	}

	default TreeMap<Integer, Set<Nodes.RecordNode>> optionalGetTop5() {
		return null;
	}

	default void optionalLoadRecoGraph(String filename) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
