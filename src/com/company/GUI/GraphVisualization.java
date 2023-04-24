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
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class GraphVisualization {
    private MainFrame mainFrame;
    private Graph<String, String> graph;
    private Map<String, Paint> colors = new HashMap<>();
    private Map <String, LinkedList<String>> map;
    private FRLayout<String, String> layout;
    private VisualizationViewer<String, String> vv;
    private DefaultModalGraphMouse<String, String> gm;
    private List<Roadmap> pathsForV;
    private List<String> visitedEdges;
    private List<String> visitedMainEdges;
    private LinkedList<List<String>> edges;
    private LinkedList<String> mainEdges;

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

    public void fillGraph() {
        for (String key : map.keySet()) {
            for (int i = 0; i < map.get(key).size() - 1; i++) {
                graph.addEdge(key + i, map.get(key).get(i), map.get(key).get(i + 1), EdgeType.UNDIRECTED);
            }
            colors.put(key, new RandomColorGenerator().nextColor());
        }
    }

    public void prepare() {
        layout = new FRLayout<>(graph);
        layout.setMaxIterations(10000);
        layout.setAttractionMultiplier(0.5);
        layout.setRepulsionMultiplier(0.5);

        vv = new VisualizationViewer<>(layout);

        gm = new DefaultModalGraphMouse<>();
        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
        vv.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(graph));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.setGraphMouse(gm);
        vv.setPreferredSize(new Dimension(600, 600));
        vv.getRenderContext().setEdgeDrawPaintTransformer(edge -> {
            if (visitedMainEdges.contains(edge)) {
                return Color.GREEN;
            } else if (visitedEdges.contains(edge)) {
                return Color.RED;
            }
            return Color.BLACK;
        });
        vv.getPickedVertexState().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getItem());
            }
        });

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
