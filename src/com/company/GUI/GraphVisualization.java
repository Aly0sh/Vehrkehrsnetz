package com.company.GUI;

import com.company.DataReaders.LineReader;
import com.company.DataReaders.StationsReader;
import com.company.Models.Roadmap;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

// Die Hauptklasse zum Rendern von Graph
public class GraphVisualization {
    private MainFrame mainFrame;
    private Graph<String, String> graph; // Die Variable zur Speicherung von Knoten und Kanten des Graphen
    private Map<String, Paint> colors = new HashMap<>(); // Farben Wörterbuch für Linien
    private Map <String, LinkedList<String>> map; // Wörterbuch für alle Stationen und Linien
    private FRLayout<String, String> layout; // Ein kraft basiertes Layout-Algorithmus, der zur Darstellung von Graphen verwendet wird
    private VisualizationViewer<String, String> vv; // Das Hauptfenster zur Visualisierung von Graphen
    private DefaultModalGraphMouse<String, String> gm; // Ein Maus-Plugin für die Interaktion mit Graphen
    private List<Roadmap> pathsForV; // Liste aller durchlaufenen Pfade
    private List<String> visitedEdges; // besuchte Kanten
    private List<String> visitedMainEdges; // Besuchte Kanten des kürzesten Pfades
    private LinkedList<List<String>> edges; // Edge-Liste für die Visualisierung
    private LinkedList<String> mainEdges; // Edge-Liste des kürzesten Pfades für die Visualisierung

    public GraphVisualization(MainFrame mainFrame) {
        graph = new SparseGraph<>();
        map = getStations();
        fillGraph();
        prepare();
        paintVertex();
        visitedEdges = new ArrayList<>();
        visitedMainEdges = new ArrayList<>();
        this.mainFrame = mainFrame;
    }

    // Zuordnung von Knoten und Kanten zum Graphen
    public void fillGraph() {
        for (String key : map.keySet()) {
            for (int i = 0; i < map.get(key).size() - 1; i++) {
                graph.addEdge(key + i, map.get(key).get(i), map.get(key).get(i + 1), EdgeType.UNDIRECTED);
            }
            colors.put(key, new RandomColorGenerator().nextColor());
        }
    }

    // Initialisierung aller Visualisierungskomponenten
    public void prepare() {
        layout = new FRLayout<>(graph);
        layout.setMaxIterations(10000); // Anzahl der Iterationen des Algorithmus zur Darstellung des Graphen
        layout.setAttractionMultiplier(0.5);
        layout.setRepulsionMultiplier(0.5);

        vv = new VisualizationViewer<>(layout);

        gm = new DefaultModalGraphMouse<>();
        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
        vv.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(graph)); // Alle Kanten werden gerader, ohne Biegungen
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller()); // Die Namen der Stationen neben den Knoten hinzufügen
        vv.setGraphMouse(gm);
        vv.setPreferredSize(new Dimension(600, 600));

        // Animation zum Zeichnen der durchlaufenen Kanten
        vv.getRenderContext().setEdgeDrawPaintTransformer(edge -> {
            if (visitedMainEdges.contains(edge)) {
                return Color.GREEN;
            } else if (visitedEdges.contains(edge)) {
                return Color.RED;
            }
            return Color.BLACK;
        });

        // Übertragung des Namen der Station, auf die der Benutzer geklickt hat
        vv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D p = e.getPoint();
                String vertex = vv.getPickSupport().getVertex(vv.getGraphLayout(), p.getX(), p.getY());
                if (mainFrame.getStartStation().isEmpty()) {
                    mainFrame.setStartStation(vertex);
                } else if (mainFrame.getEndStation().isEmpty()) {
                    mainFrame.setEndStation(vertex);
                }
            }
        });
    }

    // Die Einfärbung der Knoten in Abhängigkeit von den Linien
    public void paintVertex() {
        vv.getRenderContext().setVertexFillPaintTransformer(s -> {
            if (s.contains("-")) {
                return Color.BLACK;
            }
            for (String key : map.keySet()) {
                if (map.get(key).contains(s)) {
                    return colors.get(key);
                }
            }
            return new RandomColorGenerator().nextColor();
        });
    }

    public VisualizationViewer<String, String> getVv() {
        return vv;
    }

    // Der Methode zum Abrufen aller Stationen
    public Map<String, LinkedList<String>> getStations() {
        Map<String, LinkedList<String>> stations = new HashMap<>();
        for (String name : LineReader.checkDirectory("src/com/company/Lines")) {
            stations.put(name, StationsReader.getStation("src/com/company/Lines/" + name + ".txt"));
        }
        return stations;
    }

    public void setPathsForV(List<Roadmap> pathsForV) {
        this.pathsForV = pathsForV;
    }

    public void setMainEdges(LinkedList<String> mainEdges) {
        this.mainEdges = mainEdges;
    }

    // Methode zur Änderung aller erhaltenen durchlaufenen Pfade in eine bequemere Liste für die Animation
    private void TPathsForV() {
        int i = 0;
        boolean flag = true;
        edges = new LinkedList<>();
        List<String> cEdges;
        while (flag) {
            flag = false;
            cEdges = new ArrayList<>();
            for (Roadmap r : pathsForV) {
                if (r.getStations().size() - 1 > i) {
                    cEdges.add(graph.findEdge(r.getStations().get(i), r.getStations().get(i + 1)));
                    flag = true;
                }
            }
            if (!cEdges.isEmpty()) {
                edges.addLast(cEdges);
            }
            i++;
        }
    }

    // Eine Animation für die durchlaufenen Pfade erstellen
    public void drawEdges() {
        TPathsForV();
        visitedEdges = new ArrayList<>();
        visitedMainEdges = new ArrayList<>();
        for (List<String> i : edges) {
            for (String j : i) {
                visitedEdges.add(j);
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vv.repaint();
        }
        for (int i = 0; i < mainEdges.size() - 1; i++) {
            visitedMainEdges.add(graph.findEdge(mainEdges.get(i), mainEdges.get(i + 1)));
        }
        vv.repaint();
    }
}
