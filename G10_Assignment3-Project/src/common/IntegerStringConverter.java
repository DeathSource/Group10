package common;

import javafx.util.StringConverter;

/**
 *  This class is used to show only integer 
 *  values at the NumberAxis of a Barchart.
 */
public class IntegerStringConverter extends StringConverter<Number>{

    public IntegerStringConverter() {
    }

    @Override
    public String toString(Number object) {
        if(object.intValue()!=object.doubleValue())
            return "";
        return ""+(object.intValue());
    }

    @Override
    public Number fromString(String string) {
        Number val = Double.parseDouble(string);
        return val.intValue();
    }
} 
