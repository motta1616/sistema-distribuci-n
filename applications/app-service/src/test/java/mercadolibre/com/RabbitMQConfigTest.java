package mercadolibre.com;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigTest {
    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private ConnectionFactory connectionFactory;

    @Test
    void queueShouldBeCreatedWithCorrectName() {
        rabbitMQConfig.queueName = "queueName";

        Queue queue = rabbitMQConfig.queue();

        assertEquals("queueName", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void exchangeShouldBeCreatedWithCorrectName() {
        String exchangeName = "testExchange";
        rabbitMQConfig = new RabbitMQConfig();
        rabbitMQConfig.exchangeName = exchangeName;

        DirectExchange exchange = rabbitMQConfig.exchange();

        assertEquals(exchangeName, exchange.getName());
    }

    @Test
    void bindingShouldBindQueueToExchangeWithCorrectRoutingKey() {
        String queueName = "testQueue";
        String exchangeName = "testExchange";
        rabbitMQConfig = new RabbitMQConfig();
        rabbitMQConfig.queueName = queueName;
        rabbitMQConfig.exchangeName = exchangeName;

        Queue queue = new Queue(queueName, true);
        DirectExchange exchange = new DirectExchange(exchangeName);
        Binding binding = rabbitMQConfig.binding(queue, exchange);

        assertEquals(queueName, binding.getRoutingKey());
        assertEquals(queue.getName(), binding.getDestination());
        assertEquals(exchange.getName(), binding.getExchange());
    }

    @Test
    void messageConverterShouldReturnJackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = rabbitMQConfig.messageConverter();

        assertEquals(Jackson2JsonMessageConverter.class, converter.getClass());
    }

    @Test
    void rabbitTemplateShouldBeConfiguredWithMessageConverter() {
        Jackson2JsonMessageConverter converter = mock(Jackson2JsonMessageConverter.class);
        RabbitTemplate rabbitTemplate = rabbitMQConfig.rabbitTemplate(connectionFactory);

        assertEquals(converter.getClass(), rabbitTemplate.getMessageConverter().getClass());
    }
}