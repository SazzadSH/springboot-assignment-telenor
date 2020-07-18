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
        for(Class cls: classList){
            getClassListInMapOfClassProperty(this.classListInMapOfClassProperty,cls);
        }
        /*for(int i = 0; i < classList.size(); i++){
            getClassListInMapOfClassProperty(this.classListInMapOfClassProperty,classList.get(i));
        }*/
    }
    private <T> void getClassListInMapOfClassProperty(Map<String, List<Class>> classListInMapOfClassProperty,
                                                      Class<T> cls){
        logger.info(new StringBuilder().append("NILOG::Parsing Entity Class::")
                .append(cls.getSimpleName()).toString());
        for (Field field : cls.getDeclaredFields()) {
            String columnName;
            if (field.isAnnotationPresent(Column.class)) {
                columnName = field.getAnnotation(Column.class).name();
            } else {
                columnName = field.getName();
            }
            if(classListInMapOfClassProperty.containsKey(columnName)){
                List<Class> classList = classListInMapOfClassProperty.get(columnName);
                classList.add(cls);
                classListInMapOfClassProperty.put(columnName,classList);
            }else{
                List<Class> classList = new ArrayList<>();
                classList.add(cls);
                classListInMapOfClassProperty.put(columnName,classList);
            }
            logger.info(new StringBuilder().append("NILOG::").append(columnName).toString());
        }
    }
}
