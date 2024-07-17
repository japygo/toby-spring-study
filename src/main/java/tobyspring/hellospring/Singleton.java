package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Singleton {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        PaymentService paymentService2 = beanFactory.getBean(PaymentService.class);

//        ObjectFactory objectFactory = beanFactory.getBean(ObjectFactory.class);
//        PaymentService paymentService = objectFactory.paymentService();
//        PaymentService paymentService2 = objectFactory.paymentService();

        System.out.println(paymentService);
        System.out.println(paymentService2);
        System.out.println(paymentService == paymentService2);
    }
}
