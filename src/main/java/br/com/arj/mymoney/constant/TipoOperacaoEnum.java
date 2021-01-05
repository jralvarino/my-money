package br.com.arj.mymoney.constant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;  

import lombok.Getter;

public enum TipoOperacaoEnum {

    DESPESA(0),
    RECEITA(1);

	@Getter
    private final int code;

    private TipoOperacaoEnum(int i) {
        this.code = i;
    }

    public static TipoOperacaoEnum getFromCode(int code) {
        return Stream.of(TipoOperacaoEnum.values()).filter(t -> t.getCode() == code).findFirst().orElse(null);
    }
	
}
