package vlab.server_java.generate;

import rlcp.generate.GeneratingResult;
import rlcp.server.processor.generate.GenerateProcessor;

import java.util.UUID;
import vlab.server_java.other.*;
/**
 * Simple GenerateProcessor implementation. Supposed to be changed as needed to
 * provide necessary Generate method support.
 */
public class GenerateProcessorImpl implements GenerateProcessor {
    @Override
    public GeneratingResult generate(String condition) {
        //do Generate logic here
        String text = "Найти все циклы Эйлера в графе";
        String code = "code";
        String instructions = "instructions";

        String variantKey = UUID.randomUUID().toString();
        String algorithmT = condition.trim();

        String algorithmName = "";

        if (algorithmT.contains("~~~")){
            algorithmName = algorithmT.split("~~~")[0];

        } else {
            algorithmName = algorithmT;
        }

        Model model = ModelFactory.createModelByAlgorithm(algorithmName);
        code = model.getVariant();
        //instructions = variantKey + "~~~";
        return new GeneratingResult(text, code, instructions);
    }
}
