package vlab.server_java.generate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niqera on 09.06.2016.
 */
public class Tree {
    private Vertex rootElement;

    /**
     * Default ctor.
     */
    public Tree() {
        super();
    }

    /**
     * Return the root Node of the tree.
     * @return the root element.
     */
    public Vertex getRootElement() {
        return this.rootElement;
    }

    /**
     * Set the root Element for the tree.
     * @param rootElement the root element to set.
     */
    public void setRootElement(Vertex rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * Returns the Tree<T> as a List of Node<T> objects. The elements of the
     * List are generated from a pre-order traversal of the tree.
     * @return a List<Node<T>>.
     */
    public List<Vertex> toList() {
        List<Vertex> list = new ArrayList<Vertex>();
        walk(rootElement, list);
        return list;
    }

    /**
     * Returns a String representation of the Tree. The elements are generated
     * from a pre-order traversal of the Tree.
     * @return the String representation of the Tree.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        for(Vertex n :toList())
        {
            strb.append(n.getLevel()).append(" ").append(n.getData().getId()).append("/ ");
        }
        return strb.toString();
    }
    public void print()
    {
        for(int i = 1; i<10; i++)
        {
            for(Vertex n :toList()) {
                if(n.getLevel()==i) {
                    System.out.print(" "+n.getData().getId()+" ");
                }
            }
            System.out.println();

        }
    }

    /**
     * Walks the Tree in pre-order style. This is a recursive method, and is
     * called from the toList() method with the root element as the first
     * argument. It appends to the second argument, which is passed by reference     * as it recurses down the tree.
     * @param element the starting element.
     * @param list the output of the walk.
     */
    private void walk(Vertex element, List<Vertex> list) {
        list.add(element);
        for (Vertex data : element.getChildren()) {
            walk(data, list);
        }
    }

    public ArrayList<ArrayList<Character>> parsedSolution()
    {
        ArrayList<ArrayList<Character>> parsedSolution = new ArrayList();
        ArrayList<Character> oneSolution = new ArrayList();
        StringBuilder strb = new StringBuilder();
        for(Vertex n :toList())
        {
            if (n.getLevel() > oneSolution.size()) {
                oneSolution.add(n.getData().getId());
            }
            else {
                parsedSolution.add(oneSolution);
                ArrayList<Character> tempSol = new ArrayList<Character>();
                for(int i = 0; i<n.getLevel()-1 ; i ++)
                {
                    tempSol.add(oneSolution.get(i));
                }

                oneSolution =  tempSol;
                oneSolution.add(n.getData().getId());
            }
        }
        parsedSolution.add(oneSolution);
        return parsedSolution;
    }
    public void printParsed(ArrayList<ArrayList<Character>> parsedSolution)
    {
        for(ArrayList<Character> oneSol :parsedSolution)
        {
            for(Character ch : oneSol)
            {
                System.out.print(ch+ " ");
            }
            System.out.println();
        }
        System.out.println(parsedSolution.size());
    }
    public void setParsedSolution()
    {

    }
}

