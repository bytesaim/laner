package io.github.thecarisma.exceptions;

public class InvalidArgumentException extends Exception{

    private String[] arguments;
    private String message ;

    public InvalidArgumentException(String[] arguments) {
        this.arguments = arguments;
        this.message = "Invalid arguments";
    }

    public InvalidArgumentException(String[] arguments, String message) {
        this.arguments = arguments;
        this.message = message;
    }

    @Override
    public String getMessage() {
        StringBuffer var1 = new StringBuffer("{");

        for(int var2 = 0; var2 < arguments.length; ++var2) {
            var1.append(arguments[var2]);
            if (var2 < arguments.length - 1) {
                var1.append(", ");
            }
        }

        var1.append(" }");
        return String.format("%s - %s", message, var1.toString());
    }

}
