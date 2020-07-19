package com.telenor.assignment.helper;

import com.telenor.assignment.model.Phone;
import com.telenor.assignment.model.Subscription;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class SubProductPropertyManager {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    private Map<String, List<Class>> classListInMapOfClassProperty = new HashMap<>();

    private final List<Class>  classList = Arrays.asList(Phone.class, Subscription.class);

    @PostConstruct
    private <T> void init(){
        for(Class clazz: classList){
            getClassListInMapOfClassProperty(this.classListInMapOfClassProperty,clazz);
        }
    }
    private <T> void getClassListInMapOfClassProperty(Map<String, List<Class>> classListInMapOfClassProperty,
                                                      Class<T> clazz){
        logger.info(new StringBuilder().append("NILOG::Parsing Entity Class::")
                .append(clazz.getSimpleName()).toString());
        for (Field field : clazz.getDeclaredFields()) {
            String columnName;
            if (field.isAnnotationPresent(Column.class)) {
                columnName = field.getAnnotation(Column.class).name();
            } else {
                columnName = field.getName();
            }
            if(classListInMapOfClassProperty.containsKey(columnName)){
                List<Class> classList = classListInMapOfClassProperty.get(columnName);
                classList.add(clazz);
                classListInMapOfClassProperty.put(columnName,classList);
            }else{
                List<Class> classList = new ArrayList<>();
                classList.add(clazz);
                classListInMapOfClassProperty.put(columnName,classList);
            }
            logger.info(new StringBuilder().append("NILOG::").append(columnName).toString());
        }
    }
}
