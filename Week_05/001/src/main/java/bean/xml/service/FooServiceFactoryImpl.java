package bean.xml.service;

public class FooServiceFactoryImpl implements FooServiceFactory {

    @Override
    public FooService create() {

        return new FooService() {
            @Override
            public void foo() {
                System.out.println("factory bean");
            }
        };
    }
}
