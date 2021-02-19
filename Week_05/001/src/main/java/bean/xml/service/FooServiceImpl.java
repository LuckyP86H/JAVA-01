package bean.xml.service;

import bean.xml.repository.FooRepository;

public class FooServiceImpl implements FooService {

    private FooRepository fooRepository;

    public void setFooRepository(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @Override
    public void foo() {
        fooRepository.foo();
    }
}
