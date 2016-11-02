package vlab.server_java.check;

import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.check.PreCheckProcessor.PreCheckResult;
import rlcp.server.processor.check.PreCheckResultAwareCheckProcessor;
import vlab.server_java.other.Model;
import vlab.server_java.other.ModelFactory;

import java.math.BigDecimal;

/**
 * Simple CheckProcessor implementation. Supposed to be changed as needed to provide
 * necessary Check method support.
 */
public class CheckProcessorImpl implements PreCheckResultAwareCheckProcessor<String> {
    @Override
    public CheckingSingleConditionResult checkSingleCondition(ConditionForChecking condition, String instructions, GeneratingResult generatingResult) throws Exception {
        //do check logic here
        BigDecimal points = new BigDecimal(1.0); //оценка пользователя
        String comment = "none";
        Model model = ModelFactory.createModelByAlgorithmAndVariant(generatingResult.getCode(), instructions);
        //System.out.println(instructions); // по кнопе проверки будет приходить вот эта переменная
        if (model != null){
            //model.processModel();
            model.checkUserAnswer();
            comment = model.getTextOutput();

            points = new BigDecimal(Math.round(model.getScore() * 100.0) / 100.0);
        }
        return new CheckingSingleConditionResult(points, comment);
    }

    @Override
    public void setPreCheckResult(PreCheckResult<String> preCheckResult) {}
}
