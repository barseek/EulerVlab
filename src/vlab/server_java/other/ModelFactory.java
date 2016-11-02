package vlab.server_java.other;


import vlab.server_java.model.*;

public class    ModelFactory {
    public static Model createModelByAlgorithm(String name){
        Model model = new Eiler();
        return model;
    }

    public static Model createModelByAlgorithmAndVariant(String preGeneratedInstructions, String instructions){
        Model model = new Eiler(preGeneratedInstructions, instructions);

        return model;
    }
}
