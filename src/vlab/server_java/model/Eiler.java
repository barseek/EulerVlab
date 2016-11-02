package vlab.server_java.model;


/**
 * Created by dima on 02.05.2015.
 */

import org.json.JSONArray;
import org.json.JSONObject;
import vlab.server_java.other.Model;
import vlab.server_java.generate.*;

import java.util.ArrayList;

/**
 * Формат варианта: rE;rr;rm
 * Формат состояния модели: sliderValue;rr;E;Im;P;U;I
 */
public class Eiler implements Model {

    private String labName;
    private String edges;
    private String edgesJSON;
    private String text;
    private char startNodeId;
    private int userNumOfCycles;

    private ArrayList<ArrayList<Character>> parsedSolution;
    private ArrayList<ArrayList<Character>> userCyclesStringArray;
    private float score = 0f;
    /**
     * При создании через данный конструктор генерируется новый вариант
     */
    public Eiler(){
        labName = "eiler";
        Graph test = new Graph();
        test.randomize(1); //расскоменть
        edges=test.getStartNode().getId()+";";
        //edgesMas.add(test.getStartNode().getId());
        startNodeId = test.getStartNode().getId();

        edges += test.toString();
        edgesJSON = test.toJSON();
        test.solution(0,test.getEdges(), test.getNodes(), new Vertex());
        System.out.println("solution:");
        System.out.println(test.getTreeMap().toString());
        test.getTreeMap().printParsed(test.getTreeMap().parsedSolution());
        //edges = "ab;ac;ae;af;bc;bd;be;cd;ce;de;df";

    }

    /**
     * При создании через данный конструктор подхватывается имеющийся вариант, также грузится пользовательский ответ
     */
    public Eiler(String variant, String modelState) {
        //сплитать пришедший массив, пока не нужно
        /*
        String[] params = variant.split(";");
        for (int i = 0; i < params.length; i++) {
            System.out.println("edge " + params[i] + " ;");
        }*/
        edges = variant;
        System.out.println("Var : "+variant);
        /*

*/
        Graph UserGraph = new Graph(edges,userNumOfCycles);
        UserGraph.solution(0,UserGraph.getEdges(), UserGraph.getNodes(), new Vertex());
        System.out.println("solution:");
        System.out.println(UserGraph.getTreeMap().toString());
        UserGraph.getTreeMap().printParsed(UserGraph.getTreeMap().parsedSolution());
        parsedSolution = UserGraph.getTreeMap().parsedSolution();
        //
        System.out.println("user variant "+edges);
        System.out.println("user res "+modelState);

        userCyclesStringArray = new ArrayList();
        //из модел стейт достаем решения
        JSONObject obj = new JSONObject(modelState);
        userNumOfCycles = Integer.parseInt(obj.getString("solutionCounter"));


        if(modelState.length() > 0) {

            System.out.println("userNumOfCycles from euler " + userNumOfCycles);
        }
        else {text = "Задание не выполнено";}



        JSONArray arr = obj.getJSONArray("solutions");

        for (int i = 0; i < arr.length(); i++)
        {
            JSONArray solutionPath = arr.getJSONObject(i).getJSONArray("content");

            ArrayList<Character> solution = new ArrayList();
            for (int j = 0; j < solutionPath.length(); j++) {

                solution.add(solutionPath.getString(j).charAt(0));

            }
            userCyclesStringArray.add(solution);
        }


//вывод
        for( ArrayList<Character> solutionStr : userCyclesStringArray)
        {
            System.out.print("Solution : ");
            for(Character str :solutionStr)
            {
                System.out.print(str + " ");
            }
            System.out.println();
        }
    }

    /**
     * После конструктора позволяет "проиграть" модель с пользовательским ответом
     */
    @Override
    public void processModel(){




        //System.out.println("someshit");
    }

    /**
     * Возвращает сериализованый вариант после генерации нового варианта
     * @return Вариант
     */
    @Override
    public String getVariant(){
        System.out.println("Edges : "+edgesJSON);

        return edgesJSON;//edges;
    }

    /**
     * Возвращает состояние модели после "проигрывания"
     * @return Состояние модели
     */
    @Override
    public String getModelState(){
        return null;
    }

    //переделай этот говнокод

    boolean check(ArrayList<Character> serverSolution,ArrayList<Character> userSolution)
    {
        if (userSolution.size() != serverSolution.size()) {
            return false;
        }
        int counter = 0;
        for( int i = 0 ; i < serverSolution.size(); i++ )
        {
           if (serverSolution.get(i)==userSolution.get(i))
           {
               counter++;
           }
        }
        if (counter==serverSolution.size()) {
            return true;
        }
        else{
            return false;
        }

    }
    @Override
    public void checkUserAnswer(){
        int counter = 0;
        ArrayList<ArrayList<Character>> parsedSolutionCopy =(ArrayList<ArrayList<Character>>) parsedSolution.clone();
        for(ArrayList<Character> userSol : userCyclesStringArray)
        {
        for(int i = 0; i <parsedSolutionCopy.size(); i++)
        {
            ArrayList<Character> serverSol  = parsedSolutionCopy.get(i);
                if (check(serverSol,userSol)) {
                    counter++;
                    parsedSolutionCopy.remove(parsedSolutionCopy.get(i));
                    if(score==0f)
                    {
                        score+=0.3f;
                    }
                    else
                    {
                        score+=0.5f/(parsedSolution.size()-1);
                    }

                    break;
                }
        }
        }

        System.out.println("Server's solutions counter : "+ parsedSolution.size());
        System.out.println("Users's solutions counter : "+ userCyclesStringArray.size());
        System.out.println("Right users's solutions counter : "+ counter);
        if(userNumOfCycles==parsedSolution.size()) text = "Вы правильно нашли количество циклов Эйлера. ";
        else  text = "Вы неправильно нашли количество циклов Эйлера. ";
        if(score !=1f) {
            String cycle = "";
            if (counter==1 || counter==21) cycle = "цикл";
            if (counter>1 && counter < 5) cycle = "цикла";
            if (counter>4 && counter < 20) cycle = "циклов";
            text += "Вы нашли : " + counter + " " + cycle+" Эйлера, общее число циклов :" + parsedSolution.size();

        }
        else text += "Вы нашли все  " + counter + " циклов Эйлера!";
    }

    @Override
    public float getScore() {

        if (parsedSolution.size()==userNumOfCycles) score+=0.2f;

        return score;
    }

    @Override
    public String getTextOutput() {
        return text;
    }
}
