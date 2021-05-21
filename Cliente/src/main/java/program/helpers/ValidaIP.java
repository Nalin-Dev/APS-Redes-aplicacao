package main.java.program.helpers;

import java.util.regex.Pattern;

public class ValidaIP implements IValidador {

    @Override
    public boolean isValido(final String ip) {
        final String zeroTo255
                = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";

        final String IP_REGEXP
                = zeroTo255 + "\\." + zeroTo255 + "\\."
                + zeroTo255 + "\\." + zeroTo255;

        final Pattern IP_PATTERN
                = Pattern.compile(IP_REGEXP);

        return IP_PATTERN.matcher(ip).matches();
    }
}
