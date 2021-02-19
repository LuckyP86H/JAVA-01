package bean.xml.service;

public class StaticFooServiceFactory {
    public static FooService create() {
        return new FooService() {
            @Override
            public void foo() {
                System.out.println("static FooService");
            }
        };
    }
}
