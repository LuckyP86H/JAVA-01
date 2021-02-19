package bean.annotation.repository;

import org.springframework.stereotype.Repository;

@Repository
public class FeeRepositoryImpl implements FeeRepository {
    @Override
    public void fee() {
        System.out.println("annotation fee");
    }
}
