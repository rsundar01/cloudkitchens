package org.cloudkitchens.ordermgmt.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cloudkitchens")
public class CloudKitchensConfig {


    private List<String> inputfolders = new ArrayList<>();

    @Value("${cloudkitchens.order.preparation.min:-1}")
    private Integer min;

    @Value("${cloudkitchens.order.preparation.max:-1}")
    private Integer max;

    @Value("${cloudkitchens.order.processdelay}")
    private Integer processdelay;

    @Value("${cloudkitchens.order.nthreads:-1}")
    private Integer nthreads;

    private Shelf shelf = new Shelf();

    public List<String> getInputfolders() {
        return inputfolders;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getProcessdelay() {
        return processdelay;
    }

    public int getNthreads() {
        return nthreads;
    }

    public Shelf getShelf() { return shelf; }

    public class Shelf {

        private int hotShelfSize;
        private int coldShelfSize;
        private int frozenShelfSize;
        private int overflowShelfSize;

        public int getHotShelfSize() {
            return hotShelfSize;
        }

        public void setHotShelfSize(int hotShelfSize) {
            this.hotShelfSize = hotShelfSize;
        }

        public int getColdShelfSize() {
            return coldShelfSize;
        }

        public void setColdShelfSize(int coldShelfSize) {
            this.coldShelfSize = coldShelfSize;
        }

        public int getFrozenShelfSize() {
            return frozenShelfSize;
        }

        public void setFrozenShelfSize(int frozenShelfSize) {
            this.frozenShelfSize = frozenShelfSize;
        }

        public int getOverflowShelfSize() {
            return overflowShelfSize;
        }

        public void setOverflowShelfSize(int overflowShelfSize) {
            this.overflowShelfSize = overflowShelfSize;
        }
    }

}
