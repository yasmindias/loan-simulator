package com.project.loan_simulator.configuration

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig: Jackson2ObjectMapperBuilderCustomizer {
    override fun customize(jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder?) {
        jacksonObjectMapperBuilder
            ?.modulesToInstall(KotlinModule::class.java)
            ?.modulesToInstall(JavaTimeModule::class.java)
            ?.deserializers(LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
            ?.deserializers(LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE))
            ?.serializers(LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
            ?.serializers(LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}