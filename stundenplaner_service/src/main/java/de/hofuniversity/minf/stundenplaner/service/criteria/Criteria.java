package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.common.exception.NotFoundException;
import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.*;

public class Criteria implements Criterion{
    private static Criteria myInstance = null;

    private final Map<Criterion, Double> criteriaWeights;
    private final List<Double> weights;

    private Criteria() {
        criteriaWeights = new HashMap<>();

        // TODO: Parse config to a map of strings, currently hardcoded
        Map<String, String> criteriaConfig = new HashMap<>();
        criteriaConfig.put("room", "100.0");

        List<Double> initialWeights = new ArrayList<>();
        criteriaConfig.forEach((key, value) -> {
            try {
                initialWeights.add(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                initialWeights.add(1.0);
            }
        });

        weights = normalizeWeights(initialWeights);

        CriterionRegistry registry = new CriterionRegistry();
        int i = 0;
        for(String key: criteriaConfig.keySet()) {
            try {
                criteriaWeights.put(registry.getCriterion(key), weights.get(i++));
            } catch(NotFoundException e) {
                //todo: write to log file
                System.out.printf("Unable to load criterion class for key '%s'\n", key);
            }
        }
    }

    private List<Double> normalizeWeights(List<Double> weights) {
        Double sum = 0.0;
        List<Double> normalized = new ArrayList<>();

        for (Double weight : weights) {
            sum += weight;
        }

        Double factor = 1.0/sum;

        for (Double weight : weights) {
            normalized.add(weight * factor);
        }

        return normalized;
    }

    private Double logisticFunction(Double value) {
        // 1 / (1 + ℯ^(-0.025 (x + 100)))
        return (1.0 / (1 + Math.pow(Math.E, -0.03 * (value + 100))));
    }

    private List<Double> deLinearizeWeights(List<Double> weights, List<Double> values, Double sum) {
        List<Double> deLinearizedWeights = new ArrayList<>();

        int i = 0;
        for(Double value: values) {
            Double newWeight = (value - sum) * weights.get(i++);
            newWeight = logisticFunction(newWeight);
            deLinearizedWeights.add(newWeight);
        }

        return normalizeWeights(deLinearizedWeights);
    }

    @Override
    public Double evaluate(List<LessonDO> lessonDOList) {
        double sum = 0.0;

        List<Double> values = new ArrayList<>();

        for (Criterion nextKey : criteriaWeights.keySet()) {
            double currentValue = nextKey.evaluate(lessonDOList) * criteriaWeights.get(nextKey);

            if (Double.isInfinite(currentValue)) {
                return Double.NEGATIVE_INFINITY;
            }

            sum += currentValue;
            values.add(currentValue);
        }

        // due to the normalization of the weights, sum is between 0 and 100 (-inf for invalid combinations)

        List<Double> newWeights = deLinearizeWeights(weights, values, sum);

        sum = 0.0;

        for(int i = 0; i < values.size(); i++) {
            sum += values.get(i) * newWeights.get(i);
        }

        return sum;
    }

    @Override
    public List<CriterionExplaination> explain(List<LessonDO> lessonDOList) {
        List<CriterionExplaination> results = new ArrayList<>();
        double sum = 0.0;
        List<Double> values = new ArrayList<>();

        Iterator<Criterion> iterator = criteriaWeights.keySet().iterator();
        while(iterator.hasNext()){
            Criterion nextKey = iterator.next();
            double currentValue = nextKey.evaluate(lessonDOList) * criteriaWeights.get(nextKey);

            sum += currentValue;
            values.add(currentValue);
        }

        // due to the normalization of the weights, sum is between 0 and 100 (-inf for invalid combinations)

        List<Double> newWeights = deLinearizeWeights(weights, values, sum);


        int i = 0;
        iterator = criteriaWeights.keySet().iterator();
        while(iterator.hasNext()){
            Criterion nextKey = iterator.next();
            List<CriterionExplaination> explanations = nextKey.explain(lessonDOList);
            for (CriterionExplaination explanation : explanations) {
                explanation.weight *= newWeights.get(i++);
                results.add(explanation);
            }
        }

        return results;
    }

    public static Criteria getInstance() {
        if(myInstance == null) {
            myInstance = new Criteria();
        }
        return myInstance;
    }
}
