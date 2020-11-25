package org.cloudkitchens.ordermgmt.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INPUT_FILES")
public class InputFile {

    @Id
    @Column(unique=true, nullable = false, columnDefinition="VARCHAR(255)")
    private String fileName;

    @Column(nullable =false)
    private int status;

    public InputFile() {}

    public InputFile(String fileName) {

        this.fileName = fileName;

        this.status = 0;
    }

    public InputFile(String fileName, int status) {

        this.fileName = fileName;

        this.status = status;
    }

    public int getStatus() {

        return status;
    }

    public String getFileName() {
        return fileName;
    }
}
