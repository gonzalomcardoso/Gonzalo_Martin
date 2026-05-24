package com.mercadona.test.mapper;

import com.mercadona.test.dto.StoreDTO;
import com.mercadona.test.model.Store;

public class StoreMapper {

    public static StoreDTO toDTO(Store store) {
        StoreDTO dto = new StoreDTO();
        dto.setId(store.getId());
        dto.setCodigo(store.getCodigo());
        dto.setNombre(store.getNombre());
        return dto;
    }
}
