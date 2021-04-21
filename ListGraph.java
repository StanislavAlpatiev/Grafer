// PROG2 VT2021, Övning 4
// Grupp 033
// Viggo Asklöf vias2878
// Stanislav Alpatiev stal5991

import java.util.*;

public class ListGraph<N> implements Graph<N> {
    private Map<N, Set<Edge<N>>> nodes = new HashMap<>();

    /* add – tar emot en nod och stoppar in den i grafen. Om noden redan finns i
    grafen blir det ingen förändring. */
    @Override
    public void add (N city) {
        nodes.putIfAbsent(city, new HashSet<>());
    }

    /* connect – tar två noder, en sträng (namnet på förbindelsen) och ett heltal
    (förbindelsens vikt) och kopplar ihop dessa noder med kanter med detta
    namn och denna vikt. Om någon av noderna saknas i grafen ska undantaget
    NoSuchElementException från paketet java.util genereras. Om vikten är
    negativ ska undantaget IllegalArgumentException genereras. Om en kant
    redan finns mellan dessa två noder ska undantaget IllegalStateException
    genereras (det ska finnas högst en förbindelse mellan två noder).
    Observera att grafen ska vara oriktad, d.v.s. en förbindelse representeras av
    två kanter: kanter riktade mot den andra noden måste stoppas in hos de
    båda noderna. I en oriktad graf förekommer ju alltid kanter i par: från nod 1 till
    nod 2 och tvärtom. */
    @Override
    public void connect (N c1, N c2, String name , int weight) {
        if(!nodes.containsKey(c1) || !nodes.containsKey(c2)) {
            throw new NoSuchElementException();
        } else {
            if(weight < 0) {
                throw new IllegalArgumentException();
            } else if(getEdgeBetween(c1, c2) != null) {
                throw new IllegalStateException();
            } else {
                Set<Edge<N>> se1 = nodes.get(c1);
                Edge<N> e1 = new Edge<N>(c2, name, weight);
                se1.add(e1);
                Set<Edge<N>> se2 = nodes.get(c2);
                Edge<N> e2 = new Edge<N>(c1, name, weight);
                se2.add(e2);
            }
        }
    }

    /* remove – tar emot en nod och tar bort den från grafen. Om noden saknas i
    grafen ska undantaget NoSuchElementException från paketet java.util
    genereras. När en nod tas bort ska även dess kanter tas bort, och eftersom
    grafen är oriktad ska kanterna även tas bort från de andra noderna. */
    @Override
    public void remove(N c1) {
        if(!nodes.containsKey(c1)) {
            throw new NoSuchElementException();
        } else {
            Set<Edge<N>> se1 = new HashSet<>(getEdgesFrom(c1));
            for(Edge<N> e : se1) {
                N dest = e.getDestination();
                disconnect(c1 , dest);
            }
            nodes.remove(c1);
        }
    }

    /* disconnect – tar två noder och tar bort kanten som kopplar ihop dessa
    noder. Om någon av noderna saknas i grafen ska undantaget
    NoSuchElementException från paketet java.util genereras.
    Om det inte finns en kant mellan dessa två noder ska undantaget
    IllegalStateException genereras. (Här kan säkert metoden
    getEdge<T>Between vara till nytta.)
    Observera att eftersom grafen är oriktad, d.v.s. en förbindelse representeras
    av två kanter så måste kanten tas bort från båda noderna. */
    @Override
    public void disconnect(N c1, N c2) {
        if(!nodes.containsKey(c1) || !nodes.containsKey(c2)) {
            throw new NoSuchElementException();
        } else {
            if(getEdgeBetween(c1, c2) != null) {
                nodes.get(c1).removeIf(e -> e.getDestination().equals(c2));
                nodes.get(c2).removeIf(e -> e.getDestination().equals(c1));
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /* setConnectionWeight – tar två noder och ett heltal (förbindelsens nya vikt)
    och sätter denna vikt som den nya vikten hos förbindelsen mellan dessa två
    noder. Om någon av noderna saknas i grafen eller ingen kant finns mellan
    dessa två noder ska undantaget NoSuchElementException från paketet
    java.util genereras. Om vikten är negativ ska undantaget
    IllegalArgumentException genereras. */
    @Override
    public void setConnectionWeight(N c1, N c2, int weight) throws NoSuchElementException {
        if(!nodes.containsKey(c1) || !nodes.containsKey(c2) || getEdgeBetween(c1, c2) == null) {
            throw new NoSuchElementException();
        } else if(weight < 0) {
            throw new IllegalArgumentException();
        } else {
            getEdgeBetween(c1, c2).setWeight(weight);
            getEdgeBetween(c2, c1).setWeight(weight);
        }
    }


    /* getNodes – returnerar en kopia av mängden av alla noder. */
    @Override
    public Set<N> getNodes() {
        return Collections.unmodifiableSet(new HashSet<>(nodes.keySet()));
    }

    /* getEdgesFrom – tar en nod och returnerar en kopia av samlingen av alla
    kanter som leder från denna nod. Om noden saknas i grafen ska undantaget
    NoSuchElementException genereras. */
    @Override
    public Set<Edge<N>> getEdgesFrom(N c1) {
        if(!nodes.containsKey(c1)) {
            throw new NoSuchElementException();
        } else {
            return Collections.unmodifiableSet(nodes.get(c1));
        }
    }

    /* getEdge<T>Between – tar två noder och returnerar kanten mellan dessa noder.
    Om någon av noderna saknas i grafen ska undantaget
    NoSuchElementException genereras. Om det inte finns någon kant mellan
    noderna returneras null. */
    @Override
    public Edge<N> getEdgeBetween(N c1, N c2) {
        if(!nodes.containsKey(c1) || !nodes.containsKey(c2)) {
            throw new NoSuchElementException();
        } else {
            Set<Edge<N>> se1 = nodes.get(c1);
            for(Edge<N> e : se1) {
                if(e.getDestination().equals(c2)) {
                    return e;
                }
            }
            return null;
        }
    }

    private void depthFirstSearch (N where , Set<N>visited) {
        visited.add(where);
        for(Edge<N> e : nodes.get(where)) {
            if(!visited.contains(e.getDestination())) {
                depthFirstSearch(e.getDestination(), visited);
            }
        }
    }

    /* pathExists – tar två noder och returnerar true om det finns en väg genom
    grafen från den ena noden till den andra (eventuellt över många andra
    noder), annars returneras false. Om någon av noderna inte finns i grafen
    returneras också false. Använder sig av en hjälpmetod för djupet-förstsökning
    genom en graf. */
    @Override
    public boolean pathExists (N from, N to) {
        Set<N> visited = new HashSet<>();
        try {
            depthFirstSearch(from, visited);
        } catch(Exception e) {
            return false;
        }
        return visited.contains(to);
    }

    /* gatherPath takes two nodes and returns a list with the edges
     *
     *
     * */
    private List<Edge<N>> gatherPath(N from, N to, Map<N , N> via) {
        List<Edge<N>> path = new ArrayList<>();
        N where = to;
        while(!where.equals(from)) {
            N node = via.get(where);
            Edge<N> e = getEdgeBetween(node , where);
            path.add(e);
            where = node;
        }
        Collections.reverse(path);
        return path;
    }

    /* getPath – tar två noder och returnerar en lista (java.util.List) med kanter
    som representerar en väg mellan dessa noder genom grafen, eller null om
    det inte finns någon väg mellan dessa två noder. I den enklaste varianten
    räcker det alltså att metoden returnerar någon väg mellan de två noderna,
    men frivilligt kan man göra en lösning där returnerar den kortaste vägen (i
    antalet kanter som måste passeras) eller den snabbaste vägen (med hänsyn
    tagen till kanternas vikter). */
    @Override
    public List<Edge<N>> getPath(N from, N to) {
        if(!pathExists(from, to)) {
            return null;
        } else {
            Set<N> visited = new HashSet<>();
            LinkedList<N> queue = new LinkedList<>();
            Map<N, N> via = new HashMap<>();
            visited.add(from);
            queue.add(from);
            while(!queue.isEmpty()) {
                N node = queue.pollFirst();
                for(Edge<N> e :nodes.get(node)) {
                    N toNode = e.getDestination();
                    if(!visited.contains(toNode)) {
                        queue.add(toNode);
                        visited.add(toNode);
                        via.put(toNode, node);
                    }
                }
            }
            return gatherPath(from, to, via);
        }
    }

    /* toString – returnerar en lång sträng med strängar tagna från nodernas
    toString-metoder och kanternas toString-metoder, gärna med
    radbrytningar så att man får information om en nod per rad för förbättrad
    läsbarhet. */
    @Override
    public String toString () {
        String string = "";
        for(N city : nodes.keySet()) {
            string += city + ": ";
            for(Edge<N> e : nodes.get(city)) {
                string += e;
            }
            string += "\n";
        }
        return string;
    }
}

