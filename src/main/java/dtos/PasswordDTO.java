package dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 
 * @author svenkubiak
 *
 */
public class PasswordDTO {
    @NotNull
    @Pattern(regexp = "\\w{8,8}-\\w{4,4}-\\w{4,4}-\\w{4,4}-\\w{12,12}")
    public String token;

    @NotNull
    @Size(min = 8, max = 32)
    public String userpass;

    @NotNull
    @Size(min = 8, max = 32)
    public String userpassConfirmation;
}
