package augustobellinaso.bluefood.infrastructure.web.converter;

import augustobellinaso.bluefood.util.FormatUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BigDecimalToStringConverter implements Converter<BigDecimal, String> {

    @Override
    public String convert(BigDecimal source) {
        return FormatUtils.formatCurrency(source);
    }
}
