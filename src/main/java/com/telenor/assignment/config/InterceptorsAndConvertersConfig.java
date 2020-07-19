package com.telenor.assignment.config;

import com.telenor.assignment.validator.ProductAPIParamValidatorInterceptor;
import com.telenor.assignment.helper.enummapper.StringAutoDecoderFactory;
import com.telenor.assignment.helper.enummapper.StringToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsAndConvertersConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverterFactory(new StringAutoDecoderFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProductAPIParamValidatorInterceptor());
    }
}
