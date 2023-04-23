package com.company.GUI;

import com.company.DataReaders.LineReader;
import com.company.DataReaders.StationsReader;
import com.company.Models.Roadmap;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphVisualization {
    private Graph<String, String> graph;
    private Map<String, Paint> colors = new HashMap<>();
    private Map <String, LinkedList<String>> map;
    private FRLayout<String, String> layout;
    private VisualizationViewer<String, String> vv;
    private DefaultModalGraphMouse<String, String> gm;
    private List<Roadmap> pathsForV;

    public GraphVisualization() {
        graph = new SparseGraph<>();
        map = getStations();
        fillGraph();
        prepare();
        paintVertex();
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
        vv.setPreferredSize(new Dimension(600, 320));
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

    public void startPaintingEdge() {
        for (Roadmap r : pathsForV) {
            vv.getRenderContext().setEdgeFillPaintTransformer(
                    edge -> {
                        for (int i = 0; i < r.getStations().size() - 1; i++) {
                            if (edge.equals(graph.findEdge(r.getStations().get(i), r.getStations().get(i + 1)))) {
                                return Color.RED;
                            }
                        }
                        return Color.BLACK;
                    }
            );
            vv.repaint();
        }
    }
}
