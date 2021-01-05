package br.com.arj.mymoney.util;

import javax.persistence.AttributeConverter;

import br.com.arj.mymoney.constant.TipoOperacaoEnum;

public class TipoOperacaoConverter implements AttributeConverter<TipoOperacaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoOperacaoEnum attribute) {
        return attribute.getCode();
    }

    @Override
    public TipoOperacaoEnum convertToEntityAttribute(Integer dbData) {
        return TipoOperacaoEnum.getFromCode(dbData);
    }

}
