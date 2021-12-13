package de.hofuniversity.minf.stundenplaner.service.criteria;

import de.hofuniversity.minf.stundenplaner.persistence.timetable.data.LessonDO;

import java.util.*;

public class Criteria implements Criterion{

    private Map<Criterion, Double> criteriaWeights;

    public Criteria() {
        // TODO: Parse config to a map of strings, currently hardcoded
        Map<String, String> criteriaConfig = new HashMap<String, String>();

        List<Double> weights = new ArrayList<>();
        criteriaConfig.entrySet().forEach((entry) -> {
            try {
                weights.add(Double.parseDouble(entry.getValue()));
            } catch(NumberFormatException e) {
                weights.add(1.0);
            }
        });

        List<Double> normalizedWeights = normalizeWeights(weights);

        CriterionRegistry registry = new CriterionRegistry();
        int i = 0;
        for(String key: criteriaConfig.keySet()) {
            criteriaWeights.put(registry.getCriterion(key), normalizedWeights.get(i++));
        }
    }

    private List<Double> normalizeWeights(List<Double> weights) {
        Double sum = 0.0;
        List<Double> normalized = new ArrayList<>();

        for(int i = 0; i < weights.size(); i++) {
            sum += weights.get(i);
        };

        Double factor = 1.0/sum;

        for(int i = 0; i < weights.size(); i++) {
            normalized.add(weights.get(i) * factor);
        }

        return normalized;
    }

    private List<Double> deLinearizeWeights(List<Double> weights, Double averageValue) {
        List<Double> deLinearizedWeights = new ArrayList<>();

        return deLinearizedWeights;
    }

    @Override
    public Double evaluate(List<LessonDO> lessonDOList) {
        Double result = 0.0;
        int i = 0;

        Iterator<Criterion> iterator = criteriaWeights.keySet().iterator();

        while(iterator.hasNext()){
            Criterion nextKey = iterator.next();
            result += nextKey.evaluate(lessonDOList) * criteriaWeights.get(nextKey);
        }

        return result;
    }

    @Override
    public List<CriterionExplaination> explain(List<LessonDO> lessonDOList) {
        List<CriterionExplaination> results = new ArrayList<>();
        int i = 0;

        Iterator<Criterion> iterator = criteriaWeights.keySet().iterator();

        while(iterator.hasNext()){
            Criterion nextKey = iterator.next();
            List<CriterionExplaination> explainations = nextKey.explain(lessonDOList);
            for(int j = 0; j < explainations.size(); j++) {
                explainations.get(j).weight *= criteriaWeights.get(nextKey);
                results.add(explainations.get(j));
            }
        }

        return results;
    }
}
