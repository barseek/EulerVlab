package vlab.server_java.generate;
import org.json.*;
import java.util.*;

/**
 * Created by Niqera on 30.05.2016.
 */

public class Graph {
    private char[] alphabet= {'a','b','c','d','e','f'};
    private Node startNode;
    private ArrayList<Edge> edges;
    private ArrayList<Node> nodes;
    ArrayList<ArrayList<Edge>> savedEdges;
    ArrayList<ArrayList<Node>> savedNodes;
    private boolean used[];
    private int numOfCycles;
    private int userNumOfCycles;
    private Tree treeMap;
    public void setUserNumOfCycles(int userNumOfCycles) {
        this.userNumOfCycles = userNumOfCycles;
    }

    public int getNumOfCycles() {
        return numOfCycles;
    }

    private void  unJSON(String variant)
    {

        JSONObject obj = new JSONObject(variant);
        String startNodeStrId = obj.getString("startNode");

        char start = startNodeStrId.charAt(0);
        for( Node n : nodes) {
            if (n.getId()==start) {
                startNode = n;
            }
        }

        JSONArray arr = obj.getJSONArray("variant");
        for (int i = 0; i < arr.length(); i++)
        {
            int sourseID=-1;
            int destinationID = -1;;
            char sourseCharID = arr.getJSONObject(i).getJSONObject("data").getString("source").charAt(0);
            char destinationCharID = arr.getJSONObject(i).getJSONObject("data").getString("target").charAt(0);;

            for( int j = 0; j < alphabet.length; j++ )
            {
                if (sourseCharID==alphabet[j])sourseID=j;
                if (destinationCharID==alphabet[j])destinationID=j;
            }

            Edge temp = new Edge(nodes.get(sourseID), nodes.get(destinationID));
            edges.add(temp);
            nodes.get(sourseID).incedent(temp);
            nodes.get(destinationID).incedent(temp);
        }


        for(Edge edge : edges)
        {
            System.out.print("edge after unJSON "+edge.getId()+" ");
        }
        System.out.println();
    }
    private void deserealize(String variant)
    {
        char start = variant.charAt(0);
        for( Node n : nodes) {
            if (n.getId()==start) {
                startNode = n;
            }
        }
        String serrializedEdges = variant.substring(2);
        String[] edgesMas = serrializedEdges.split(";");
        for (String edgeStr : edgesMas)
        {
            char sourseChar = edgeStr.charAt(0);
            char destinationChar = edgeStr.charAt(1);
            int sourseID=-1;
            int destinationID=-1;
            for( int i = 0; i < alphabet.length; i++ )
            {
                if (sourseChar==alphabet[i])sourseID=i;
                if (destinationChar==alphabet[i])destinationID=i;
            }
            if(sourseID!=-1 && destinationID!=-1)
            {
                Edge temp = new Edge(nodes.get(sourseID), nodes.get(destinationID));
                edges.add(temp);
                nodes.get(sourseID).incedent(temp);
                nodes.get(destinationID).incedent(temp);
            }

        }
        for(Edge edge : edges)
        {
            System.out.print("edge "+edge.getId()+" ");
        }

    }
    //если длина юзд  = 6, то все в порядке
    private  void dfs(int v) {
        //если вершина является пройденной, то не производим из нее вызов процедуры
        if (used[v]) {
            return;
        }
        used[v] = true; //помечаем вершину как пройденную
       System.out.print((v + 1) + " ");
        //запускаем обход из всех вершин, смежных с вершиной v
        for (int i = 0; i < nodes.get(v).adjustedNodes.size(); ++i) {
            int w = nodes.get(v).adjustedNodes.get(i).getNumId();
            dfs(w); //вызов обхода в глубину от вершины w, смежной с вершиной v
        }
    }

    private boolean adjacency(Node one, Node two)
    {
        for(Edge oneEdge :one.incedentEdges)
            for(Edge twoEdge :two.incedentEdges)
                if (oneEdge==twoEdge) return true;
        return false;
    }
    public Graph(String serrializedEdges, int userNumOfCycles)
    {
        //treeMap = new TreeMap<Integer, Node>();
        edges = new ArrayList();
        nodes = new ArrayList();
        treeMap = new Tree();
        savedEdges = new ArrayList();
        savedNodes = new ArrayList();
        for(int i = 0; i<6; i++) {
            Node temp = new Node(alphabet[i],i);
            nodes.add(temp);
        }

        //deserealize(serrializedEdges);
        unJSON(serrializedEdges);
        this.userNumOfCycles = userNumOfCycles;
        System.out.println("User graph created");
        //System.out.println(userNumOfCycles+ " USER NUM IF CYCLES");

        //проверка на смежность вершин и внесение в списки смежности, неплохо бы вынести в другой метод
        for(int i = 0; i <nodes.size()-1; i++) {
            Node node1 =nodes.get(i);
            for (int j = i+1; j<nodes.size(); j++)
            {
                Node node2 =  nodes.get(j);
                if (adjacency(node1, node2)) {
                    node1.adjustedNodes.add(node2);
                    node2.adjustedNodes.add(node1);
                    //System.out.println(node1.getId() + "  " + node2.getId());
                }
            }
        }

    }

    public Node getStartNode() {
        return startNode;
    }

    public Tree getTreeMap() {
        return treeMap;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Graph()
    {
        //контейнеры для ребер и вершин
        used = new boolean[6];
        Arrays.fill(used, false);
        treeMap = new Tree();
        savedEdges = new ArrayList();
        savedNodes = new ArrayList();
        edges = new ArrayList();
        nodes = new ArrayList();
        /*ArrayList<Integer> incedenceCount = new ArrayList();

        incedenceCount.add(2);
        incedenceCount.add(4);
        incedenceCount.add(2);
        incedenceCount.add(2);
        incedenceCount.add(4);
        incedenceCount.add(2);
        Collections.shuffle(incedenceCount);*/
        //создание вершин с рандомными инцедентностями
//

        for(int i = 0; i<6; i++) {
            Node temp = new Node(alphabet[i],i);
            nodes.add(temp);
        }
        Random r = new Random(System.currentTimeMillis());
        int randomed_node = (r.nextInt(6) + 0);
        startNode = nodes.get(randomed_node);
        System.out.println("START NODE IS : "+startNode.getId()+"  num is : "+startNode.getNumId());
            /*
        Edge temp = new Edge(nodes.get(0), nodes.get(1));
        edges.add(temp);
        nodes.get(0).incedent(temp);
        nodes.get(1).incedent(temp);

        temp = new Edge(nodes.get(0), nodes.get(2));
        edges.add(temp);
        nodes.get(0).incedent(temp);
        nodes.get(2).incedent(temp);

        temp = new Edge(nodes.get(0), nodes.get(3));
        edges.add(temp);
        nodes.get(0).incedent(temp);
        nodes.get(3).incedent(temp);

        temp = new Edge(nodes.get(0), nodes.get(5));
        edges.add(temp);
        nodes.get(0).incedent(temp);
        nodes.get(5).incedent(temp);

        temp = new Edge(nodes.get(1), nodes.get(3));
        edges.add(temp);
        nodes.get(1).incedent(temp);
        nodes.get(3).incedent(temp);

        temp = new Edge(nodes.get(2), nodes.get(3));
        edges.add(temp);
        nodes.get(2).incedent(temp);
        nodes.get(3).incedent(temp);

        temp = new Edge(nodes.get(3), nodes.get(4));
        edges.add(temp);
        nodes.get(3).incedent(temp);
        nodes.get(4).incedent(temp);

        temp = new Edge(nodes.get(4), nodes.get(5));
        edges.add(temp);
        nodes.get(4).incedent(temp);
        nodes.get(5).incedent(temp);
*/
        //Дальше будет идти код для продакшена, его заменю моком
   //

        //Random r = new Random(System.currentTimeMillis());
        int nextNode;
        for(int i=0; i<nodes.size(); i++)
        {

            if (i != 5) {
                nextNode = i+1;
            }
            else
            {
                nextNode = 0;
            }

            Edge temp = new Edge(nodes.get(i), nodes.get(nextNode));
            edges.add(temp);
            nodes.get(i).incedent(temp);
            nodes.get(nextNode).incedent(temp);


        }
        //проверка на смежность вершин и внесение в списки смежности, неплохо бы вынести в другой метод
        for(int i = 0; i <nodes.size()-1; i++) {
            Node node1 =nodes.get(i);
            for (int j = i+1; j<nodes.size(); j++)
            {
                Node node2 =  nodes.get(j);
                if (adjacency(node1, node2)) {
                    node1.adjustedNodes.add(node2);
                    node2.adjustedNodes.add(node1);
                    //System.out.println(node1.getId() + "  " + node2.getId());
                }
            }
        }

    }
    @Override
    public String toString()
    {   StringBuilder strB = new StringBuilder();

        for (Edge temp : edges) {
            strB.append(temp.getId()).append(";");
        }
        return strB.toString();
    }

    public String toJSON()
    {   StringBuilder strB = new StringBuilder();
        strB.append("{");
        strB.append("\"startNode\": \"");
        strB.append(getStartNode().getId());
        strB.append("\",");
        strB.append("\"variant\":[ ");
        for (Edge temp : edges) {
            strB.append("{\"group\": \"edges\", \"data\": { \"id\": \"");
            strB.append(temp.getId());
            strB.append("\", \"source\": \"");
            strB.append(temp.getSourse().getId());
            strB.append("\", \"target\": \"");
            strB.append(temp.getDestination().getId());
            strB.append("\"}},");
            //strB.append(temp.getId()).append(";");
        }

        strB.delete(strB.length()-1, strB.length());
        strB.append("]");
        strB.append("}");
        return strB.toString();
    }
    public void randomize(int count) {

        //проверить списки межности вершин
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < count; i++) {
            boolean breaker = true;
            int randomed_node=startNode.getNumId();
            while (breaker) {
               if(i>0) {
                   randomed_node = (r.nextInt(6) + 0);
               }
                if (nodes.get(randomed_node).incedentEdges.size()==2)
                {
                    breaker = false;
                }
            }
            breaker = true;
            while (breaker) {
                Node randomedNode = nodes.get(randomed_node);
                int swapped_node = (r.nextInt(6) + 0);
                Node swappedNode1 = nodes.get(swapped_node);
                Node swappedNode2;
                if (swapped_node==5)
                {  swappedNode2 = nodes.get(swapped_node - 1);}

                else
                {
                    swappedNode2 = nodes.get(swapped_node +1);
                }
                if (!randomedNode.adjustedNodes.contains(swappedNode1) &&
                        !randomedNode.adjustedNodes.contains(swappedNode2)) {
                    Edge temp = new Edge(randomedNode, swappedNode1);
                    edges.add(temp);
                    randomedNode.incedent(temp);
                    swappedNode1.incedent(temp);

                    temp = new Edge(randomedNode, swappedNode2);
                    edges.add(temp);
                    randomedNode.incedent(temp);
                    swappedNode2.incedent(temp);

                    swappedNode1.adjustedNodes.remove(swappedNode2);
                   // swappedNode1.adjustedNodes.add(randomedNode);
                    swappedNode2.adjustedNodes.remove(swappedNode1);
                   // swappedNode2.adjustedNodes.add(randomedNode);
                    //вынеси в отдельный метод
                    for (int j = 0; j<edges.size(); j++) {
                        Edge tempEdge = edges.get(j);
                        if ((tempEdge.getSourse() == swappedNode1 && tempEdge.getDestination() == swappedNode2) ||
                                (tempEdge.getSourse() == swappedNode2 && tempEdge.getDestination() == swappedNode1)) {
                            edges.remove(tempEdge);
                            break;
                        }
                    }

                    breaker = false;
                }
            }
        }

        //проверка на смежность вершин и внесение в списки смежности, неплохо бы вынести в другой метод
        for(Node n : nodes)
        {
            n.adjustedNodes = new ArrayList();
        }
        for(int i = 0; i <nodes.size()-1; i++) {
            Node node1 =nodes.get(i);
            for (int j = i+1; j<nodes.size(); j++)
            {
                Node node2 =  nodes.get(j);
                if (adjacency(node1, node2)) {
                    node1.adjustedNodes.add(node2);
                    node2.adjustedNodes.add(node1);
                    //System.out.println(node1.getId() + "  " + node2.getId());
                }
            }
        }

    }

    public void solution(int level, ArrayList<Edge> currentEdges, ArrayList<Node> currentNodes, Vertex parentNode)
    {
        //
        /*ArrayList<Edge> newEdges= currentEdges;
        ArrayList<Node> newNodes= currentNodes;
        ArrayList<Edge> bufEdges;
        ArrayList<Node> bufNodes;
        Node start=null;
        if(level == 0 ) {
            start = nodes.get(0);
            if(newEdges.size()==0 || start.adjustedNodes.size()==0) return; //условие выхода, когда закончатся ребра или когда из ноды нельзя никуда добраться
            Vertex tempV = new Vertex(level+1, start);
            treeMap.setRootElement(tempV);
            solution(level+1 ,newEdges,newNodes,tempV);
        }
        else {
            //проходим по мапе, которая сформировалась на данный момент
            //и выбираем элемент, от которого будем продолжать движение
            for (Vertex item : treeMap.toList()) {
                if (item.getLevel() == level) {
                    start = item.getData();
                    if (newEdges.size() == 0 || start.adjustedNodes.size() == 0) return;


                //treeMap.put(level, start);
                for (int i=0 ; i < start.adjustedNodes.size(); i++)  {
                    Node n =  start.adjustedNodes.get(i);
                    for (int j = 0 ; j <newEdges.size(); j++)  {
                        Edge e =  newEdges.get(j);
                        if ((e.getDestination() == n && e.getSourse() == start) || (e.getDestination() == start && e.getSourse() == n))//ошибка?
                        {
                            //сделал грязь с ребрами, но нужно создавать каждый раз экземпляр нодсов, и аджустед ноды менять
                            Vertex tempV = new Vertex(level + 1,n);

                            parentNode.addChild(tempV);
                            //сохраняем состояние для последующих вызовов рекурсивного алогоритма
                            ArrayList<Edge> tempE = (ArrayList<Edge>) currentEdges.clone();
                            ArrayList<Node> tempN = (ArrayList<Node>) currentNodes.clone();

                            savedEdges.add(tempE);
                            savedNodes.add(tempN);
                            //удаление ребра по которому только что прошли, так же удаление связей из инцидентных рёбер
                            newEdges.remove(e); //concurrent Exception
                            start.adjustedNodes.remove(n);
                            n.adjustedNodes.remove(start);
                            start.incedentEdges.remove(e);
                            n.incedentEdges.remove(e);
                            solution(level + 1, newEdges, newNodes,tempV);
                            newEdges =  savedEdges.get(savedEdges.size()-1);
                            savedEdges.remove(savedEdges.size()-1);
                            newNodes = savedNodes.get(savedNodes.size()-1);
                            savedNodes.remove(savedNodes.size()-1);
                        }
                    }
                }
            }
        }
        }
        */
        //



        Node start=null;
        if(level == 0 ) {
            start = startNode;//nodes.get(0);
            Vertex tempV = new Vertex(level+1, start);
            treeMap.setRootElement(tempV);
            solution(level+1 ,currentEdges,currentNodes,tempV);
        }
        else {
            //проходим по мапе, которая сформировалась на данный момент
            //и выбираем элемент, от которого будем продолжать движение
            start = parentNode.getData();
            if (start.adjustedNodes.size() == 0) return;

            for (Node n :  start.adjustedNodes){
                for (Edge e :  currentEdges){
                    if ((e.getDestination() == n && e.getSourse() == start) || (e.getDestination() == start && e.getSourse() == n))//ошибка?
                    {
                        //сделал грязь с ребрами, но нужно создавать каждый раз экземпляр нодсов, и аджустед ноды менять
                        Vertex tempV = new Vertex(level + 1,n);

                        parentNode.addChild(tempV);
                        //сохраняем состояние для последующих вызовов рекурсивного алогоритма
                        ArrayList<Edge> tempE = (ArrayList<Edge>) currentEdges.clone();
                        ArrayList<Node> tempN = (ArrayList<Node>) currentNodes.clone();
                        Node copyS = (Node) start.clone();
                        Node copyN = (Node) n.clone();
                        savedEdges.add(tempE);
                        savedNodes.add(tempN);
                        //удаление ребра по которому только что прошли, так же удаление связей из инцидентных рёбер
                        tempE.remove(e); //concurrent Exception
                        copyS.adjustedNodes.remove(copyN);
                        copyN.adjustedNodes.remove(copyS);
                        copyS.incedentEdges.remove(e);
                        copyN.incedentEdges.remove(e);
                        solution(level + 1, tempE, tempN,tempV);
                                /*
                                currentEdges =  savedEdges.get(savedEdges.size()-1);
                                savedEdges.remove(savedEdges.size()-1);
                                currentNodes = savedNodes.get(savedNodes.size()-1);
                                savedNodes.remove(savedNodes.size()-1);
                                */
                    }
                }
            }

        }

    }
}