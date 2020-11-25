package org.cloudkitchens.ordermgmt.configurations;

import org.cloudkitchens.ordermgmt.configuration.CloudKitchensConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=CloudKitchensConfig.class,
        initializers = ConfigFileApplicationContextInitializer.class,
        loader=AnnotationConfigContextLoader.class)
public class CloudKitchensConfigTest {

    @Autowired
    CloudKitchensConfig config1;

    @Autowired
    CloudKitchensConfig config2;

    @Test
    public void checkForDuplicates() {

        Assert.assertTrue(config1 == config2);
    }


    @Test
    public void checkValues() {

        Assert.assertEquals(2000, config1.getProcessdelay());

        Assert.assertEquals(1, config1.getInputfolders().size());

        Assert.assertEquals(2000, config1.getMin());

        Assert.assertEquals(6000, config1.getMax());

        Assert.assertEquals(2, config1.getNthreads());

        Assert.assertEquals(10, config1.getShelf().getHotShelfSize());

        Assert.assertEquals(10, config1.getShelf().getColdShelfSize());

        Assert.assertEquals(10, config1.getShelf().getFrozenShelfSize());

        Assert.assertEquals(15, config1.getShelf().getOverflowShelfSize());
    }

}
