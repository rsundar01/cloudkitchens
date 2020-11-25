package org.cloudkitchens.ordermgmt.data;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InputFileRepositoryTest {

    //@Autowired
    //private TestEntityManager testEntityManager;

    @Autowired
    InputFileRepository inputFileRepository;

    @Before
    public void setUp() {

        InputFile inputFile = new InputFile("test_inputfile_01.json");

        inputFileRepository.saveAndFlush(inputFile);

    }

    @Test
    public void testInsert() {

        InputFile inputFile = inputFileRepository.getOne("test_inputfile_01.json");

        Assert.assertNotNull(inputFile);

        Assert.assertEquals("test_inputfile_01.json", inputFile.getFileName());
    }

    @Test
    public void testUpdate() {

        InputFile inputFile = new InputFile("test_inputfile_01.json");

        inputFileRepository.saveAndFlush(inputFile);

        Assert.assertEquals(1, inputFileRepository.findAll().size());

    }

}
