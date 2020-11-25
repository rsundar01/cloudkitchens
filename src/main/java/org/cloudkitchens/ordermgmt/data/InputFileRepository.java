package org.cloudkitchens.ordermgmt.data;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFileRepository extends JpaRepository<InputFile, String> {

}
