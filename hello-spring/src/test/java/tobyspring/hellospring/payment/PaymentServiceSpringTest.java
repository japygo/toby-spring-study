package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class) // 2,3
@ContextConfiguration(classes = TestPaymentConfig.class) // 2,3
class PaymentServiceSpringTest {

    @Autowired
//    BeanFactory beanFactory; // 2
    PaymentService paymentService; // 3

    @Autowired
    ExRateProviderStub exRateProviderStub;

    @Autowired
    Clock clock;

    @Test
    void convertedAmount() {
//        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class); // 1
//        PaymentService paymentService = beanFactory.getBean(PaymentService.class); // 1,2

        // exRate: 1000
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));

        // exRate: 500
        exRateProviderStub.setExRate(BigDecimal.valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(5_000));
    }

    @Test
    void validUntil() {
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // valid until이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }
}
