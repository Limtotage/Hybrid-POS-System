package com.hybridpos.sale_service.config;

import com.hybridpos.sale_service.event.SaleCreatedEvent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableKafka
public class KafkaConfig {
        @Value("${spring.kafka.bootstrap-servers}")
        private String bootstrapServers;

        // =========================
        // PRODUCER
        // =========================

        @Bean
        public ProducerFactory<String, SaleCreatedEvent> producerFactory() {

                Map<String, Object> config = new HashMap<>();

                config.put(
                                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                bootstrapServers);

                config.put(
                                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                StringSerializer.class);

                config.put(
                                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                JsonSerializer.class);

                return new DefaultKafkaProducerFactory<>(config);
        }

        @Bean
        public KafkaTemplate<String, SaleCreatedEvent> kafkaTemplate() {
                return new KafkaTemplate<>(producerFactory());
        }

        // =========================
        // CONSUMER
        // =========================

        @Bean
        public ConsumerFactory<String, SaleCreatedEvent> consumerFactory() {

                Map<String, Object> config = new HashMap<>();

                config.put(
                                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                bootstrapServers);

                config.put(
                                ConsumerConfig.GROUP_ID_CONFIG,
                                "sale-service-group");

                config.put(
                                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                StringDeserializer.class);

                config.put(
                                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                JsonDeserializer.class);

                config.put(
                                JsonDeserializer.TRUSTED_PACKAGES,
                                "com.hybridpos.sale_service.event");

                config.put(
                                JsonDeserializer.VALUE_DEFAULT_TYPE,
                                SaleCreatedEvent.class.getName());

                config.put(
                                JsonDeserializer.USE_TYPE_INFO_HEADERS,
                                false);

                return new DefaultKafkaConsumerFactory<>(config);
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, SaleCreatedEvent> kafkaListenerContainerFactory() {

                ConcurrentKafkaListenerContainerFactory<String, SaleCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

                factory.setConsumerFactory(consumerFactory());

                return factory;
        }
}