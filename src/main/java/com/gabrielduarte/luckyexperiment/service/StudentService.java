package com.gabrielduarte.luckyexperiment.service;

import com.gabrielduarte.luckyexperiment.domain.StudentEntity;
import com.gabrielduarte.luckyexperiment.repository.StudentRepository;
import com.gabrielduarte.luckyexperiment.response.StudentResponse;
import com.gabrielduarte.luckyexperiment.utils.LuckyFactor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final LuckyFactor luckyFactor;

    public StudentService(StudentRepository repository, LuckyFactor luckyFactor) {
        this.repository = repository;
        this.luckyFactor = luckyFactor;
    }

    public void create() {
        List<StudentEntity> studentEntities = new ArrayList<>(1000);                
        
        for(int i = 0; i<1000; i++) {
            final BigDecimal performance = luckyFactor.createPerformance();
            final StudentEntity studentEntity = new StudentEntity();
            studentEntity.setPerformance(performance);
            
            studentEntities.add(studentEntity);
        }

        studentEntities.sort(Comparator.comparing(StudentEntity::getPerformance));
        repository.saveAll(studentEntities);
    }

    public List<StudentEntity> getListByMeritocracy() {
        return repository.findAll();
    }

    public List<StudentResponse> getListWithLuck() {
        final List<StudentEntity> all = repository.findAll();
        applyLucky(all);

        final List<StudentResponse> studentResponses = makeDiff(all);

        Objects.requireNonNull(studentResponses).sort(Comparator.comparing(StudentResponse::getLuckyPerformance));

        int position = 1;

        for(int i = studentResponses.size() - 1; i >= 0; i--) {
            studentResponses.get(i).setPosition(position);
            position = position + 1;
        }

        return studentResponses;
    }

    private List<StudentResponse> makeDiff(final List<StudentEntity> list) {
        final ArrayList<StudentResponse> responseList = new ArrayList<>(1010);

        for(StudentEntity entity : list) {
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(entity.getId());
            studentResponse.setPerformance(entity.getPerformance());
            studentResponse.setLuckyPerformance(getPerformanceWithLucky(entity));
            studentResponse.setDiffBetweenPerformance(studentResponse.getLuckyPerformance()
                                                                    .subtract(studentResponse.getPerformance()));
            studentResponse.setTotalLucky(getTotalLucky(entity));

            responseList.add(studentResponse);
        }

        return responseList;
    }

    private void applyLucky(final List<StudentEntity> luckyList) {
        for (StudentEntity entity : luckyList) {
            BigDecimal familyPressure = luckyFactor.luckOrSetback();
            BigDecimal selfPressure = luckyFactor.luckOrSetback();
            BigDecimal goodSleep = luckyFactor.luckOrSetback();
            BigDecimal healthy = luckyFactor.luckOrSetback();
            BigDecimal guessedAnswers = luckyFactor.luckOrSetback();

            entity.setFamilyPressure(familyPressure);
            entity.setSelfPressure(selfPressure);
            entity.setGoodSleep(goodSleep);
            entity.setHealthy(healthy);
            entity.setGuessedAnswers(guessedAnswers);
        }
    }

    private BigDecimal getPerformanceWithLucky(final StudentEntity studentEntity) {
        BigDecimal performance = studentEntity.getPerformance();
        BigDecimal totalLucky = getTotalLucky(studentEntity);
        BigDecimal valueToAdd = totalLucky.divide(BigDecimal.valueOf(100))
                .multiply(performance);

        return performance.add(valueToAdd);
    }

    private BigDecimal getTotalLucky(StudentEntity studentEntity) {
        BigDecimal familyPressure = studentEntity.getFamilyPressure();
        BigDecimal selfPressure = studentEntity.getSelfPressure();
        BigDecimal goodSleep = studentEntity.getGoodSleep();
        BigDecimal healthy = studentEntity.getHealthy();
        BigDecimal guessedAnswers = studentEntity.getGuessedAnswers();

        return familyPressure.add(selfPressure).add(goodSleep).add(healthy).add(guessedAnswers);
    }
}
