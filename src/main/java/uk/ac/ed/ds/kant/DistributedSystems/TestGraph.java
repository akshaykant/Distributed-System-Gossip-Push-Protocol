package uk.ac.ed.ds.kant.DistributedSystems;
/**
 * Creates a graph with three nodes.
 * Plots the graph and changes the color every second.
 */

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


public class TestGraph {

    public static void main(String [] argv) {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph("test");
        graph.display();
        graph.addAttribute("ui.antialias");
        graph.addAttribute("stylesheet", "graph {padding : 50px;}"
                + "node {size: 100px; fill-mode: plain;}"
                + "node.red {fill-color: red;}"
                + "node.blue {fill-color: blue;}");

        Node a = graph.addNode("A");
        Node b = graph.addNode("B");
        Node c = graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("CB", "C", "B");
        graph.addEdge("AC", "A", "C");

        a.addAttribute("ui.class", "red");
        b.addAttribute("ui.class", "red");
        c.addAttribute("ui.class", "red");
        boolean is_red = true;
        while(true){
            try {
                Thread.sleep(1000);
                if (is_red == true) {
                    a.setAttribute("ui.class", "blue");
                    b.setAttribute("ui.class", "blue");
                    c.setAttribute("ui.class", "blue");
                    is_red = false;
                }
                else {
                    a.setAttribute("ui.class", "red");
                    b.setAttribute("ui.class", "red");
                    c.setAttribute("ui.class", "red");
                    is_red = true;
                }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
