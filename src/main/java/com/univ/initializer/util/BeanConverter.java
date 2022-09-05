package com.univ.initializer.util;

import java.util.Collections;
import java.util.List;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author univ
 * 2022/8/10 9:49 上午
 */
@Component
public class BeanConverter {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    MapperFacade mapperFacade = mapperFactory.getMapperFacade();

    public BeanConverter() {
        init();
    }

    private void init() {

    }
    
    public <S, D> D map(S s, Class<D> clz) {
        return mapperFacade.map(s, clz);
    }

    public <S, D> void map(S s, D d) {
        mapperFacade.map(s, d);
    }

    public <S, D> List<D> mapList(List<S> sourceList, Class<D> clz) {
        return CollectionUtils.isEmpty(sourceList) ? Collections.emptyList() : mapperFacade.mapAsList(sourceList, clz);
    }
}
