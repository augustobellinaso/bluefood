package augustobellinaso.bluefood.infrastructure.web.converter;

import augustobellinaso.bluefood.util.FormatUtils;
import augustobellinaso.bluefood.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String source) {

        if (StringUtils.isEmpty(source)) {
            return null;
        }

        source = source.replace(",", ".").trim();
        return BigDecimal.valueOf(Double.valueOf(source));
    }
}
