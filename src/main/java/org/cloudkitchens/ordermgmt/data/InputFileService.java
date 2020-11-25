package org.cloudkitchens.ordermgmt.data;


import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class InputFileService {

    //@PersistenceContext
    //EntityManager entityManager;

    @Autowired
    InputFileRepository repository;


    public InputFile getInputFile(String file) {

        //return entityManager.find(InputFile.class, file);
        Optional<InputFile> inputFile = repository.findById(file);

        if(inputFile.isPresent()) return inputFile.get();
        else return  null;
    }

    public boolean containsFile(String file) {

        if(getInputFile(file) != null) return true;

        return false;
    }

    public InputFile insertRecord(InputFile inputFile) {

        return repository.save(inputFile);

        //return entityManager.merge(inputFile);

    }

    public InputFile updateRecord(InputFile inputFile) {

        return repository.save(inputFile);

        //return entityManager.merge(inputFile);
    }
}
